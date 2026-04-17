package org.cslt.hotel.services;

import org.cslt.hotel.models.Booking;
import org.cslt.hotel.models.Guest;
import org.cslt.hotel.models.Room;

import java.util.List;

public interface BookingService {

    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    Booking newBooking(Booking booking);
    Booking updateBooking(Long id, Booking booking);
    void deleteBookingById(Long id);
    List<Room> searchRooms(int capacity, String is_PetFriendly);
    Guest findGuestByDocNumber(String docNumber);

}
