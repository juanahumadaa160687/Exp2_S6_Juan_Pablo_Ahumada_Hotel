package org.cslt.hotel.models.room;

import org.cslt.hotel.controllers.RoomController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoomModelAssembler implements RepresentationModelAssembler<Room, RoomModel> {

    @Override
    public RoomModel toModel(Room room) {
        RoomModel roomModel = new RoomModel();
        roomModel.setRoom_id(room.getRoom_id());
        roomModel.setRoom_number(room.getRoom_number());
        roomModel.setRoom_type(room.getRoom_type());
        roomModel.setRoom_price(room.getRoom_price());
        roomModel.setRoom_capacity(room.getRoom_capacity());
        roomModel.setRoom_description(room.getRoom_description());
        roomModel.setPet_friendly(room.getPet_friendly());

        roomModel.add(linkTo(methodOn(RoomController.class).getRoomById(room.getRoom_id())).withSelfRel());
        roomModel.add(linkTo(methodOn(RoomController.class).getAllRooms()).withRel("all-rooms"));

        return roomModel;
    }
}
