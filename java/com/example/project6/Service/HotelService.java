package com.example.project6.Service;

import com.example.project6.Api.ApiException;
import com.example.project6.Model.Hotel;
import com.example.project6.Model.User;
import com.example.project6.Repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public void add(User user, Hotel hotel) {
        hotel.setUser(user);
        hotelRepository.save(hotel);
    }

    public void update(User user,Integer id,Hotel hotel) {
        Hotel oldHotel = hotelRepository.findHotelByUserAndId(user,id);

        if(oldHotel == null) {
            throw new ApiException("wrong id");
        }
        oldHotel.setName(hotel.getName());
        oldHotel.setPhone(hotel.getPhone());
        oldHotel.setApartment(hotel.getApartment());
        oldHotel.setFloorNumber(hotel.getFloorNumber());
        oldHotel.setRooms(hotel.getRooms());
        oldHotel.setPrice(hotel.getPrice());
        hotelRepository.save(oldHotel);

    }
    public void delete(User user,Integer id) {
        Hotel hotel = hotelRepository.findHotelByUserAndId(user,id);
        if(hotel == null) {
            throw new ApiException("wrong id");
        }
        hotelRepository.delete(hotel);
    }

    public Hotel searchApartment(String apartment){
        if(hotelRepository.searchByApartment(apartment) == null){
            throw new ApiException("wrong Apartment name");
        }
        return hotelRepository.searchByApartment(apartment);
    }

    public List<Hotel> getAllDiscount(){
        List<Hotel> hotel = hotelRepository.getHotelByDiscount();
        return hotel;
    }


}
