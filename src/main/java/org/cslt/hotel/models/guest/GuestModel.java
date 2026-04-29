package org.cslt.hotel.models.guest;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GuestModel extends RepresentationModel<GuestModel> {

    private Long guest_id;
    private String doc_type;
    private String doc_number;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String address;
    private String city;
    private String country;

}
