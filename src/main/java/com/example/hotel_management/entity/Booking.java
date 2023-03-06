package com.example.hotel_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Builder
@Table(name = "Booking_details")
@NoArgsConstructor
public class Booking {
    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long booking_id;
    @Column(name = "duration")
    private long duration;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "start_date")
    private Date start_date;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "end_date")
    private Date end_date;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_list_customer_id")
    private List<Customer> customerList;
    @Column(name = "type_of_room_prefered")
    private String typeOfRoomPrefered;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_list_booking_id")
    private List<Room> roomList;
    @Column(name = "modeOfBokking")
    private String modeOfBokking;
    @Column(name = "bill_amount")
    private double bill_amount = 0;
    @Column(name = "mode_of_payement")
    private String mode_of_payement;

    public void setStart_date(Date start_date) {
        this.start_date = new Date(start_date.getTime());
    }

    public void setEnd_date(Date end_date) {
        this.end_date = new Date(end_date.getTime());
    }

    public Date getEnd_date() {
        return new Date(end_date.getTime());
    }

    public Date getStart_date() {
        return new Date(start_date.getTime());
    }
}
