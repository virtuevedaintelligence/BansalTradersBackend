package com.vvi.btb.domain.mapper.order;

import com.vvi.btb.constant.CommonImplConstant;
import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.domain.entity.Order;
import com.vvi.btb.domain.entity.OrderStatus;
import com.vvi.btb.domain.entity.Payment;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.request.order.OrderRequest;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.repository.UserRepository;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.function.Function;

public record OrderMapper(OrderProductsMapper orderProductsMapper,
                          UserRepository userRepository) implements Function<OrderRequest, Order> {
    @SneakyThrows
    @Override
    public Order apply(OrderRequest orderRequest) {
        return Order
                .builder()
                .orderPlacedDate(orderRequest.getOrderPlacedDate())
                .orderStatus(OrderStatus.INITIATED)
                .payment(Payment.INITIATED)
                .totalOrderPrice(orderRequest.getTotalOrderPrice())
                .user(setUserId(orderRequest.getUserId()))
                .orderProducts(orderProductsMapper.apply(orderRequest.getOrderedProducts()))
                .build();
    }

    private User setUserId(long userId) throws UserException {
        Optional<User> user = userRepository.findUserById(userId);
        if(user.isEmpty()){
            throw new UserException(CommonImplConstant.PLEASE_CONTACT_ADMIN, UserImplConstant.USER_NOT_FOUND);
        }
        return user.get() ;
    }
}
