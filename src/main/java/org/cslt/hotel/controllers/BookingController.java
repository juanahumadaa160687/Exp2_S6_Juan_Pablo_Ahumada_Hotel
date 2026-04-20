package org.cslt.hotel.controllers;

import org.cslt.hotel.models.Booking;
import org.cslt.hotel.models.Guest;
import org.cslt.hotel.models.Room;
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

    @Autowired
    GuestController guestController;

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

    @PostMapping("/new")
    public Booking newBooking(@RequestParam String doc_number, @RequestParam int adults, @RequestParam int childs, @RequestParam String pet, @RequestParam Date check_in, @RequestParam Date check_out) {

        System.out.println("New booking");

        List<Room> availableRooms = bookingService.searchRooms(adults + childs, pet);

        System.out.println("Available rooms: " + availableRooms.size());

        Guest guest = guestController.getGuestByDocNumber(doc_number);

        if(bookingService.getAllBookings().isEmpty()) {
            Booking booking = new Booking();
            booking.setRoom(availableRooms.getFirst());
            booking.setAdults(adults);
            booking.setChildren(childs);
            booking.setPets(pet);
            booking.setGuest(guest);
            booking.setCheckin_date(check_in);
            booking.setCheckout_date(check_out);
            booking.setTotal_price(availableRooms.getFirst().getRoom_price() * (check_out.getTime() - check_in.getTime()) / (1000 * 60 * 60 * 24));
            booking.setStatus("PENDIENTE");
            booking.setCode(guest.getGuest_id() + String.valueOf(availableRooms.getFirst().getRoom_id()) + check_in.getTime());

            System.out.println("Booking: " + booking.getRoom().getRoom_number() + " " + booking.getGuest().getFirst_name() + " " + booking.getCheckin_date() + " " + booking.getCheckout_date());
            return bookingService.newBooking(booking);
        }

        else {
            for(Booking booking : bookingService.getAllBookings()) {
                for (Room room : availableRooms) {
                    if (booking.getRoom().equals(room) && booking.getCheckin_date().compareTo(check_out) < 0 && booking.getCheckout_date().compareTo(check_in) > 0) {
                        System.out.println("La habitación " + room.getRoom_number() + " no está disponible para las fechas seleccionadas");
                    } else {
                        Booking newBooking = new Booking();
                        newBooking.setRoom(room);
                        newBooking.setAdults(adults);
                        newBooking.setChildren(childs);
                        newBooking.setPets(pet);
                        newBooking.setGuest(guest);
                        newBooking.setCheckin_date(check_in);
                        newBooking.setCheckout_date(check_out);
                        newBooking.setTotal_price(room.getRoom_price() * (check_out.getTime() - check_in.getTime()) / (1000 * 60 * 60 * 24));
                        newBooking.setStatus("PENDIENTE");
                        newBooking.setCode(guest.getGuest_id() + String.valueOf(availableRooms.getFirst().getRoom_id()) + check_in.getTime());

                        System.out.println("Booking: " + booking.getRoom().getRoom_number() + " " + booking.getGuest().getFirst_name() + " " + booking.getCheckin_date() + " " + booking.getCheckout_date());

                        return bookingService.newBooking(newBooking);
                    }
                }
            }
        }
        System.out.println("No hay habitaciones disponibles para las fechas seleccionadas");
        return null;
    }

    @PutMapping("/edit/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        try {
            return bookingService.updateBooking(id, booking);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la reserva: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBookingById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la reserva: " + e.getMessage());
        }
    }

}
