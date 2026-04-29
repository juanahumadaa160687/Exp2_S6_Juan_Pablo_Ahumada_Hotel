package org.cslt.hotel.services;

import org.cslt.hotel.models.booking.Booking;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public Booking newBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBookingById(Long id) {

        bookingRepository.deleteById(id);
    }

    @Override
    public List<Room> searchRooms(int capacity, PetFriendly person_friendly) {
        return bookingRepository.searchRooms(capacity, person_friendly);
    }

    @Override
    public Booking confirmBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);

        if (existingBooking != null) {
            existingBooking.setStatus(booking.getStatus());
            return bookingRepository.save(existingBooking);
        }

        return null;
    }

}
