package org.cslt.hotel.models.room;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RoomModel extends RepresentationModel<RoomModel> {

    private Long room_id;
    private int room_number;
    private RoomType room_type;
    private Double room_price;
    private int room_capacity;
    private String room_description;
    private PetFriendly pet_friendly;

}
