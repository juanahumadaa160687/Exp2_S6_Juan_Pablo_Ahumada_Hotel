package org.cslt.hotel.controllers;

import org.cslt.hotel.models.booking.*;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@ComponentScan(basePackages = "org.cslt.hotel.models.booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingModelAssembler bookingModelAssembler;

    @Autowired
    private GuestController guestController;


    @GetMapping("/all")
    public CollectionModel<BookingModel> getAllBookings() {

        List<Booking> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            return CollectionModel.empty();
        }

        else {
            List<BookingModel> bookingModels = bookings.stream()
                    .map(bookingModelAssembler::toModel)
                    .collect(Collectors.toList());

            return CollectionModel.of(bookingModels);
        }

    }

    @GetMapping("/{id}")
    public EntityModel<BookingModel> getBookingById(@PathVariable Long id){

        Booking booking = bookingService.getBookingById(id);

        if (booking == null) {
            throw new BookingNotFoundException("Reserva ID: " + id + " no encontrada");
        }

        else {
                return EntityModel.of(bookingModelAssembler.toModel(booking));
            }

    }

    @PostMapping("/new")
    public ResponseEntity<BookingModel> newBooking(@RequestParam String doc_number, @RequestParam int adults, @RequestParam int childs, @RequestParam String pet, @RequestParam Date check_in, @RequestParam Date check_out) {

        Guest guest = guestController.getGuestByDocNumber(doc_number);

        if(guest == null){
            throw new RuntimeException("Número de documento no encontrado");
        }

        List<Room> availableRooms = bookingService.searchRooms(adults + childs, PetFriendly.valueOf(pet.toUpperCase()));
        System.out.println(availableRooms.getFirst().getRoom_price());

        for(Booking booking : bookingService.getAllBookings()) {
            for (Room room : availableRooms) {
                if (booking.getRoom().equals(room) && booking.getCheckin_date().compareTo(check_out) < 0 && booking.getCheckout_date().compareTo(check_in) > 0) {
                    throw new RuntimeException("La habitación " + room.getRoom_number() + " no está disponible para las fechas seleccionadas");
                } else {
                    Booking newBooking = new Booking();
                    newBooking.setRoom(room);
                    newBooking.setAdults(adults);
                    newBooking.setChildren(childs);

                    if(pet.equals("PERMITDO")){
                        newBooking.setPets(Pets.SI);
                    }
                    else {
                        newBooking.setPets(Pets.NO);
                    }
                    newBooking.setGuest(guest);
                    newBooking.setCheckin_date(check_in);
                    newBooking.setCheckout_date(check_out);
                    newBooking.setTotal_price(room.getRoom_price() * ((check_out.getTime() - check_in.getTime()) / (1000 * 60 * 60 * 24)));
                    newBooking.setStatus(Status.PENDIENTE);
                    newBooking.setCode("BOOK" + System.currentTimeMillis() + "-" + guest.getGuest_id());

                    Booking savedBooking = bookingService.newBooking(newBooking);

                    BookingModel bookingModel = bookingModelAssembler.toModel(savedBooking);

                    return ResponseEntity
                            .created(bookingModel.getRequiredLink("self").toUri())
                            .body(bookingModel);
                }
            }
        }
        throw new RuntimeException("No hay habitaciones disponibles");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {

        Booking booking = bookingService.getBookingById(id);

        if (booking == null) {
            throw new RuntimeException("La Reserva no existe");
        }

        else {
            bookingService.deleteBookingById(id);
            return ResponseEntity.noContent().build();
        }

    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<BookingModel> confirmBooking(@PathVariable Long id) {

        Booking confirmedBooking = bookingService.getBookingById(id);

        if (confirmedBooking == null) {
            throw new RuntimeException("La Reserva no existe");
        } else if (!Objects.equals(confirmedBooking.getStatus(), Status.PENDIENTE)) {
            throw new RuntimeException("La Reserva ya está confirmada");
        } else {
            confirmedBooking.setStatus(Status.CONFIRMADA);
            bookingService.confirmBooking(id, confirmedBooking);
            BookingModel bookingModel = bookingModelAssembler.toModel(confirmedBooking);

            return  ResponseEntity
                    .created(bookingModel.getRequiredLink("self").toUri())
                    .body(bookingModel);
        }

    }

}
