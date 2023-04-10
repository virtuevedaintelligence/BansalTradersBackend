package com.vvi.btb.service.impl;

import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.ProductInformation;
import com.vvi.btb.domain.mapper.order.OrderMapper;
import com.vvi.btb.domain.mapper.order.OrderProductsMapper;
import com.vvi.btb.domain.request.order.OrderRequest;
import com.vvi.btb.domain.request.order.OrderedProducts;
import com.vvi.btb.domain.response.OrderResponse;
import com.vvi.btb.exception.domain.OrderException;
import com.vvi.btb.repository.OrderProductRepository;
import com.vvi.btb.repository.OrderRepository;
import com.vvi.btb.repository.ProductRepository;
import com.vvi.btb.service.abs.OrderService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.vvi.btb.constant.OrderImplConstant.DECREASE_QUANTITY;
import static com.vvi.btb.constant.OrderImplConstant.QUANTITY_ERROR_MESSAGE;

@Service
public record OrderServiceImpl(ProductRepository productRepository,
                               OrderRepository orderRepository,
                               OrderMapper orderMapper) implements OrderService {

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) throws OrderException {
        for (OrderedProducts orderedProduct : orderRequest.getOrderedProducts()) {
            Product product = productRepository.getProductDetail(orderedProduct.getId());
            for (ProductInformation productInformation : Objects.requireNonNull(product).getProductInformation()) {
                if (productInformation.getWeight() == orderedProduct.getWeight() &&
                        productInformation.getQuantity() > orderedProduct.getQuantity()) {
                    productInformation.setQuantity(productInformation.getQuantity() - orderedProduct.getQuantity());
                    orderRepository.createOrder(orderMapper.apply(orderRequest));
                    productRepository.save(product);

                } else {
                    throw new OrderException(QUANTITY_ERROR_MESSAGE, DECREASE_QUANTITY);
                }
            }
        }
        return null;
    }
}
