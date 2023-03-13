package com.example.hotel_management.service;

import com.example.hotel_management.entity.Booking;
import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;
import com.example.hotel_management.repository.BookingRepository;
import com.example.hotel_management.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingRepository bookingRepository;

    /**
     * to get List of all Customers.
     *
     * @return List of Customers
     */
    @Override
    public List<Customer> getCustomers() throws DataNotFoundException {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.size() == 0) {
            throw new DataNotFoundException("Please enter some data list is empty");
        }
        log.info(customerList.size() + " Total number of customers present");
        return customerList;
    }

    /**
     * to Add a new Customer.
     *
     * @param customer - Customer's Details
     * @return Customer's Details
     */
    @Override
    public Customer addcustomers(final Customer customer) {
        //No exception needed as values are already @NotEmpty.
        return customerRepository.save(customer);
    }

    /**
     * to get Customer details with Customer's ID.
     *
     * @param id - Customer's ID
     * @return Customer's Details
     */
    @Override
    public Customer getCustomerById(final long id) throws IdNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new IdNotFoundException("No customer for this id available");
        }
        return customer.get();

    }

    /**
     * to Update Customer Details.
     *
     * @param custId   - Customer's ID
     * @param customer - Customer's Details
     * @return Customer's Details
     */
    @Override
    public Customer updateCustomer(final long custId, final Customer customer) throws IdNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(custId);
        if (customerOptional.isEmpty()) {
            throw new IdNotFoundException("Customer with Id " + custId + " not found!");
        }
        Customer existingCustomer = customerOptional.get();
        existingCustomer.setCustomer_number(customer.getCustomer_number());
        existingCustomer.setCustomer_age(customer.getCustomer_age());
        existingCustomer.setCustomer_address(customer.getCustomer_address());
        existingCustomer.setCustomer_age(customer.getCustomer_age());
        existingCustomer.setCustomer_name(customer.getCustomer_name());
        log.info("Customer details updated! ");
        return customerRepository.save(existingCustomer);
    }

    /**
     * to Delete a Customer.
     *
     * @param id - customer's ID
     */
    @Override
    public void deleteCustomer(final long id) throws IdNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new IdNotFoundException("Customer not found with id " + id);
        }
        log.info("Customer with id " + id + "deleted successfully!");
        customerRepository.deleteById(id);
    }

    /**
     * to get Customer details for a particular booking ID.
     *
     * @param booking_id - booking ID
     * @return Customer's Details
     */

    @Override
    public List<Customer> getCustomersOfParticularBookingId(final long booking_id) throws IdNotFoundException {
        if (Objects.isNull(booking_id)) {
            throw new IdNotFoundException("Booking Id is null" + booking_id);
        }
        Optional<Booking> booking = bookingRepository.findById(booking_id);
        if (booking.isEmpty()) {
            throw new IdNotFoundException("Booking details not found with id " + booking_id);
        }
        return booking.get().getCustomerList();
    }


}
