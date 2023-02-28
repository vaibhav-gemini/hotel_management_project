package com.example.hotel_management.controller;

import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;
import com.example.hotel_management.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    /**
     * To get List of all Rooms
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to retrieve all rooms information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all the details correctly",content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Data not available",content = {@Content(mediaType = "application/json")})
    })
    @GetMapping()
    public ResponseEntity<List<Room>> getRooms() throws DataNotFoundException {
        return new ResponseEntity<List<Room>>(roomService.getRooms(), HttpStatus.OK);
    }

    /**
     * To get Details of Room with Room's ID
     *
     * @param roomId - Room's ID
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to retrieve room information for a certain room number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all the details correctly",content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Data not available",content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(path = "/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable long roomId) throws DataNotFoundException {
        return new ResponseEntity<Room>(roomService.getRoomById(roomId), HttpStatus.OK);
    }

    /**
     * To Add a new Room
     * @param room - Room's details
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to add rooms details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Details added successfully",content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to add data, please check logs.",content = {@Content(mediaType = "application/json")})
    })
    @PostMapping()
    public ResponseEntity<Room> addRooms(@RequestBody Room room) throws DataNotFoundException {
        return new ResponseEntity<Room>(roomService.addRooms(room), HttpStatus.CREATED);
    }

    /**
     * to Update Room Details
     *
     * @param roomId - Customer's ID
     * @param room   - Customer's details
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to update rooms details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details updated successfully",content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to update data, please check logs.",content = {@Content(mediaType = "application/json")})
    })
    @PutMapping(path = "/update/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable long roomId , @RequestBody Room room){
        return new ResponseEntity<>(roomService.updateRoom(room,roomId), HttpStatus.OK);
    }

    /**
     * to Delete a Customer
     *
     * @param roomId - Customer's ID
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to delete room details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details deleted successfully",content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to delete data, please check logs.",content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping(path = "/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable long roomId) throws IdNotFoundException {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<String>("Data Delete for ID" + roomId, HttpStatus.OK);
    }

}
