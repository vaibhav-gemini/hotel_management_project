package com.example.hotel_management.repository;

import com.example.hotel_management.entity.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        Room room = new Room(101,"luxury",0,true,5000,false,true);
        roomRepository.save(room);
        System.out.println(room);
        System.out.println("Calling before each");
    }


    @AfterEach
    void tearDown() {
        System.out.println("Calling last method to clear database");
        roomRepository.deleteAll();
        System.out.println(roomRepository.findAll());
    }

    @Test
    void findRoomByTypeAndAvailability() {
        System.out.println("calling findroombytype function");
        String type = null;
        boolean available = false;
        List<Room> rooms = roomRepository.findAll();
        for(Room room1 : rooms) {
            type = room1.getType();
            available = room1.isAvailability();
        }
        assertThat(type).isEqualTo("luxury");
        assertThat(available).isTrue();
    }

}