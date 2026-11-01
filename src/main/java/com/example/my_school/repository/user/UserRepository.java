package com.example.my_school.repository.user;

import com.example.my_school.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select us from User as us where us.phoneNumber=?1 order by us.id desc")
    Optional<User> findByPhoneNumber(String phone);

    @Query("select us from User as us where us.email=?1")
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("delete from User as us where us.id=?1")
    Integer deleteUser(Integer id);

    @Query("select us from User us order by us.id desc")
    Page<User> findAllUser (Pageable pageable);

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR u.phoneNumber LIKE CONCAT('%', :keyword, '%')
        order by u.id desc
    """)
    Page<User> search(String keyword, Pageable pageable);

}
