package org.cslt.hotel.repository;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Booking booking;
    private Room room;
    private Guest guest;

    @BeforeEach
    public void setup() {
        guest = new Guest();
        guest.setDoc_type(DocType.PASAPORTE);
        guest.setDoc_number("123456789");
        guest.setFirst_name("John");
        guest.setLast_name("Doe");
        guest.setEmail("jdoe@email.com");
        guest.setPhone_number("1234567890");
        guest.setAddress("123 Main St");
        guest.setCity("New York");
        guest.setCountry("USA");

        guestRepository.save(guest);

        room = new Room();
        room.setRoom_number(101);
        room.setRoom_type(RoomType.SINGLE);
        room.setRoom_price(100.0);
        room.setRoom_description("A cozy single room with a comfortable bed.");
        room.setRoom_capacity(3);
        room.setPet_friendly(PetFriendly.PERMITIDO);

        roomRepository.save(room);

        booking = new Booking();
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

        bookingRepository.save(booking);

    }

    @AfterEach
    public void tearDown() {
        bookingRepository.deleteAll();
    }

    @Test
    @DisplayName("Test Get All Booking")
    public void getAllBooking(){
        List<Booking> bookingList = bookingRepository.findAll();

        assertNotNull(bookingList);
        assertFalse(bookingList.isEmpty());
    }

    @Test
    @DisplayName("Test Get Booking by ID")
    public void getBookingById(){
        Booking booking1 = bookingRepository.findById(booking.getBooking_id()).orElse(null);
        assertNotNull(booking1);
        assertEquals(booking.getBooking_id(), booking1.getBooking_id());
    }

    @Test
    @DisplayName("Test Save Booking")
    public void saveBooking() {
        bookingRepository.save(booking);

        assertNotNull(booking);
    }

    @Test
    @DisplayName("Test Delete Booking by ID")
    public void deleteBooking() {
        bookingRepository.deleteById(booking.getBooking_id());
        assertFalse(bookingRepository.findById(booking.getBooking_id()).isPresent());
    }

}
