package com.vvi.btb.domain.mapper.order;

import com.vvi.btb.domain.entity.OrderProducts;
import com.vvi.btb.domain.request.order.OrderedProducts;

import java.util.function.Function;

public class OrderProductMapper implements Function<OrderedProducts, OrderProducts> {
    @Override
    public OrderProducts apply(OrderedProducts orderedProducts) {
        return OrderProducts
                .builder()
                .img(orderedProducts.getImg())
                .name(orderedProducts.getName())
                .quantity(orderedProducts.getQuantity())
                .price(orderedProducts.getPrice())
                .weight(orderedProducts.getWeight())
                .category(orderedProducts.getCategory())
                .build();
    }
}
