package org.cslt.hotel.services;

import org.cslt.hotel.models.guest.Guest;
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
    public Guest updateGuest(Long id, Guest newGuest) {

        Guest guest = guestRepository.findById(id).orElse(null);

        if (guest != null) {
            guest.setFirst_name(newGuest.getFirst_name());
            guest.setLast_name(newGuest.getLast_name());
            guest.setEmail(newGuest.getEmail());
            guest.setDoc_type(newGuest.getDoc_type());
            guest.setDoc_number(newGuest.getDoc_number());
            guest.setPhone_number(newGuest.getPhone_number());
            guest.setAddress(newGuest.getAddress());
            guest.setCity(newGuest.getCity());
            guest.setCountry(newGuest.getCountry());

            guestRepository.save(guest);

            return guest;

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
