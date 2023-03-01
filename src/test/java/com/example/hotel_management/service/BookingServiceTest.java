package com.example.hotel_management.service;

import com.example.hotel_management.entity.Booking;
import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;
import com.example.hotel_management.repository.BookingRepository;
import com.example.hotel_management.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = AutoConfigureMockMvc.class)
@WebMvcTest(BookingService.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private BookingServiceImp bookingServiceImp;
    @InjectMocks
    private RoomServiceImp roomServiceImp;

    Customer customer1 = new Customer(1L, "mana", "india", "123", 20);
    Customer customer2 = new Customer(2L, "shruti", "india", "123", 25);
    Customer customer3 = new Customer(3L, "ayush", "india", "123", 29);
    Customer customer4 = new Customer(4L, "rajat", "india", "123", 30);
    Room room = new Room(1L, "simple", 2, true, 5000, false, true);
    List<Customer> customerList1 = new ArrayList<>(Arrays.asList(customer1, customer2));
    List<Customer> customerList2 = new ArrayList<>(Arrays.asList(customer3, customer4));
    List<Room> rooms = new ArrayList<>(Arrays.asList(room));
    Booking booking1 = new Booking(1L, 5, new Date(11 - 07 - 2000), new Date(19 - 07 - 2000), customerList1, "simple", rooms, "offline", 25000, "online");
    Booking booking2 = new Booking(2L, 5, new Date(11 - 07 - 2000), new Date(19 - 07 - 2000), customerList2, "luxury", rooms, "offline", 35000, "online");


    @Test
    public void getAllBookingDetailsTest() throws Exception {
        List<Booking> bookingList = new ArrayList<>(Arrays.asList(booking1, booking2));

        when(bookingRepository.findAll()).thenReturn(bookingList);
        assertNotNull(bookingList);
        assertEquals(2, bookingServiceImp.getAllBookingDetails().size());
        verify(bookingRepository).findAll();
    }

    @Test
    public void getBookingDetailsByIdTest() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking1));
        assertEquals(5, bookingServiceImp.getBookingDetailsById(1L).getDuration());
        verify(bookingRepository).findById(1L);
    }

    @Test
    public void addBookingDetailsTest() throws DataNotFoundException {
        Booking newBooking = Booking.builder()
                .booking_id(3L)
                .duration(15)
                .start_date(new Date(11 - 07 - 2000))
                .end_date(new Date(15 - 07 - 2000))
                .mode_of_payement("online")
                .modeOfBokking("offline")
                .bill_amount(50000)
                .roomList(rooms)
                .customerList(customerList1)
                .build();
        when(bookingRepository.save(Mockito.any())).thenReturn(newBooking);
        Booking booking = bookingServiceImp.addBookingDetails(newBooking);
        assertNotNull(booking);
        assertEquals(booking, newBooking);

        verify(bookingRepository).save(newBooking);
    }

    @Test
    public void deleteBookingDetailsTest() throws IdNotFoundException, DataNotFoundException {
        Booking newBooking = Booking.builder()
                .booking_id(3L)
                .duration(15)
                .start_date(new Date(11 - 07 - 2000))
                .end_date(new Date(15 - 07 - 2000))
                .mode_of_payement("online")
                .modeOfBokking("offline")
                .bill_amount(50000)
                .roomList(rooms)
                .customerList(customerList1)
                .build();

        Room record = Room.builder()
                .room_number(101L)
                .type("simple")
                .occupancy(2)
                .availability(true)
                .price_per_day(5000)
                .checked(false)
                .isCheckedOut(true)
                .build();
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(newBooking));
        when(bookingRepository.save(Mockito.any())).thenReturn(newBooking);
        when(roomRepository.save(Mockito.any())).thenReturn(room);
        bookingServiceImp.deleteBookingDetails(3L);
        roomServiceImp.addRooms(record);

        verify(bookingRepository).findById(3L);
        verify(bookingRepository).deleteById(3L);
        verify(roomRepository).save(record);
    }

    @Test
    public void methodToAllocateRoomTest() throws DataNotFoundException {
        Room record = Room.builder()
                .room_number(1L)
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
        assertEquals(room, record);

        verify(roomRepository).save(record);
        when(roomRepository.findRoomByTypeAndAvailability(anyString(), anyBoolean())).thenReturn(rooms);
        }
}
