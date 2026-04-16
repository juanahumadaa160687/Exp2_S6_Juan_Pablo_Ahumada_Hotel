package org.cslt.hotel.controllers;

import org.cslt.hotel.models.Booking;
import org.cslt.hotel.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        try {
            return bookingService.getAllBookings();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las reservas: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id){
        try {
            return bookingService.getBookingById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la reserva: " + e.getMessage());
        }
    }

    @PostMapping("/new-booking")
    public Booking newBooking(@RequestParam Long guestId, @RequestParam int adults, @RequestParam int childs, @RequestParam String pet, @RequestParam Date check_in, @RequestParam Date check_out) {

        Booking booking = new Booking();

        GuestController guestController = new GuestController();

        int total_guests = adults + childs;

        for (Booking booking1 : bookingService.getAllBookings()) {
            if(booking1.getCheckin_date().compareTo(check_in) <= 0 && booking1.getCheckout_date().compareTo(check_out) >= 0){
                if(booking1.getRoom().getRoom_capacity() >= total_guests && booking1.getRoom().getIs_PetFriendly().equalsIgnoreCase(pet)) {
                    booking.setRoom(booking1.getRoom());
                    break;
                }
            }
        }

        booking.setGuest(guestController.getGuest(guestId));
        booking.setAdults(adults);
        booking.setChildren(childs);
        booking.setPets(pet);
        booking.setCheckin_date(check_in);
        booking.setCheckout_date(check_out);
        booking.setTotal_price(booking.getRoom().getRoom_price() * (check_out.getTime() - check_in.getTime()) / (1000 * 60 * 60 * 24));

        booking.getRoom().setRoom_availability("OCUPADA");

        return bookingService.newBooking(booking);
    }

    @PutMapping("/update-booking/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        try {
            return bookingService.updateBooking(id, booking);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la reserva: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-booking/{id}")
    public void deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBookingById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la reserva: " + e.getMessage());
        }
    }

}
