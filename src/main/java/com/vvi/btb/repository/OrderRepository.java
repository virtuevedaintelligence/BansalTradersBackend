package com.vvi.btb.repository;

import com.vvi.btb.dao.OrderDao;
import com.vvi.btb.domain.entity.Order;

public record OrderRepository(OrderDao orderDao) {

    public Order createOrder(Order order){
        return orderDao.save(order);
    }
}
