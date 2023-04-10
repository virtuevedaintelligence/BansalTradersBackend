package com.vvi.btb.domain.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String orderPlacedDate;
    private int totalOrderPrice;
    private List<OrderedProducts> orderedProducts;
    long userId;
    String payment;
}
