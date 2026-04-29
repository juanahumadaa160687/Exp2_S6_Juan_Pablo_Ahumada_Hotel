package org.cslt.hotel.controllers;

import org.cslt.hotel.models.guest.DocType;
import org.cslt.hotel.models.guest.Guest;
import org.cslt.hotel.services.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuestController.class)
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GuestService service;

    private Guest guest;

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
    }

    @Test
    @DisplayName("Test get all Guests")
    public void getAllGuests() throws Exception {

        when(service.getAllGuests()).thenReturn(Arrays.asList(guest));

        mockMvc.perform(get("/guests/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].guest_id").value(guest.getGuest_id()));
    }

    @Test
    @DisplayName("Test get Guest by ID")
    public void getGuestById() throws Exception {
        when(service.getGuestById(guest.getGuest_id())).thenReturn(guest);

        mockMvc.perform(get("/guests/" + guest.getGuest_id()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.guest_id").value(guest.getGuest_id()));
    }

    @Test
    @DisplayName("Test create Guest")
    public void createGuest() throws Exception {

        when(service.newGuest(any(Guest.class))).thenReturn(guest);

        mockMvc.perform(post("/guests/new")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content("{\"doc_type\":\"PASAPORTE\",\"doc_number\":\"123456789\",\"first_name\":\"John\",\"last_name\":\"Doe\",\"email\":\"jdoe@email.com\",\"phone_number\":\"1234567890\",\"address\":\"123 Main St\",\"city\":\"New York\",\"country\":\"USA\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.guest_id").value(guest.getGuest_id()));
    }

    @Test
    @DisplayName("Test update Guest")
    public void updateGuest() throws Exception {

        when(service.updateGuest(eq(guest.getGuest_id()), any(Guest.class))).thenReturn(guest);

        mockMvc.perform(put("/guests/edit/" + guest.getGuest_id())
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content("{\"doc_type\":\"PASAPORTE\",\"doc_number\":\"123456789\",\"first_name\":\"John\",\"last_name\":\"Doe\",\"email\":\"jdoe@email.com\",\"phone_number\":\"1234567890\",\"address\":\"123 Main St\",\"city\":\"New York\",\"country\":\"USA\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.guest_id").value(guest.getGuest_id()));
    }

    @Test
    @DisplayName("Test delete Guest")
    public void deleteGuest() throws Exception {
        mockMvc.perform(delete("/guests/delete/" + guest.getGuest_id()))
                .andExpect(status().isNoContent());
        verify(service, times(1)).deleteGuestById(guest.getGuest_id());
    }

}
