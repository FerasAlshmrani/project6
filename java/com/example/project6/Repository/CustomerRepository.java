package com.example.project6.Repository;


import com.example.project6.Model.Customer;
import com.example.project6.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("select c from Customer c where c.user =?1 and c.id =?2")
    Customer findCustomerByUserAndId(User user , Integer id);

    Customer findCustomerByUser(User user);

    @Query("select c from Customer c where c.isBooked = true")
    List<Customer> getCustomerByIsBooked();

}
