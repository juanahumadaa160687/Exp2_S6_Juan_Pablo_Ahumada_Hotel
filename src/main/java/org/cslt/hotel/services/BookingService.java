package org.cslt.hotel.services;

import org.cslt.hotel.models.booking.Booking;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;

import java.util.List;

public interface BookingService {

    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    Booking newBooking(Booking booking);
    void deleteBookingById(Long id);
    List<Room> searchRooms(int capacity, PetFriendly pet_friendly);
    Booking confirmBooking(Long id, Booking booking);
}
