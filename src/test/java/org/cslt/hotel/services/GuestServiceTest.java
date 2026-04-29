package org.cslt.hotel.services;

import org.cslt.hotel.models.guest.DocType;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.repositories.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuestServiceTest {

    @Mock
    private GuestRepository repository;

    @InjectMocks
    private GuestServiceImpl service;

    private Guest guest;

    @BeforeEach
    public void setUp() {
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

    }

    @Test
    @DisplayName("Test get all Guests")
    public void getAllGuests() {
        List<Guest> expected = Arrays.asList(guest);

        when(repository.findAll()).thenReturn(expected);

        assertEquals(expected, service.getAllGuests());
    }

    @Test
    @DisplayName("Test get Guest by ID")
    public void getGuestById() {
        when(repository.findById(guest.getGuest_id())).thenReturn(Optional.of(guest));
        assertEquals(guest, service.getGuestById(guest.getGuest_id()));
    }

    @Test
    @DisplayName("Test create Guest")
    public void createGuest() {
        when(repository.save(guest)).thenReturn(guest);
        assertEquals(guest, service.newGuest(guest));
    }

    @Test
    @DisplayName("Test update Guest")
    public void updateGuest() {
        when(repository.findById(guest.getGuest_id())).thenReturn(Optional.of(guest));
        when(repository.save(guest)).thenReturn(guest);
        Guest result = service.updateGuest(guest.getGuest_id(), guest);
        assertEquals(guest, result);
        assertEquals(guest.getGuest_id(), result.getGuest_id());
        verify(repository, times(1)).save(guest);
    }

    @Test
    @DisplayName("Test Delete Guest")
    public void deleteGuest() {
        service.deleteGuestById(guest.getGuest_id());
        verify(repository, times(1)).deleteById(guest.getGuest_id());
    }

}
