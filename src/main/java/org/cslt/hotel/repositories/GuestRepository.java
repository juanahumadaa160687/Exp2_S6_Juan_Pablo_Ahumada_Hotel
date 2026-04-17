package org.cslt.hotel.repositories;

import org.cslt.hotel.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query("SELECT g FROM Guest g WHERE g.doc_number=?1")
    Guest findByDocNumber(String docNumber);

}
