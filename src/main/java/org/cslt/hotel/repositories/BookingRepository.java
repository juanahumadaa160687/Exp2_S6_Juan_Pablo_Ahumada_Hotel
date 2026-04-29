package org.cslt.hotel.repositories;

import org.cslt.hotel.models.booking.Booking;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    //Habitaciones disponibles según capacidad y permiso de mascota
    @Query("SELECT Room FROM Booking b FULL OUTER JOIN b.room Room  WHERE Room.room_capacity >= ?1 and Room.pet_friendly=?2")
    List<Room> searchRooms(int capacity, PetFriendly pet_friendly);

    //Huésped por nro. Documento
    @Query("SELECT Guest FROM Booking b FULL OUTER JOIN b.guest Guest  WHERE Guest.doc_number=?1")
    Guest findGuestByDocNumber(String docNumber);

    //Reservas no confirmadas de un huésped
    @Query("SELECT Booking FROM Booking Booking  WHERE Booking.guest.guest_id=?1 and Booking.status='PENDIENTE'")
    Booking getNonConfirmedBookingByGuestId(Long guestId);

}
