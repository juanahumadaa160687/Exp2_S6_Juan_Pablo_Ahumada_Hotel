package org.cslt.hotel.controllers;

import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.models.room.RoomType;
import org.cslt.hotel.services.RoomService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    private Room room;

    @BeforeEach
    public void setup() {
        room = new Room();
        room.setRoom_id(1L);
        room.setRoom_number(101);
        room.setRoom_type(RoomType.SINGLE);
        room.setRoom_price(100.0);
        room.setRoom_description("A cozy single room with a comfortable bed.");
        room.setRoom_capacity(2);
        room.setPet_friendly(PetFriendly.PERMITIDO);
    }

    @Test
    @DisplayName("Test Get All Rooms")
    public void getAllRoomsTest() throws Exception {

        when(roomService.getAllRooms()).thenReturn(Arrays.asList(room));

        mockMvc.perform(get("/rooms/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].room_id").value(room.getRoom_id()));
    }

    @Test
    @DisplayName("Test Get Room By ID")
    public void getRoomByIdTest() throws Exception {

        when(roomService.getRoomById(1L)).thenReturn(room);

        mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.room_id").value(room.getRoom_id()));
    }

    @Test
    @DisplayName("Test Create Room")
    public void createRoomTest() throws Exception {

        when(roomService.newRoom(any(Room.class))).thenReturn(room);

        mockMvc.perform(post("/rooms/new")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content("{\"room_number\":101,\"room_type\":\"SINGLE\",\"room_price\":100.0,\"room_description\":\"A cozy single room with a comfortable bed.\",\"room_capacity\":2,\"pet_friendly\":\"PERMITIDO\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room_id").value(room.getRoom_id()));
    }

    @Test
    @DisplayName("Test Update Room")
    public void updateRoomTest() throws Exception {
        when(roomService.updateRoom(eq(room.getRoom_id()), any(Room.class))).thenReturn(room);

        mockMvc.perform(put("/rooms/edit/1")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content("{\"room_number\":101,\"room_type\":\"SINGLE\",\"room_price\":100.0,\"room_description\":\"A cozy single room with a comfortable bed.\",\"room_capacity\":2,\"pet_friendly\":\"PERMITIDO\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room_id").value(room.getRoom_id()));
    }

    @Test
    @DisplayName("Test Delete Room")
    public void deleteRoomTest() throws Exception {
        mockMvc.perform(delete("/rooms/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(roomService, times(1)).deleteRoomById(1L);
    }
}
