package org.cslt.hotel.models;


import org.cslt.hotel.models.booking.Booking;
import org.cslt.hotel.models.booking.Pets;
import org.cslt.hotel.models.booking.Status;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.room.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    @Test
    @DisplayName("Test Getters & Setters Booking")
    public void testGettersAndSetters() {

        Booking booking = new Booking();
        booking.setBooking_id(1L);
        booking.setCode("ABC123");
        booking.setCheckin_date(Date.valueOf("2024-07-01"));
        booking.setCheckout_date(Date.valueOf("2024-07-10"));
        booking.setAdults(2);
        booking.setChildren(1);
        booking.setTotal_price(500.0);
        booking.setStatus(Status.CONFIRMADA);
        booking.setPets(Pets.SI);
        booking.setGuest(new Guest());
        booking.setRoom(new Room());

        assertEquals(1L, booking.getBooking_id());
        assertEquals("ABC123", booking.getCode());
    }

    @Test
    @DisplayName("Test Constructor Booking")
    public void testConstructor() {
        Booking booking = new Booking(1L, "ABC123", Status.PENDIENTE, Date.valueOf("2024-07-01"), Date.valueOf("2024-07-10"),  2, 1, Pets.NO, 500.0, new Room(), new Guest());
        assertEquals(1L, booking.getBooking_id());
        assertEquals("ABC123", booking.getCode());
    }
}
