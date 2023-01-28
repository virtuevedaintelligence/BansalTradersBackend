package com.vvi.btb.dao;

import com.vvi.btb.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}
