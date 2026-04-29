package org.cslt.hotel.models;

import org.cslt.hotel.models.guest.DocType;
import org.cslt.hotel.models.guest.Guest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestTest {

    @Test
    @DisplayName("Test Getters & Setters Guest")
    public void testGettersAndSettersGuest() {

        Guest guest = new Guest();
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

        assertEquals(1L, guest.getGuest_id());
        assertEquals(DocType.PASAPORTE, guest.getDoc_type());
    }

    @Test
    @DisplayName("Test Constructor Guest")
    public void testConstructorGuest() {
        Guest guest = new Guest(1L, DocType.PASAPORTE, "123456789", "John", "Doe", "jdoe@email.com", "1234567890", "123 Main St", "New York", "USA");
        assertEquals(1L, guest.getGuest_id());
        assertEquals(DocType.PASAPORTE, guest.getDoc_type());
    }
}
