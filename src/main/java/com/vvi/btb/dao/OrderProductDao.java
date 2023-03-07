package com.vvi.btb.dao;

import com.vvi.btb.domain.entity.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductDao extends JpaRepository<OrderProducts,Long> {
}
