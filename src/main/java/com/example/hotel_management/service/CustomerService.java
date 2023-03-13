package com.example.hotel_management.service;

import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers() throws DataNotFoundException;

    Customer addcustomers(Customer customer);

    Customer getCustomerById(long id) throws IdNotFoundException;

    Customer updateCustomer(long custId, Customer customer) throws IdNotFoundException;

    void deleteCustomer(long id) throws IdNotFoundException;

    List<Customer> getCustomersOfParticularBookingId(long booking_id) throws IdNotFoundException;
}
