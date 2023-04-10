package com.vvi.btb.repository;

import com.vvi.btb.dao.OrderProductDao;
import com.vvi.btb.domain.entity.OrderProducts;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record OrderProductRepository(OrderProductDao orderProductDao) {


    public void saveOrder(List<OrderProducts> orderProducts){
        orderProductDao.saveAll(orderProducts);
    }
}
