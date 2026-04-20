package org.cslt.hotel.controllers;

import org.cslt.hotel.models.Room;
import org.cslt.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping("/new")
    public Room newRoom(@RequestBody Room room) {
        return roomService.newRoom(room);
    }

    @PutMapping("/edit/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoomById(id);
    }

    @GetMapping("/room-search")
    public List<Room> searchRooms(@RequestParam int capacity, @RequestParam String is_PetFriendly) {
        return roomService.searchRooms(capacity, is_PetFriendly);
    }

}
