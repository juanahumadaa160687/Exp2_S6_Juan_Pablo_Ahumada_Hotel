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
        for (Guest g : guestRepository.findAll()) {
            if (g.getGuest_id().equals(id)) {
                g.setDoc_type(guest.getDoc_type());
                g.setDoc_number(guest.getDoc_number());
                g.setFirst_name(guest.getFirst_name());
                g.setLast_name(guest.getLast_name());
                g.setEmail(guest.getEmail());
                g.setPhone_number(guest.getPhone_number());
                g.setAddress(guest.getAddress());
                g.setCity(guest.getCity());
                g.setCountry(guest.getCountry());
                return guestRepository.save(g);
            }
        }
        return null;
    }

    @Override
    public void deleteGuestById(Long id) {
        guestRepository.deleteById(id);
    }

    @Override
    public Guest findGuestByDocNumber(String docNumber) {
        return guestRepository.findByDocNumber(docNumber);
    }

}
