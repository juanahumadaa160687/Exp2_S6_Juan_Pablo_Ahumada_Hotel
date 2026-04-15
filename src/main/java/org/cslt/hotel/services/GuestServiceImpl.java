package org.cslt.hotel.services;

import org.cslt.hotel.models.Guest;
import org.cslt.hotel.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id).orElse(null);
    }

    @Override
    public Guest newGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Long id, Guest guest) {
        Guest existingGuest = guestRepository.findById(id).orElse(null);
        if (existingGuest != null) {
            existingGuest.setFirst_name(guest.getFirst_name());
            existingGuest.setLast_name(guest.getLast_name());
            existingGuest.setEmail(guest.getEmail());
            existingGuest.setPhone_number(guest.getPhone_number());
            existingGuest.setAddress(guest.getAddress());
            existingGuest.setCity(guest.getCity());
            existingGuest.setCountry(guest.getCountry());
            guestRepository.save(existingGuest);
        }
        return null;
    }

    @Override
    public void deleteGuestById(Long id) {
        guestRepository.deleteById(id);
    }

}
