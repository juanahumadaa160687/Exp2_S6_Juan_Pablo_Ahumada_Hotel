package org.cslt.hotel.models.booking;

import jakarta.persistence.*;
import lombok.*;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.room.Room;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) CHECK(status IN ('CONFIRMADA', 'PENDIENTE'))")
    private Status status;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date checkin_date;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date checkout_date;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private int adults;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private int children;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(3) CHECK(pets IN ('SI', 'NO'))")
    private Pets pets;

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 2)")
    private Double total_price;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOOKING_ROOM"))
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOOKING_GUEST"))
    private Guest guest;

}
