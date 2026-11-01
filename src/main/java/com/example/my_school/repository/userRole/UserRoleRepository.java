package com.example.my_school.repository.userRole;

import com.example.my_school.entity.User;
import com.example.my_school.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    @Query("select ur.role.name from UserRole ur where ur.user=?1")
    List<String> getAllRoleByUser(User user);

    @Modifying
    @Transactional
    @Query("delete from UserRole ur where ur.user.id=?1 and ur.role.name=?2")
    void deleteByIdUserAnRolenName(Integer idUser,String roleName);
}
