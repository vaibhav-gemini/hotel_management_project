package com.example.hotel_management.controller;

import com.example.hotel_management.entity.Booking;
import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.service.CustomerServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();//convert json to string and vise versa

    ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private CustomerServiceImp customerServiceImp;

    Customer customer1 = new Customer(1L,"mana","india","123",20);
    Customer customer2 = new Customer(2L,"shruti","india","123",25);
    List<Customer> customerList = new ArrayList<>(Arrays.asList(customer1,customer2));
    Booking booking1 = new Booking(1L,5,new Date(11-07-2000),new Date(19-07-2000),customerList,"simple",null,"offline",25000,"online");
    @Test
    public void getAllCustomersSuccess() throws Exception {
        List<Customer> customerList = new ArrayList<>(Arrays.asList(customer1,customer2));
        Mockito.when(customerServiceImp.getCustomers()).thenReturn(customerList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customer_name", is("mana")))
                .andDo(print());
    }

    @Test
    public void getCustomerByIdSuccess() throws Exception {
        Mockito.when(customerServiceImp.getCustomerById(anyLong())).thenReturn(customer1);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.customer_name",is("mana")))
                .andExpect(jsonPath("$.customer_address",is("india")))
                .andExpect(jsonPath("$.customer_number",is("123")))
                .andDo(print());
    }
    @Test
    public void addCustomersSuccess() throws Exception{
        Customer customer = Customer.builder()
                .customer_id(1L)
                .customer_name("vaibhav")
                .customer_address("india")
                .customer_age(25)
                .customer_number("123456")
                .build();

        Mockito.when(customerServiceImp.addcustomers(Mockito.any())).thenReturn(customer);
        String content = objectWriter.writeValueAsString(customer);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.customer_name",is("vaibhav")))
                .andDo(print());
    }
    @Test
    public void updateCustomerSuccess() throws Exception {
        Customer customerUpdate = Customer.builder()
                        .customer_id(1L)
                       .customer_address("Delhi")
                       .customer_name("Rohan")
                       .customer_number("123456789")
                       .customer_age(25)
                       .build();

        System.out.println(customerUpdate);
        //Mockito.when(customerServiceImp.updateCustomer( anyLong(),Mockito.any())).thenReturn(customerUpdate);
         Mockito.when(customerServiceImp.getCustomerById(customer1.getCustomer_id())).thenReturn(customer1);
         Mockito.when(customerServiceImp.addcustomers(Mockito.any())).thenReturn(customerUpdate);
        String updatedContent = objectWriter.writeValueAsString(customerUpdate);
        //MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/customers/update/{custId}",1L)
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer_name",is("Rohan")))
                .andExpect(jsonPath("$.customer_number",is("123456789")))
                .andExpect(jsonPath("$.customer_age",is(25)))
                .andDo(print());
    }
    @Test
    public void deleteCustomerSuccess() throws Exception {
        Mockito.when(customerServiceImp.getCustomerById(customer1.getCustomer_id())).thenReturn(customer1);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/customers/delete/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void getCustomersBybookingidSuccess() throws Exception {

        Mockito.when(customerServiceImp.getCustomersOfParticularBookingId(anyLong())).thenReturn(customerList);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/bookingId/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].customer_name",is("mana")))
                .andExpect(jsonPath("$[1].customer_name",is("shruti")))
                .andDo(print());
    }
}
