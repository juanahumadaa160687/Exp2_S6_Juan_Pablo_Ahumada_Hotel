package org.cslt.hotel.models.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookingModel extends RepresentationModel<BookingModel> {

    private Long booking_id;
    private String code;
    private String status;
    private String checkin_date;
    private String checkout_date;
    private int adults;
    private int children;
    private String pets;
    private Double total_price;
    private Long room_id;
    private Long guest_id;

}
