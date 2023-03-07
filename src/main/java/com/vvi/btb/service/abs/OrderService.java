package com.vvi.btb.service.abs;

import com.vvi.btb.domain.request.order.OrderRequest;
import com.vvi.btb.domain.response.OrderResponse;
import com.vvi.btb.exception.domain.OrderException;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest) throws OrderException;
}
