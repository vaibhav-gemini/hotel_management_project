package com.example.hotel_management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Hotel API" , version = "2.0" ,description = "Spring boot CRUD application for  Hotel Management System", contact = @Contact(name = "Vaibhav Oberoi" , email = "vaibhav.oberoi@geminisolutions.com")))
public class HotelManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelManagementApplication.class, args);
	}

}
