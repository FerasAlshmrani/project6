package com.example.project6.Controller;

import com.example.project6.Api.ApiResponse;
import com.example.project6.Model.Hotel;
import com.example.project6.Model.User;
import com.example.project6.Service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    //ADMIN and HOTEL and USER
    @GetMapping("/get-hotel")
    public ResponseEntity getAllHotels() {
        List<Hotel> hotels = hotelService.findAll();
        return ResponseEntity.status(200).body(hotels);
    }

    //HOTEL
    @PostMapping("/add")
    public ResponseEntity addHotel(@AuthenticationPrincipal User user,@RequestBody @Valid Hotel hotel) {
        hotelService.add(user, hotel);
        return ResponseEntity.status(200).body(new ApiResponse("Hotel added successfully"));
    }

    //ADMIN OR HOTEL
    @PutMapping("/update/{id}")
    public ResponseEntity updateHotel(@AuthenticationPrincipal User user, @RequestBody @Valid Hotel hotel,@PathVariable Integer id) {
        hotelService.update(user,id,hotel);
        return ResponseEntity.status(200).body(new ApiResponse("Hotel updated successfully"));
    }

    //ADMIN OR HOTEL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteHotel(@AuthenticationPrincipal User user,@PathVariable Integer id) {
        hotelService.delete(user,id);
        return ResponseEntity.status(200).body(new ApiResponse("Hotel deleted successfully"));
    }

    //HOTEL OR ADMIN OR USER
    @GetMapping("/search-hotel/{apartment}")
    public ResponseEntity searchApartment(@PathVariable String apartment) {
        Hotel hotel = hotelService.searchApartment(apartment);
        return ResponseEntity.status(200).body(hotel);
    }

    //HOTEL OR ADMIN OR USER
    @GetMapping("/get-discount")
    public ResponseEntity getAllDiscount() {
        List<Hotel> hotels = hotelService.getAllDiscount();
        return ResponseEntity.status(200).body(hotels);
    }

}
