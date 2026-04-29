package org.cslt.hotel.controllers;

import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.guest.GuestModel;
import org.cslt.hotel.models.guest.GuestModelAssembler;
import org.cslt.hotel.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/guests")
@ComponentScan(basePackages = "org.cslt.hotel.models.guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestModelAssembler guestModelAssembler;


    @GetMapping("/all")
    public CollectionModel<GuestModel> getAllGuests() {

        List<Guest> guests = guestService.getAllGuests();

        List<GuestModel> guestModels = guests.stream()
                .map(guestModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(guestModels);

    }

    @GetMapping("/{id}")
    public EntityModel<GuestModel> getGuest(@PathVariable Long id) {

        Guest guest = guestService.getGuestById(id);

        if (guest == null) {
            throw new GuestNotFoundException("Guest with id " + id + " not found");
        }

        else{
            return EntityModel.of(guestModelAssembler.toModel(guest));
        }

    }

    @PostMapping("/new")
    public ResponseEntity<GuestModel> newGuest(@RequestBody Guest guest) {

        Guest newGuest = guestService.newGuest(guest);
        GuestModel guestModel = guestModelAssembler.toModel(newGuest);

        return ResponseEntity
                .created(guestModel.getRequiredLink("self").toUri())
                .body(guestModel);

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<GuestModel> updateGuest(@PathVariable Long id, @RequestBody Guest guest) {

        Guest updatedGuest = guestService.updateGuest(id, guest);
        GuestModel guestModel = guestModelAssembler.toModel(updatedGuest);

        return ResponseEntity.ok(guestModel);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable Long id) {

        guestService.deleteGuestById(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/search/{doc_number}")
    public Guest getGuestByDocNumber(@PathVariable String doc_number) {

        Guest guest = guestService.findGuestByDocNumber(doc_number);
        if (guest == null) {
            throw new GuestNotFoundException("Huésped con nro. de documento: " + doc_number + " no encontrado");
        }

        return guest;
    }

}
