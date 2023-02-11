package com.vvi.btb.dao;

import com.vvi.btb.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
   Optional<User> findByContactNumber(Long contactNumber);
   Optional<User> findUserByUserName(String userName);
}
