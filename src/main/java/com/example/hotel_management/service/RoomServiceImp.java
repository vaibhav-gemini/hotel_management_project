package com.example.hotel_management.service;

import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;
import com.example.hotel_management.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class RoomServiceImp implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    /**
     * to get List of all Rooms
     * @return List of Rooms
     */
    @Override
    public List<Room> getRooms() throws DataNotFoundException {
        List<Room> rooms = roomRepository.findAll();
        if(rooms.size()==0){
            throw new DataNotFoundException("Please enter some data list is empty");
        }
        log.info(rooms.size()+" Total number of rooms ");
        return rooms;
    }

    /**
     * to Add a new Room
     * @param room - Room's Details
     * @return Room's Details
     */
    @Override
    public Room addRooms(Room room) throws DataNotFoundException {
        if(room.getType()==null || Objects.isNull(room.getPrice_per_day()) ){
            throw new DataNotFoundException("Some data missing!");
        }
        return roomRepository.save(room);
    }

    /**
     * to get Room details with room's ID
     *
     * @param id - Customer's ID
     * @return Customer's Details
     */
    @Override
    public Room getRoomById(long id) throws IdNotFoundException{
        Optional<Room> room = roomRepository.findById(id);
        if(room.isEmpty()){
            throw new IdNotFoundException("No room for this id");
        }
        return room.get();
    }

    /**
     * to add default Rooms
     *
     */
    @Override
    public void addDefaultroom() {
        Room room1 = new Room(101, "deluxe", 2, true, 10000, false, true);
        Room room2 = new Room(102, "deluxe", 2, true, 10000, false, true);
        Room room3 = new Room(201, "simple", 2, true, 5000, false, true);
        Room room4 = new Room(202, "simple", 2, true, 5000, false, true);
        Room room5 = new Room(401, "luxury", 2, true, 15000, false, true);
        Room room6 = new Room(402, "luxury", 2, true, 15000, false, true);
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        roomRepository.save(room4);
        roomRepository.save(room5);
        roomRepository.save(room6);
    }

    /**
     * to Update Room Details
     *
     * @param id - Room's ID
     * @param room   - Room's Details
     * @return Room's Details
     */
    @Override
    public Room updateRoom(Room room, long id) throws IdNotFoundException {
        if(room == null || Objects.isNull(room.getRoom_number())){
            throw new IdNotFoundException("No ID present please check again.");
        }
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isEmpty()){
            throw new RuntimeException("Id not found bro");
        }
        Room existingRoom = roomOptional.get();
        existingRoom.setChecked(room.isChecked());
        existingRoom.setAvailability(room.isAvailability());
        existingRoom.setOccupancy(room.getOccupancy());
        existingRoom.setType(room.getType());
        existingRoom.setPrice_per_day(room.getPrice_per_day());
        existingRoom.setCheckedOut(room.isCheckedOut());
        log.info("Room details updated! ");
        return roomRepository.save(existingRoom);

    }

    /**
     * to Delete a Room
     *
     * @param id - room's ID
     */
    @Override
    public void deleteRoom(long id) throws IdNotFoundException {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isEmpty()){
            throw new IdNotFoundException("Room with room id "+ id + " not found");
        }
        log.info("Room with ID " + id + "deleted successfully");
        roomRepository.deleteById(id);
    }
}
