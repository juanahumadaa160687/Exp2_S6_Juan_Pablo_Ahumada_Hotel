package org.cslt.hotel.models.guest;

import org.cslt.hotel.controllers.GuestController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GuestModelAssembler implements RepresentationModelAssembler<Guest, GuestModel> {

    @Override
    public GuestModel toModel(Guest guest) {

        GuestModel guestModel = new  GuestModel();

        //Mapear los campos de Guest al GuestModel

        guestModel.setGuest_id(guest.getGuest_id());
        guestModel.setDoc_type(guest.getDoc_type().name());
        guestModel.setDoc_number(guest.getDoc_number());
        guestModel.setFirst_name(guest.getFirst_name());
        guestModel.setLast_name(guest.getLast_name());
        guestModel.setEmail(guest.getEmail());
        guestModel.setPhone_number(guest.getPhone_number());
        guestModel.setAddress(guest.getAddress());
        guestModel.setCity(guest.getCity());
        guestModel.setCountry(guest.getCountry());

        //Agregar el self link
        guestModel.add(linkTo(methodOn(GuestController.class).getGuest(guest.getGuest_id())).withSelfRel());

        //Agregar link para eliminar huésped
        guestModel.add(linkTo(methodOn(GuestController.class).deleteGuest(guest.getGuest_id())).withRel("delete-guest"));

        //Agregar link all guests
        guestModel.add(linkTo(methodOn(GuestController.class).getAllGuests()).withRel("all-guests"));

        return guestModel;

    }

}
