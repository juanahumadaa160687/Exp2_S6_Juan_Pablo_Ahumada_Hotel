package org.cslt.hotel.services;

import org.cslt.hotel.models.booking.Booking;
import org.cslt.hotel.models.booking.Pets;
import org.cslt.hotel.models.booking.Status;
import org.cslt.hotel.models.guest.DocType;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.models.room.RoomType;
import org.cslt.hotel.repositories.BookingRepository;
import org.cslt.hotel.repositories.GuestRepository;
import org.cslt.hotel.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository repository;


    @InjectMocks
    private BookingServiceImpl service;


    private Booking booking;
    private Room room;
    private Guest guest;

    @BeforeEach
    public void setup() {
        guest = new Guest();
        guest.setGuest_id(1L);
        guest.setDoc_type(DocType.PASAPORTE);
        guest.setDoc_number("123456789");
        guest.setFirst_name("John");
        guest.setLast_name("Doe");
        guest.setEmail("jdoe@email.com");
        guest.setPhone_number("1234567890");
        guest.setAddress("123 Main St");
        guest.setCity("New York");
        guest.setCountry("USA");

        room = new Room();
        room.setRoom_id(1L);
        room.setRoom_number(101);
        room.setRoom_type(RoomType.SINGLE);
        room.setRoom_price(100.0);
        room.setRoom_description("A cozy single room with a comfortable bed.");
        room.setRoom_capacity(3);
        room.setPet_friendly(PetFriendly.PERMITIDO);

        booking = new Booking();
        booking.setBooking_id(1L);
        booking.setCode("ABC123");
        booking.setCheckin_date(Date.valueOf("2024-07-01"));
        booking.setCheckout_date(Date.valueOf("2024-07-10"));
        booking.setAdults(2);
        booking.setChildren(1);
        booking.setTotal_price(500.0);
        booking.setStatus(Status.PENDIENTE);
        booking.setPets(Pets.SI);
        booking.setGuest(guest);
        booking.setRoom(room);

    }

    @Test
    @DisplayName("Test Get All Bookings")
    public void testGetAllBookings() {
        List<Booking> expectedBookings = Arrays.asList(booking);
        when(repository.findAll()).thenReturn(expectedBookings);

        assertEquals(expectedBookings, service.getAllBookings());
    }

    @Test
    @DisplayName("Test Get Booking by ID")
    public void testGetBookingById() {
        when(repository.findById(1L)).thenReturn(Optional.of(booking));
        assertEquals(booking, service.getBookingById(1L));
    }

    @Test
    @DisplayName("Test Create Booking")
    public void testCreateBooking() {

        when(repository.save(booking)).thenReturn(booking);

        assertEquals(booking, service.newBooking(booking));
    }

    @Test
    @DisplayName("Test Confirm Booking")
    public void testConfirmBooking() {
        when(repository.findById(1L)).thenReturn(Optional.of(booking));
        when(repository.save(booking)).thenReturn(booking);
        Booking result = service.confirmBooking(1L, booking);

        assertEquals(booking, result);
        assertEquals(booking.getStatus(), result.getStatus());
        verify(repository, times(1)).save(booking);
    }

    @Test
    @DisplayName("Test Cancel Booking")
    public void testCancelBooking() {
        service.deleteBookingById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

}
