package com.example.hotel_management.controller;

import com.example.hotel_management.entity.Room;
import com.example.hotel_management.service.RoomServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = RoomController.class)
public class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();//convert json to string and vise versa
    ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private RoomServiceImp roomServiceImp;


    Room room1 = new Room(101L,"luxury",2,true,5000,true,false);
    Room room2 = new Room(201L,"simple",2,true,8000,true,false);


    @Test
    public void getAllRoomsSuccess() throws Exception {
        List<Room> roomList = new ArrayList<>(Arrays.asList(room1,room2));
        Mockito.when(roomServiceImp.getRooms()).thenReturn(roomList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/rooms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].type", is("simple")))
                .andExpect(jsonPath("$[0].availability", is(true))).andDo(print());
    }

    @Test
    public void getRoomByIdSuccess() throws Exception {
        Mockito.when(roomServiceImp.getRoomById(anyLong())).thenReturn(room1);
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/{id}",101L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.type",is("luxury")))
                .andExpect(jsonPath("$.price_per_day",is(5000.0)))
                .andExpect(jsonPath("$.checked",is(true)))
                .andDo(print());
    }

    @Test
    public void addRoomsSuccess() throws Exception{
         Room record = Room.builder()
                 .room_number(101L)
                 .type("simple")
                 .occupancy(2)
                 .availability(true)
                 .price_per_day(5000)
                 .checked(false)
                 .isCheckedOut(true)
                 .build();
         Mockito.when(roomServiceImp.addRooms(Mockito.any())).thenReturn(record);
         String content = objectWriter.writeValueAsString(record);
        System.out.println(content);
         MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/rooms")
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                         .content(content);

         mockMvc.perform(mockRequest)
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$",notNullValue()))
                 .andDo(print());
    }

    @Test
    public void updateRoomSuccess() throws Exception {
        Room updatedRoom = Room.builder()
                .room_number(101L)
                .type("ultraLuxury")
                .occupancy(2)
                .availability(false)
                .isCheckedOut(true)
                .checked(true)
                .price_per_day(5000)
                .build();

        System.out.println(updatedRoom);
    // Can use this also     Mockito.when(roomServiceImp.updateRoom(Mockito.any(), anyLong())).thenReturn(updatedRoom);
        Mockito.when(roomServiceImp.getRoomById(room1.getRoom_number())).thenReturn(room1);
        Mockito.when(roomServiceImp.addRooms(Mockito.any())).thenReturn(updatedRoom);
        String updatedContent = objectWriter.writeValueAsString(updatedRoom);
        // Can use this also       MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/rooms/update/{id}",101L)
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type",is("ultraLuxury")))
                .andDo(print());
    }

    @Test
    public void deleteRoomSuccess() throws Exception {//after deleting we will just pass a status code 200 to confirm that we deleted successfully
        Mockito.when(roomServiceImp.getRoomById(room2.getRoom_number())).thenReturn(room2);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/rooms/delete/{id}",201L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
