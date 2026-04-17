package org.cslt.hotel.services;

import org.cslt.hotel.models.Guest;

import java.util.List;

public interface GuestService {

    List<Guest> getAllGuests();
    Guest getGuestById(Long id);
    Guest newGuest(Guest guest);
    Guest updateGuest(Long id, Guest guest);
    void deleteGuestById(Long id);
    Guest findGuestByDocNumber(String docNumber);

}
