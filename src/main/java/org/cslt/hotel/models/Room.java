package org.cslt.hotel.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROOM")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private int  room_number;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private String room_type;

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 2)")
    private Double room_price;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private int room_capacity;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String room_description;

    @Column(nullable = false, columnDefinition = "VARCHAR(2)")
    private String is_PetFriendly;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private String room_availability;

}
