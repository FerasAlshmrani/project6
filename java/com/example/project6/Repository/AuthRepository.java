package com.example.project6.Repository;

import com.example.project6.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);

    User findUserById(Integer id);
}
