package org.cslt.hotel.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "BOOKING")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long booking_id;

    @Column(length = 100, nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    private String code;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) CHECK(status IN ('CONFIRMADA', 'CANCELADA', 'PENDIENTE'))")
    private String status;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date checkin_date;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date checkout_date;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private int adults;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private int children;

    @Column(nullable = false, columnDefinition = "VARCHAR(3) CHECK(pets IN ('SI', 'NO'))")
    private String pets;

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 2)")
    private Double total_price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOOKING_ROOM"))
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOOKING_GUEST"))
    private Guest guest;

}
