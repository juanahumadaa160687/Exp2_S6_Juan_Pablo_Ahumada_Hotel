package org.cslt.hotel.services;


import org.cslt.hotel.models.room.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();
    Room getRoomById(Long id);
    Room newRoom(Room room);
    Room updateRoom(Long id, Room room);
    void deleteRoomById(Long id);

}
