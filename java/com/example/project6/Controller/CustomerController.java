package com.example.project6.Controller;

import com.example.project6.Api.ApiResponse;
import com.example.project6.DTO.CustomerDTO;
import com.example.project6.Model.Customer;
import com.example.project6.Model.User;
import com.example.project6.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    // ADMIN ROLE
    @GetMapping("/get")
    public ResponseEntity getAllCustomers(){
        return ResponseEntity.status(200).body(customerService.allCustomers());
    }

    // USER OR ADMIN ROLE
    @PostMapping("/add")
    public ResponseEntity addCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid Customer customer){
        customerService.addCustomer(user.getId(), customer);
        return ResponseEntity.status(200).body(new ApiResponse("Customer added successfully"));
    }

    // USER ROLE
    @PutMapping("/update/{id}")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody @Valid Customer customer){
        customerService.updateCustomer(user.getId(), customer);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated successfully"));
    }

    // ADMIN ROLE
    @DeleteMapping("/delete/{username}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user,@PathVariable String username){
        customerService.deleteCustomer(username);
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted successfully"));
    }

    // USER ROLE
    @PutMapping("/add-balance/{balance}")
    public ResponseEntity addBalance(@AuthenticationPrincipal User user, @PathVariable Integer balance){
        customerService.addBalance(user, balance);
        return ResponseEntity.status(200).body(new ApiResponse("Balance added successfully"));
    }

    // USER ROLE
    @PutMapping("/book-hotel/{apartment}/{duration}")
    public ResponseEntity bookHotel(@AuthenticationPrincipal User user, @PathVariable String apartment, @PathVariable Integer duration){
        customerService.bookHotel(user, apartment, duration);
        return ResponseEntity.status(200).body(new ApiResponse("Hotel booked successfully"));
    }
    // ADMIN OR HOTEL ROLE
    @PutMapping("/duration-done/{username}/{apartment}")
    public ResponseEntity durationDone(@AuthenticationPrincipal User user, @PathVariable String username, @PathVariable String apartment){
        customerService.durationDone(user, username, apartment);
        return ResponseEntity.status(200).body(new ApiResponse("Duration Done successfully"));
    }

    // ADMIN
    @GetMapping("/get-only-booked")
    public ResponseEntity getOnlyBooked(){
        return ResponseEntity.status(200).body(customerService.getOnlyBooked());
    }


}
