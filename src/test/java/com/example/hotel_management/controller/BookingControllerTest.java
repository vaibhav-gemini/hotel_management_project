package com.example.hotel_management.controller;

import com.example.hotel_management.entity.Booking;
import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.entity.Room;
import com.example.hotel_management.service.BookingServiceImp;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private BookingServiceImp bookingServiceImp;

    Customer customer1 = new Customer(1L,"mana","india","123",20);
    Customer customer2 = new Customer(2L,"shruti","india","123",25);
    Customer customer3 = new Customer(3L,"ayush","india","123",29);
    Customer customer4 = new Customer(4L,"rajat","india","123",30);
    Room room = new Room(1L,"simple",2,true,5000,false,true);
    List<Customer> customerList1 = new ArrayList<>(Arrays.asList(customer1,customer2));
    List<Customer> customerList2 = new ArrayList<>(Arrays.asList(customer3,customer4));
    List<Room> rooms = new ArrayList<>(Arrays.asList(room));
    Booking booking1 = new Booking(1L,5,new Date(11-07-2000),new Date(15-07-2000),customerList1,"simple",rooms,"offline",25000,"online");
    Booking booking2 = new Booking(2L,5,new Date(11-07-2000),new Date(15-07-2000),customerList2,"luxury",rooms,"offline",35000,"online");


    @Test
    public void getAllBookingSucess() throws Exception{
        List<Booking> bookingList = new ArrayList<>(Arrays.asList(booking1,booking2));
        Mockito.when(bookingServiceImp.getAllBookingDetails()).thenReturn(bookingList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/booking")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].duration",is(5)))
                .andExpect(jsonPath("$[1].typeOfRoomPrefered",is("luxury")))
                .andExpect(jsonPath("$[0].customerList[0].customer_name", is("mana")))
                .andExpect(jsonPath("$[0].roomList[0].type",is("simple")))
                .andExpect(jsonPath("$[0].bill_amount",is(25000.0)))
                .andExpect(jsonPath("$[0].customerList", hasSize(2)))
                .andDo(print());
    }

    @Test
    public void getBookingDetailsByIdSuccess() throws Exception {
        Mockito.when(bookingServiceImp.getBookingDetailsById(anyLong())).thenReturn(booking1);

        mockMvc.perform(MockMvcRequestBuilders.get("/booking/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.duration",is(5)))
                .andExpect(jsonPath("$.typeOfRoomPrefered",is("simple")))
                .andExpect(jsonPath("$.customerList[0].customer_name", is("mana")))
                .andExpect(jsonPath("$.roomList[0].type",is("simple")))
                .andExpect(jsonPath("$.bill_amount",is(25000.0)))
                .andDo(print());
    }

    @Test
    public void addBookingDetailsSuccess() throws Exception{
        Booking addBooking = Booking.builder()
                        .booking_id(1L)
                        .duration(5)
                        .start_date(new Date(11-07-2000))
                        .end_date(new Date(15-07-2000))
                        .mode_of_payement("online")
                        .modeOfBokking("offline")
                        .bill_amount(50000)
                        .roomList(rooms)
                        .customerList(customerList1)
                        .build();
        Mockito.when(bookingServiceImp.addBookingDetails(Mockito.any())).thenReturn(addBooking);
        String content = objectWriter.writeValueAsString(addBooking);
        System.out.println(content);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.duration",is(5)))
                .andExpect(jsonPath("$.customerList[0].customer_name",is("mana")))
                .andExpect(jsonPath("$.roomList[0].type",is("simple")))
                .andDo(print());
    }

    @Test
    public void updateBookingSuccess() throws Exception {
        Booking updateBooking = Booking.builder()
                .booking_id(1L)
                .duration(7)
                .start_date(new Date(11-07-2000))
                .end_date(new Date(11-07-2000))
                .mode_of_payement("online")
                .modeOfBokking("offline")
                .bill_amount(13000)
                .roomList(rooms)
                .customerList(customerList1)
                .build();

        //Mockito.when(bookingServiceImp.updateBooking(anyLong(), Mockito.any())).thenReturn(updateBooking);
        Mockito.when(bookingServiceImp.getBookingDetailsById(booking1.getBooking_id())).thenReturn(booking1);
        Mockito.when(bookingServiceImp.addBookingDetails(Mockito.any())).thenReturn(updateBooking);
        String updatedContent = objectWriter.writeValueAsString(updateBooking);
        //MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/booking/update/{id}",1L)
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void deleteBookingDetailsSuccess() throws Exception {
        Mockito.when(bookingServiceImp.getBookingDetailsById(booking1.getBooking_id())).thenReturn(booking1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/booking/delete/{id}",1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",notNullValue()))
                .andDo(print());
    }
}
