package org.cslt.hotel.controllers;

import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.models.room.RoomModel;
import org.cslt.hotel.models.room.RoomModelAssembler;
import org.cslt.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
@ComponentScan(basePackages = "org.cslt.hotel.models.room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomModelAssembler roomModelAssembler;

    @GetMapping("/all")
    public CollectionModel<RoomModel> getAllRooms() {

        List<Room> rooms = roomService.getAllRooms();

        List<RoomModel> roomModels = rooms.stream()
                .map(roomModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roomModels);
    }

    @GetMapping("/{id}")
    public EntityModel<RoomModel> getRoomById(@PathVariable Long id) {

        Room room = roomService.getRoomById(id);

        if (room == null) {
            throw new RoomNotFoundException("La habitación con id " + id + " no fue encontrada");
        }

        else {
            return EntityModel.of(roomModelAssembler.toModel(room));
        }
    }

    @PostMapping("/new")
    public ResponseEntity<RoomModel> newRoom(@RequestBody Room room) {

        Room newRoom = roomService.newRoom(room);

        RoomModel roomModel = roomModelAssembler.toModel(newRoom);

        return ResponseEntity
                .created(roomModel.getRequiredLink("self").toUri())
                .body(roomModel);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<RoomModel> updateRoom(@PathVariable Long id, @RequestBody Room room) {

        Room updatedRoom = roomService.updateRoom(id, room);
        RoomModel roomModel = roomModelAssembler.toModel(updatedRoom);

        return ResponseEntity
                .created(roomModel.getRequiredLink("self").toUri())
                .body(roomModel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {

        roomService.deleteRoomById(id);

        return ResponseEntity.noContent().build();
    }

}
