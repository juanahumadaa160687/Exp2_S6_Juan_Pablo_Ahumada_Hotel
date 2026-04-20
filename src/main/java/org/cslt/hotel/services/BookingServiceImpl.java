package org.cslt.hotel.services;

import org.cslt.hotel.models.Booking;
import org.cslt.hotel.models.Guest;
import org.cslt.hotel.models.Room;
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
    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking != null) {
            existingBooking.setStatus(booking.getStatus());
            existingBooking.setAdults(booking.getAdults());
            existingBooking.setChildren(booking.getChildren());
            existingBooking.setGuest(booking.getGuest());
            existingBooking.setRoom(booking.getRoom());
            existingBooking.setCheckin_date(booking.getCheckin_date());
            existingBooking.setCheckout_date(booking.getCheckout_date());
            return bookingRepository.save(existingBooking);
        }
        return null;
    }

    @Override
    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Room> searchRooms(int capacity, String is_PetFriendly) {
        return bookingRepository.searchRooms(capacity, is_PetFriendly);
    }

    @Override
    public Guest findGuestByDocNumber(String docNumber) {
        return bookingRepository.findGuestByDocNumber(docNumber);
    }

}
