package com.example.hotel_management.service;

import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;

import java.util.List;

public interface CustomerService {
    public List<Customer> getCustomers() throws DataNotFoundException;
    public Customer addcustomers(Customer customer);
    public Customer getCustomerById(long id) throws IdNotFoundException;
    public Customer updateCustomer(long custId,Customer customer) throws IdNotFoundException;
    public void deleteCustomer(long id) throws IdNotFoundException;
    public List<Customer> getCustomersOfParticularBookingId(long booking_id) throws IdNotFoundException;
}
