package com.example.hotel_management.controller;

import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;
import com.example.hotel_management.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping(path = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * To get List of all Customers.
     *
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to retrieve all customer information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all the details correctly", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Data not available", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Customer>> getCustomers() throws DataNotFoundException {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    /**
     * To get Details of Customer with Customer's ID.
     *
     * @param customerId - Customer's ID
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to retrieve customer information for a certain customer ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all the details correctly", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Data not available", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(path = "/{customerId}", produces = "application/json")
    public ResponseEntity<Customer> getCustomerById(@PathVariable final long customerId) throws DataNotFoundException {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    /**
     * To Add a new Customer.
     *
     * @param customer - Customer's details
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to add customers details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Details added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to add data, please check logs.", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Customer> addcustomers(@RequestBody final Customer customer) {
        return new ResponseEntity<>(customerService.addcustomers(customer), HttpStatus.CREATED);
    }

    /**
     * to Update Customer Details.
     *
     * @param customerId - Customer's ID
     * @param customer   - Customer's details
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to update customer details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details updated successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to update data, please check logs.", content = {@Content(mediaType = "application/json")})
    })
    @PutMapping(path = "/update/{customerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> updateCustomer(@PathVariable final long customerId, @RequestBody final Customer customer) throws IdNotFoundException {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customer), HttpStatus.OK);
    }

    /**
     * to Delete a Customer.
     *
     * @param customerId - Customer's ID
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to delete customer details. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details deleted successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to delete data, please check logs.", content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping(path = "/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable final long customerId) throws IdNotFoundException {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>("Data Deleted for ID " + customerId, HttpStatus.OK);
    }

    /**
     * To get Details of Customer with Customer's ID.
     *
     * @param bookingId - Booking's ID
     * @return ResponseEntity
     */

    @Operation(summary = "This is used to retrieve a list of customers associated with a specific booking ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched details correctly", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Unable to add data, please check logs.", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(path = "/bookingId/{bookingId}", produces = "application/json")
    public ResponseEntity<List<Customer>> getCustomersBybookingid(@PathVariable final long bookingId) {
        return new ResponseEntity<>(customerService.getCustomersOfParticularBookingId(bookingId), HttpStatus.OK);
    }
}
