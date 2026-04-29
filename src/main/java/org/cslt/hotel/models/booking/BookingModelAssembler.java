package org.cslt.hotel.models.booking;

import org.cslt.hotel.controllers.BookingController;
import org.cslt.hotel.controllers.GuestController;
import org.cslt.hotel.controllers.RoomController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingModelAssembler implements RepresentationModelAssembler<Booking, BookingModel> {

    @Override
    public BookingModel toModel(Booking booking) {
        BookingModel bookingModel = new BookingModel();

        //Mapear los campos de Guest al GuestModel
        bookingModel.setBooking_id(booking.getBooking_id());
        bookingModel.setCode(booking.getCode());
        bookingModel.setStatus(booking.getStatus().name());
        bookingModel.setCheckin_date(booking.getCheckin_date().toString());
        bookingModel.setCheckout_date(booking.getCheckout_date().toString());
        bookingModel.setAdults(booking.getAdults());
        bookingModel.setChildren(booking.getChildren());
        bookingModel.setPets(booking.getPets().name());
        bookingModel.setTotal_price(booking.getTotal_price());
        bookingModel.setRoom_id(booking.getRoom().getRoom_id());
        bookingModel.setGuest_id(booking.getGuest().getGuest_id());

        //Link self
        bookingModel.add(linkTo(methodOn(BookingController.class).getBookingById(booking.getBooking_id())).withSelfRel());

        //Link al huésped de la reserva
        bookingModel.add(linkTo(methodOn(GuestController.class).getGuest(booking.getGuest().getGuest_id())).withRel("guest"));

        //Link a la habitación de la reserva
        bookingModel.add(linkTo(methodOn(RoomController.class).getRoomById(booking.getRoom().getRoom_id())).withRel("room"));

        //Link a confirmar o eliminar la reserva sólo si el estado de la reserva es "pendiente"
        if(booking.getStatus().name().equals("PENDIENTE")){
            bookingModel.add(linkTo(methodOn(BookingController.class).confirmBooking(booking.getBooking_id())).withRel("confirm-booking"));
            bookingModel.add(linkTo(methodOn(BookingController.class).deleteBooking(booking.getBooking_id())).withRel("cancel-booking"));
        }

        //Link a todas las reservas
        bookingModel.add(linkTo(methodOn(BookingController.class).getAllBookings()).withRel("all-bookings"));

        return bookingModel;

    }
}
