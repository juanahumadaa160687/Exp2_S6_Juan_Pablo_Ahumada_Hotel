package org.cslt.hotel.repositories;

import org.cslt.hotel.models.Booking;
import org.cslt.hotel.models.Guest;
import org.cslt.hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT Room FROM Booking b FULL OUTER JOIN b.room Room  WHERE Room.room_capacity >= ?1 and Room.is_PetFriendly=?2")
    List<Room> searchRooms(int capacity, String is_PetFriendly);

    @Query("SELECT Guest FROM Booking b FULL OUTER JOIN b.guest Guest  WHERE Guest.doc_number=?1")
    Guest findGuestByDocNumber(String docNumber);

}
