package org.cslt.hotel.repositories;

import org.cslt.hotel.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
