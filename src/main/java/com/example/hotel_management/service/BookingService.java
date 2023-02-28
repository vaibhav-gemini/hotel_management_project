package com.example.hotel_management.service;

import com.example.hotel_management.entity.Booking;
import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;

import java.util.List;

public interface BookingService {
    public List<Booking> getAllBookingDetails() throws DataNotFoundException;
    public Booking addBookingDetails(Booking booking) throws DataNotFoundException;
    public Booking getBookingDetailsById(long id) throws IdNotFoundException;
    public void deleteBookingDetails(long id) throws IdNotFoundException, DataNotFoundException;
    public Booking updateBooking(long id , Booking booking);
    public Booking methodToAllocateRoom(Booking booking, List<Room> rooms) throws DataNotFoundException;
    public int countCustomers(Booking booking);
    public Booking getRoomsToAllocate(Booking booking) throws DataNotFoundException;
}
