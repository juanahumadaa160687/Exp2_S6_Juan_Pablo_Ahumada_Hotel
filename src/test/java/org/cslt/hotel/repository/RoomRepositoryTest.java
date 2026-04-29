package org.cslt.hotel.repository;

import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.models.room.RoomType;
import org.cslt.hotel.repositories.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    private Room room;

    @BeforeEach
    public void setup(){
        room = new Room();
        room.setRoom_number(101);
        room.setRoom_type(RoomType.SINGLE);
        room.setRoom_price(100.0);
        room.setRoom_description("A cozy single room with a comfortable bed.");
        room.setRoom_capacity(2);
        room.setPet_friendly(PetFriendly.PERMITIDO);

        roomRepository.save(room);

    }

    @AfterEach
    public void tearDown(){
        roomRepository.deleteAll();
    }

    @Test
    @DisplayName("Test find all Rooms")
    public void testFindAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        assertNotNull(rooms);
        assertFalse(rooms.isEmpty());
    }

    @Test
    @DisplayName("Test find room by ID")
    public void testFindRoomById() {
        Room room1 = roomRepository.findById(this.room.getRoom_id()).orElse(null);
        assertNotNull(room1);
        assertEquals(room1.getRoom_id(), this.room.getRoom_id());
    }

    @Test
    @DisplayName("Test new room")
    public void testNewRoom() {

        roomRepository.save(room);

        assertNotNull(room);
    }

    @Test
    @DisplayName("Test delete room")
    public void testDeleteRoom() {
        roomRepository.deleteById(this.room.getRoom_id());
        assertFalse(roomRepository.findById(this.room.getRoom_id()).isPresent());
    }

}
