package com.example.hotel_management.service;

import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = AutoConfigureMockMvc.class)
@WebMvcTest(CustomerService.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImp customerServiceImp;
    Customer customer1 = new Customer(1L,"mana","india","123",20);
    Customer customer2 = new Customer(2L,"shruti","india","123",25);



    @Test
    public void getCustomersTest() throws Exception{
        List<Customer> customerList = new ArrayList<>(Arrays.asList(customer1,customer2));

        when(customerRepository.findAll()).thenReturn(customerList);
        assertNotNull(customerList);
        assertEquals(2,customerServiceImp.getCustomers().size());
        verify(customerRepository).findAll();
    }

    @Test
    public void getCustomerByIdTest(){
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));
        assertEquals(20,customerServiceImp.getCustomerById(1L).getCustomer_age());
        verify(customerRepository).findById(1L);
    }

    @Test
    public void addcustomersTest() {
        Customer customer = Customer.builder()
                .customer_id(1L)
                .customer_address("Delhi")
                .customer_name("Rohan")
                .customer_number("123456789")
                .customer_age(25)
                .build();

        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        Customer newCustomer = customerServiceImp.addcustomers(customer);
        assertNotNull(newCustomer);
        assertEquals(newCustomer,customer);

        verify(customerRepository).save(customer);
    }


    @Test
    public void updateCustomerTest(){
        Customer customer = Customer.builder()
                .customer_id(1L)
                .customer_address("Delhi")
                .customer_name("Rohan")
                .customer_number("123456789")
                .customer_age(25)
                .build();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        customer.setCustomer_name("shruti");
        Customer newCustomer = customerServiceImp.updateCustomer(customer.getCustomer_id(), customer);
        assertNotNull(newCustomer);
        assertEquals("shruti",customer.getCustomer_name());

        verify(customerRepository).findById(1L);
        verify(customerRepository).save(customer);
    }

    @Test
    public void deleteCustomerTest(){
        Customer customer = Customer.builder()
                .customer_id(1L)
                .customer_address("Delhi")
                .customer_name("Rohan")
                .customer_number("123456789")
                .customer_age(25)
                .build();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);

        customerServiceImp.deleteCustomer(1L);
        verify(customerRepository).findById(1L);
        verify(customerRepository).deleteById(1L);
    }

}
