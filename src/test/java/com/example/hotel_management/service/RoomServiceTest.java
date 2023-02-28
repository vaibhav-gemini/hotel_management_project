package com.example.hotel_management.service;

import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = AutoConfigureMockMvc.class)
@WebMvcTest(value = RoomService.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImp roomServiceImp;

    Room room1 = new Room(101L,"luxury",2,true,5000,true,false);
    Room room2 = new Room(201L,"simple",2,true,8000,true,false);
    @Test
    public void getRoomsTest() throws DataNotFoundException {
        List<Room> roomList = new ArrayList<>(Arrays.asList(room1,room2));
        when(roomRepository.findAll()).thenReturn(roomList);
        assertNotNull(roomList);
        assertEquals(2,roomServiceImp.getRooms().size());

        verify(roomRepository).findAll();
    }

    @Test
    public void getRoomByIdTest(){
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room1));
        assertEquals("luxury",roomServiceImp.getRoomById(101L).getType());
        verify(roomRepository).findById(101L);
    }
    @Test
    public void addRoomsTest() throws DataNotFoundException {
        Room record = Room.builder()
                .room_number(101L)
                .type("simple")
                .occupancy(2)
                .availability(true)
                .price_per_day(5000)
                .checked(false)
                .isCheckedOut(true)
                .build();

        when(roomRepository.save(Mockito.any())).thenReturn(record);
        Room room = roomServiceImp.addRooms(record);
        assertNotNull(room);
        assertEquals(room,record);

        verify(roomRepository).save(record);
    }
    @Test
    public void updateRoomsTest(){
        Room record = Room.builder()
                .room_number(101L)
                .type("simple")
                .occupancy(2)
                .availability(true)
                .price_per_day(5000)
                .checked(false)
                .isCheckedOut(true)
                .build();
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(record));
        when(roomRepository.save(Mockito.any())).thenReturn(record);
        record.setType("ultraLuxury");
        Room newRoom = roomServiceImp.updateRoom(record,record.getRoom_number());
        assertNotNull(newRoom);
        assertEquals("ultraLuxury",record.getType());
        verify(roomRepository).findById(101L);
        verify(roomRepository).save(record);
    }
    @Test
    public void deleteRoomTest(){
        Room record = Room.builder()
                .room_number(101L)
                .type("simple")
                .occupancy(2)
                .availability(true)
                .price_per_day(5000)
                .checked(false)
                .isCheckedOut(true)
                .build();

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(record));
        when(roomRepository.save(Mockito.any())).thenReturn(record);

        roomServiceImp.deleteRoom(101L);

        verify(roomRepository).findById(101L);
        verify(roomRepository).deleteById(101L);
    }
}
