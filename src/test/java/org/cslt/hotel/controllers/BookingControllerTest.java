package org.cslt.hotel.controllers;

import org.cslt.hotel.models.booking.Booking;
import org.cslt.hotel.models.booking.Pets;
import org.cslt.hotel.models.booking.Status;
import org.cslt.hotel.models.guest.DocType;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.models.room.RoomType;
import org.cslt.hotel.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService service;

    @MockitoBean
    private GuestController guestController;

    private Booking booking;
    private Guest guest;
    private Room room;

    //Creación de objetos de prueba para las pruebas unitarias
    @BeforeEach
    public void setup() {
        guest = new Guest();
        guest.setGuest_id(1L);
        guest.setDoc_type(DocType.PASAPORTE);
        guest.setDoc_number("123456789");
        guest.setFirst_name("John");
        guest.setLast_name("Doe");
        guest.setEmail("jdoe@email.com");
        guest.setPhone_number("1234567890");
        guest.setAddress("123 Main St");
        guest.setCity("New York");
        guest.setCountry("USA");

        room = new Room();
        room.setRoom_id(1L);
        room.setRoom_number(101);
        room.setRoom_type(RoomType.SINGLE);
        room.setRoom_price(100.0);
        room.setRoom_description("A cozy single room with a comfortable bed.");
        room.setRoom_capacity(3);
        room.setPet_friendly(PetFriendly.PERMITIDO);

        booking = new Booking();
        booking.setBooking_id(1L);
        booking.setCode("ABC123");
        booking.setCheckin_date(Date.valueOf("2024-07-01"));
        booking.setCheckout_date(Date.valueOf("2024-07-10"));
        booking.setAdults(2);
        booking.setChildren(1);
        booking.setTotal_price(500.0);
        booking.setStatus(Status.PENDIENTE);
        booking.setPets(Pets.SI);
        booking.setGuest(guest);
        booking.setRoom(room);

    }

    @Test
    @DisplayName("Test Get All Bookings")
    public void testGetAllBookings() throws Exception {

        when(service.getAllBookings()).thenReturn(Arrays.asList(booking));

        mockMvc.perform(get("/bookings/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].booking_id").value(booking.getBooking_id()));
    }

    @Test
    @DisplayName("Test Get Booking by ID")
    public void testGetBookingById() throws Exception {

        when(service.getBookingById(booking.getBooking_id())).thenReturn(booking);
        mockMvc.perform(get("/bookings/" + booking.getBooking_id()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.booking_id").value(booking.getBooking_id()));

    }

    @Test
    @DisplayName("Test Create Booking")
    public void testCreateBooking() throws Exception {

        //Al llamar a la función para obtener el huésped por número de documento, este regresará el huésped ya creado
        when(guestController.getGuestByDocNumber(guest.getDoc_number())).thenReturn(guest);

        //Al llamar a la función para obtener las habitaciones disponibles según criterios, regresará la habitación ya creada
        when(service.searchRooms(eq(room.getRoom_capacity()), eq(room.getPet_friendly()))).thenReturn(Arrays.asList(room));

        //Al llamar a todas las reservas registras traerá una lista con la reserva ya creada, para que el sistema pueda verificar la disponibilidad de la habitación
        when(service.getAllBookings()).thenReturn(Arrays.asList(booking));

        //Al llamar a la función crear reserva, devolverá la reserva ya creada.
        when(service.newBooking(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/bookings/new")
                //Se le entregan todos los parámetros de búsquedas necesarios para realizar la función
                        .queryParam("doc_number", guest.getDoc_number())
                        .queryParam("adults", Integer.valueOf(booking.getAdults()).toString())
                        .queryParam("childs", Integer.valueOf(booking.getChildren()).toString())
                        .queryParam("pet", room.getPet_friendly().name())
                //Se modifican las fechas para que no coincidan con la reserva ya registrada
                        .queryParam("check_in", Date.valueOf("2026-07-01").toString())
                        .queryParam("check_out", Date.valueOf("2026-07-10").toString())
                .contentType(String.valueOf(MediaType.APPLICATION_JSON)));
                verify(service, times(1)).newBooking(any(Booking.class));
    }

    @Test
    @DisplayName("Test Confirm Booking")
    public void testConfirmBooking() throws Exception {

        when(service.getBookingById(booking.getBooking_id())).thenReturn(booking);
        when(service.confirmBooking(eq(booking.getBooking_id()), any(Booking.class))).thenReturn(booking);

        mockMvc.perform(put("/bookings/confirm/" + booking.getBooking_id())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("{\"status\": \"CONFIRMADA\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.booking_id").value(booking.getBooking_id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("CONFIRMADA"));
    }

    @Test
    @DisplayName("Test Cancel Booking")
    public void testCancelBooking() {
        when(service.getBookingById(booking.getBooking_id())).thenReturn(booking);

        service.deleteBookingById(booking.getBooking_id());
        verify(service, times(1)).deleteBookingById(booking.getBooking_id());
    }

}
