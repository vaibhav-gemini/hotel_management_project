package com.example.hotel_management.controller;

import com.example.hotel_management.entity.Booking;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping(path = "/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    /**
     * To get List of all Bookings done.
     *
     * @return ResponseEntity
     */

    private int flag = 0;

    @Operation(summary = "This is used to fetch All the Booking's done. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all the details correctly", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Data not available", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Booking>> getAllBooking() throws DataNotFoundException {
        return new ResponseEntity<>(bookingService.getAllBookingDetails(), HttpStatus.OK);
    }

    /**
     * To Add a new Booking.
     *
     * @param booking - Booking's details
     * @return ResponseEntity
     */


    @Operation(summary = "This is used to add booking details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Details added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to add data, please check logs.", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping(produces = "application/json")
    public ResponseEntity<Booking> addBookingDetails(@Valid @RequestBody final Booking booking) throws DataNotFoundException {
        Booking newBooking = bookingService.getRoomsToAllocate(booking);
        return new ResponseEntity<>(bookingService.addBookingDetails(newBooking), HttpStatus.CREATED);
    }

    /**
     * To get Details of Booking with Booking's ID.
     *
     * @param bookingId - Booking's ID
     * @return ResponseEntity
     */


    @Operation(summary = "This is used to fetch booking details of a particular Booking ID. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched details correctly", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "ID not available", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(path = "/{bookingId}", produces = "application/json")
    public ResponseEntity<Booking> getBookingDetailsById(@PathVariable final long bookingId) {
        return new ResponseEntity<>(bookingService.getBookingDetailsById(bookingId), HttpStatus.OK);
    }

    /**
     * to Update Booking Details.
     *
     * @param bookingId - Booking's ID
     * @param booking   - Booking's details
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to update booking details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details updated successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to update data, please check logs.", content = {@Content(mediaType = "application/json")})
    })
    @PutMapping(path = "/update/{bookingId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Booking> updateBooking(@PathVariable final long bookingId, @RequestBody final Booking booking) {
        return new ResponseEntity<>(bookingService.updateBooking(bookingId, booking), HttpStatus.OK);
    }

    /**
     * to Delete a booking.
     *
     * @param bookingId - Customer's ID
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to delete booking details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details deleted successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to delete data, please check logs.", content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping(path = "/delete/{bookingId}")
    public ResponseEntity<String> deleteBookingDetails(@PathVariable final long bookingId) throws DataNotFoundException {
        bookingService.deleteBookingDetails(bookingId);
        return new ResponseEntity<>("Booking details Deleted with ID - " + bookingId, HttpStatus.OK);
    }
}
