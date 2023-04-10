package com.vvi.btb.dao;

import com.vvi.btb.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order,Long> {
}
