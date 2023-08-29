package com.example.project6.Controller;

import com.example.project6.Api.ApiResponse;
import com.example.project6.Model.User;
import com.example.project6.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/get-users")
    public ResponseEntity getUsers(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User user){
        userService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("User registered successfully"));
    }

    @PutMapping("/update/{username}")
    public ResponseEntity update(@AuthenticationPrincipal User adminUser,@RequestBody @Valid User user,@PathVariable String username){
        userService.updateUser(user,username);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity delete(@AuthenticationPrincipal User adminUser,@PathVariable String username){
        userService.deleteUser(username);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }

    @PutMapping("/add-hotel-role/{username}")
    public ResponseEntity addHotelRole(@AuthenticationPrincipal User adminUser,@PathVariable String username){
        userService.transferCustomerToHotel(username);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @PutMapping("/add-user-role/{username}")
    public ResponseEntity addUserRole(@AuthenticationPrincipal User adminUser,@PathVariable String username){
        userService.transferHotelToUser(username);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

}
