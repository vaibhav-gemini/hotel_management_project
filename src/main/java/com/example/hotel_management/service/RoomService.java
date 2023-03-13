package com.example.hotel_management.service;

import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;

import java.util.List;

public interface RoomService {

    List<Room> getRooms() throws DataNotFoundException;

    Room addRooms(Room room) throws DataNotFoundException;

    Room getRoomById(long id) throws IdNotFoundException;

    void addDefaultroom();

    Room updateRoom(Room room, long id) throws IdNotFoundException;

    void deleteRoom(long id) throws IdNotFoundException;
}
