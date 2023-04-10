package com.vvi.btb.domain.mapper.order;

import com.vvi.btb.domain.entity.OrderProducts;
import com.vvi.btb.domain.request.order.OrderRequest;
import com.vvi.btb.domain.request.order.OrderedProducts;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public record OrderProductsMapper(OrderProductMapper orderProductMapper)
        implements Function<List<OrderedProducts>, List<OrderProducts> > {


    @Override
    public List<OrderProducts> apply(List<OrderedProducts> orderedProducts) {
        return orderedProducts
                .stream().map(orderProductMapper::apply)
                .collect(Collectors.toList());

    }
}
