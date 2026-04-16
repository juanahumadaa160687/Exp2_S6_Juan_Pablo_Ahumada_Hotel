package org.cslt.hotel.repositories;

import org.cslt.hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.room_capacity >= :capacity AND r.is_PetFriendly = :isPetFriendly")
    List<Room> searchRooms(@Param("capacity") int capacity, @Param("isPetFriendly") String isPetFriendly);

}
