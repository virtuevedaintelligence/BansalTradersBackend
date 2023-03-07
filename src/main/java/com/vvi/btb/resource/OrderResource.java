package com.vvi.btb.resource;

import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.request.order.OrderRequest;
import com.vvi.btb.exception.domain.OrderException;
import com.vvi.btb.service.abs.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/orders")
@Slf4j
public record OrderResource(OrderService orderService) {

    @PostMapping("/placeOrder")
    public ResponseEntity<HttpResponse> createOrder(@RequestBody OrderRequest orderRequest) throws OrderException {
        log.info("Code Reached create Order");

        orderService.createOrder(orderRequest);
        return null;
    }

    public ResponseEntity<HttpResponse> cancelOrder(@PathVariable("orderId") Long orderId){
        return null;
    }

    public ResponseEntity<HttpResponse> getAllOrders(@PathVariable("orderId") Long orderId){
        return null;
    }
}
