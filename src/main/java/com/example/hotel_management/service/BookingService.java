package com.example.hotel_management.service;

import com.example.hotel_management.entity.Booking;
import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;

import java.util.List;

public interface BookingService {
    List<Booking> getAllBookingDetails() throws DataNotFoundException;

    Booking addBookingDetails(Booking booking) throws DataNotFoundException;

    Booking getBookingDetailsById(long id) throws IdNotFoundException;

    void deleteBookingDetails(long id) throws IdNotFoundException, DataNotFoundException;

    Booking updateBooking(long id, Booking booking);

    Booking methodToAllocateRoom(Booking booking, List<Room> rooms) throws DataNotFoundException;

    int countCustomers(Booking booking);

    Booking getRoomsToAllocate(Booking booking) throws DataNotFoundException;
}
