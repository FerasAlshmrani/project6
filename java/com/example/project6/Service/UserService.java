package com.example.project6.Service;

import com.example.project6.Api.ApiException;
import com.example.project6.Model.User;
import com.example.project6.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final AuthRepository authRepository;

    public List<User> getUsers(){
        return authRepository.findAll();
    }
    public void register(User user){
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("USER");
        authRepository.save(user);
    }

    public void updateUser(User user,String username){
        User oldUser = authRepository.findUserByUsername(username);
        if(oldUser == null){
            throw new ApiException("User does not exist");
        }
        oldUser.setUsername(user.getUsername());
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        oldUser.setPassword(hash);
        authRepository.save(oldUser);
    }

    public void deleteUser(String username){
        User user = authRepository.findUserByUsername(username);
        if(user == null){
            throw new ApiException("User does not exist");
        }
        authRepository.delete(user);
    }

    public void transferCustomerToHotel(String username){
        User user = authRepository.findUserByUsername(username);
        if(user == null){
            throw new ApiException("User does not exist");
        }
        if(user.getRole().equals("Hotel")){
            throw new ApiException("This username is already Hotel role");
        }
        user.setCustomer(null);
        user.setRole("HOTEL");
        authRepository.save(user);
    }

    public void transferHotelToUser(String username){
        User user = authRepository.findUserByUsername(username);
        if(user == null){
            throw new ApiException("User does not exist");
        }
        if(user.getRole().equals("USER")){
            throw new ApiException("This username is already USER role");
        }
        user.setCustomer(null);
        user.setRole("USER");
        authRepository.save(user);
    }
}
