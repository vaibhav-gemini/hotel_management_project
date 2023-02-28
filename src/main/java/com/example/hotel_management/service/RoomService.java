package com.example.hotel_management.service;

import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;

import java.util.List;

public interface RoomService {

    public List<Room> getRooms() throws DataNotFoundException;
    public Room addRooms(Room room) throws DataNotFoundException;
    public Room getRoomById(long id) throws IdNotFoundException;
    public void addDefaultroom();
    public Room updateRoom(Room room , long id) throws IdNotFoundException;
    public void deleteRoom(long id) throws IdNotFoundException;
}
