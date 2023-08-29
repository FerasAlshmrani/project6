package com.example.project6.Service;

import com.example.project6.Api.ApiException;
import com.example.project6.DTO.CustomerDTO;
import com.example.project6.Model.Customer;
import com.example.project6.Model.Hotel;
import com.example.project6.Model.User;
import com.example.project6.Repository.AuthRepository;
import com.example.project6.Repository.CustomerRepository;
import com.example.project6.Repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final HotelRepository hotelRepository;

    public List<Customer> allCustomers(){

        List<Customer> customerList =  customerRepository.findAll();

        return customerList;
    }

    public void addCustomer(Integer user_id, Customer customer){
/*
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setBalance(customerDTO.getBalance());
*/

        User user = authRepository.findUserById(user_id);
        customer.setUser(user);
        customerRepository.save(customer);
    }

    public void updateCustomer(Integer user_id, Customer customer){

        User user = authRepository.findUserById(user_id);

        Customer customer1 = customerRepository.findCustomerByUserAndId(user,user.getCustomer().getId());

        if(customer1== null){
            throw new ApiException("Customer not found");
        }
        customer1.setName(customer.getName());
        customer1.setEmail(customer.getEmail());
        customer1.setPhone(customer.getPhone());
        customer1.setBalance(customer.getBalance());
        customerRepository.save(customer1);
    }

    public void deleteCustomer(String username){

        User user = authRepository.findUserByUsername(username);
        if(user==null){
            throw new ApiException("User not found");
        }
        if(user.getRole().equals("USER")) {

            Customer customer = customerRepository.findCustomerByUser(user);

            if(customer==null){
                throw new ApiException("this user dont have customer information");
            }
            user.setCustomer(null);
            authRepository.save(user);
            customerRepository.delete(customer);
        }else {

            throw new ApiException("User is not Customer");
        }

    }
    public void addBalance(User user,Integer balance){
        Customer customer = customerRepository.findCustomerByUser(user);
        if(customer == null){
            throw new ApiException("Customer not found");
        }
        customer.setBalance(customer.getBalance()+balance);
        customerRepository.save(customer);
    }
    public void bookHotel (User user, String apartment,Integer duration) {

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        Hotel hotel = hotelRepository.findHotelByApartment(apartment);
        if (hotel == null) {
            throw new ApiException("Wrong Apartment");
        }

        if(customer.getIsBooked()){
            throw new ApiException("this user is already booked");
        }
        if(hotel.getIsBooked()){
            throw new ApiException("this Apartment is already booked");
        }

        if(hotel.getDiscount() == 0) {
            if (customer.getBalance() < hotel.getPrice() * duration) {
                throw new ApiException("you dont have much money");
            }

            customer.setBalance(customer.getBalance()-hotel.getPrice()*duration);
        } else{
            if (customer.getBalance() < (hotel.getPrice()*(hotel.getDiscount())/100)*duration) {
                throw new ApiException("you dont have much money");
            }
            customer.setBalance((Integer) customer.getBalance()-(hotel.getPrice()*(hotel.getDiscount())/100)*duration);
        }

        customer.setIsBooked(true);
        customer.setDuration(duration);
        customer.setWhichApartment(hotel.getApartment());

        hotel.setIsBooked(true);
        hotel.setByUsername(user.getUsername());

        customerRepository.save(customer);
        hotelRepository.save(hotel);

    }

    public void durationDone(User user,String username, String apartment){
        User user1 = authRepository.findUserByUsername(username);

        Customer customer = customerRepository.findCustomerByUser(user1);
        if(user1 == null){
            throw new ApiException("Wrong username");
        }

        if(customer == null){
            throw new ApiException("Customer not found");
        }

        Hotel hotel = hotelRepository.searchByApartment(apartment);

        if(hotel == null){
            throw new ApiException("Wrong Apartment");
        }

        if(customer.getIsBooked() == false){
            throw new ApiException("this user dont have book yet to remove");
        }
        if(hotel.getIsBooked() == false){
            throw new ApiException("this Apartment dont have book yet to remove");
        }

        customer.setIsBooked(false);
        customer.setDuration(0);
        customer.setWhichApartment("");

        hotel.setIsBooked(false);
        hotel.setByUsername("");

        customerRepository.save(customer);
        hotelRepository.save(hotel);

    }

    public List<Customer> getOnlyBooked(){
        return customerRepository.getCustomerByIsBooked();
    }


}
