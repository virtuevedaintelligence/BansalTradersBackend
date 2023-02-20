package com.vvi.btb.controller;

import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.request.order.OrderRequest;
import com.vvi.btb.domain.request.user.UserOTPRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/orders")
@Slf4j
public class OrderResource {

    public ResponseEntity<HttpResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return null;
    }

    public ResponseEntity<HttpResponse> cancelOrder(@PathVariable("orderId") Long orderId){
        return null;
    }

    public ResponseEntity<HttpResponse> getAllOrders(@PathVariable("orderId") Long orderId){
        return null;
    }
}
