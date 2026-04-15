package org.cslt.hotel.controllers;

import org.cslt.hotel.models.Guest;
import org.cslt.hotel.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping("/all")
    public String getAllGuests() {
        return "List of all guests";
    }

    @GetMapping("/{id}")
    public Guest getGuest(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }

    @PostMapping("/new-guest")
    public Guest newGuest(@RequestBody Guest guest) {
        return guestService.newGuest(guest);
    }

    @PutMapping("/update-guest/{id}")
    public Guest updateGuest(@PathVariable Long id, @RequestBody Guest guest) {
        return guestService.updateGuest(id, guest);
    }

    @DeleteMapping("/delete-guest/{id}")
    public void deleteGuest(@PathVariable Long id) {
        guestService.deleteGuestById(id);
    }

}
