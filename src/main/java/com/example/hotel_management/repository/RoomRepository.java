package com.example.hotel_management.repository;

import com.example.hotel_management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findRoomByTypeAndAvailability(String type, boolean availability);
}
