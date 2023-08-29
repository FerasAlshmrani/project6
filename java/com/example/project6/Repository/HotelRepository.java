package com.example.project6.Repository;

import com.example.project6.Model.Hotel;
import com.example.project6.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {

    @Query("select h from Hotel h where h.user = ?1 and h.id =?2")
    Hotel findHotelByUserAndId(User user, Integer id);

    Hotel searchByApartment(String apartment);
    Hotel findHotelByApartment(String apartment);

    @Query("select h from Hotel h where h.discount > 0 ")
    List<Hotel> getHotelByDiscount();


}
