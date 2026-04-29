package org.cslt.hotel.services;

import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.models.room.RoomType;
import org.cslt.hotel.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

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
    public void getAllRoomsTest() {
        List<Room> expected = roomService.getAllRooms();
        when(roomRepository.findAll()).thenReturn(expected);

        assertEquals(expected, roomService.getAllRooms());
    }

    @Test
    @DisplayName("Test Get Room By Id")
    public void getRoomByIdTest() {
        when(roomRepository.findById(room.getRoom_id())).thenReturn(Optional.of(room));

        assertEquals(room, roomService.getRoomById(room.getRoom_id()));
    }

    @Test
    @DisplayName("Test Create Room")
    public void createRoomTest() {
        when(roomRepository.save(room)).thenReturn(room);
        assertEquals(room, roomService.newRoom(room));
    }

    @Test
    @DisplayName("Test Update Room")
    public void updateRoomTest() {
        when(roomRepository.findById(room.getRoom_id())).thenReturn(Optional.of(room));
        when(roomRepository.save(room)).thenReturn(room);
        Room updatedRoom = roomService.updateRoom(room.getRoom_id(), room);

        assertEquals(room, updatedRoom);
        verify(roomRepository, times(1)).findById(room.getRoom_id());
    }

    @Test
    @DisplayName("Test Delete Room")
    public void deleteRoomTest() {
        roomService.deleteRoomById(room.getRoom_id());
        verify(roomRepository, times(1)).deleteById(room.getRoom_id());
    }

}
