package com.example.myschool.repository.user;

import com.example.myschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select us from User as us where us.phoneNumber=?1")
    Optional<User> findByPhoneNumber(String phone);

    @Query("select us from User as us where us.email=?1")
    Optional<User> findByEmail(String email);
}
