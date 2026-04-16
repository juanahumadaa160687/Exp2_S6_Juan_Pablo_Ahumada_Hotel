package org.cslt.hotel.services;

import org.cslt.hotel.models.Room;
import org.cslt.hotel.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room newRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        Room existingRoom = roomRepository.findById(id).orElse(null);
        if (existingRoom != null) {
            existingRoom.setRoom_number(room.getRoom_number());
            existingRoom.setRoom_type(room.getRoom_type());
            existingRoom.setRoom_price(room.getRoom_price());
            existingRoom.setRoom_availability(room.getRoom_availability());
            existingRoom.setRoom_capacity(room.getRoom_capacity());
            existingRoom.setRoom_description(room.getRoom_description());
            existingRoom.setRoom_type(room.getIs_PetFriendly());
            return roomRepository.save(existingRoom);
        }
        return null;
    }

    @Override
    public List<Room> searchRooms(int capacity, String is_PetFriendly) {
        for(Room room : roomRepository.findAll()) {
            if (room.getRoom_capacity() >= capacity && room.getIs_PetFriendly().equals(is_PetFriendly)) {
                return roomRepository.searchRooms(capacity, is_PetFriendly);
            }
        }
        return null;
    }

}
