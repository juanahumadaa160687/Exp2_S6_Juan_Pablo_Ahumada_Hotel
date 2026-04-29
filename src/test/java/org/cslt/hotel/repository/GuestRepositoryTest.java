package org.cslt.hotel.repository;

import org.cslt.hotel.models.guest.DocType;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.repositories.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GuestRepositoryTest {

    @Autowired
    private GuestRepository guestRepository;

    private Guest guest;

    @BeforeEach
    public void setup(){
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

    }

    @Test
    @DisplayName("Test find all Guests")
    public void testFindAllGuests() {

        List<Guest> guests = guestRepository.findAll();

        assertNotNull(guests);
        assertFalse(guests.isEmpty());
    }

    @Test
    @DisplayName("Test find Guest by ID")
    public void testFindGuestById() {

        Guest guest = guestRepository.findById(this.guest.getGuest_id()).orElse(null);
        assertNotNull(guest);
        assertEquals(guest.getGuest_id(), this.guest.getGuest_id());
    }

    @Test
    @DisplayName("Test save Guest")
    public void testSaveGuest() {

        guestRepository.save(guest);

        assertNotNull(guest);
    }

    @Test
    @DisplayName("Test delete Guest")
    public void testDeleteGuest() {
        guestRepository.deleteById(this.guest.getGuest_id());
        assertFalse(guestRepository.findById(this.guest.getGuest_id()).isPresent());

    }
}
