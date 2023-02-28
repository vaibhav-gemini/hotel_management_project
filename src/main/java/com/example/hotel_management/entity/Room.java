package com.example.hotel_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "rooms_details")
public class Room {
    @Id
    @Column(name = "room_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long room_number;
    @Column(name = "type")
    private String type;
    @Column(name = "occupancy")
    private int occupancy;
    @Column(name = "availability")
    private boolean availability;
    @Column(name = "price_per_day")
    private double price_per_day;
    @Column(name = "checked")
    private boolean checked;
    @Column(name = "is_Checked_out")
    private boolean isCheckedOut;

}
