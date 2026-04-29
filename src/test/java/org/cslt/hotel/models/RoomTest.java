package org.cslt.hotel.models;

import org.cslt.hotel.models.room.PetFriendly;
import org.cslt.hotel.models.room.Room;
import org.cslt.hotel.models.room.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTest {

    @Test
    @DisplayName("Test Getters & Setters Room")
    public void testGettersAndSettersRoom() {

        Room room = new Room();
        room.setRoom_id(1L);
        room.setRoom_number(101);
        room.setRoom_type(RoomType.SINGLE);
        room.setRoom_price(100.0);
        room.setRoom_description("A cozy single room with a comfortable bed.");
        room.setRoom_capacity(2);
        room.setPet_friendly(PetFriendly.PERMITIDO);

        assertEquals(1L, room.getRoom_id());
        assertEquals(101, room.getRoom_number());
    }

    @Test
    @DisplayName("Test Constructor Room")
    public void testConstructorRoom() {

        Room room = new Room(1L, 101, RoomType.SINGLE, 100.0, 2, "A cozy single room with a comfortable bed.", PetFriendly.PERMITIDO);

        assertEquals(1L, room.getRoom_id());
        assertEquals(101, room.getRoom_number());
    }
}
