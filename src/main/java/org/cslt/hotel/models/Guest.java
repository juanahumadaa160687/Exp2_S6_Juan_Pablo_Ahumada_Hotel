package org.cslt.hotel.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GUEST")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guest_id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String first_name;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String last_name;

    @Column(nullable = false, columnDefinition = "VARCHAR(100) UNIQUE")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private String phone_number;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String address;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String city;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String country;

}
