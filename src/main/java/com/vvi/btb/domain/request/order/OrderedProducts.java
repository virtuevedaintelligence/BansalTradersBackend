package com.vvi.btb.domain.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProducts {
    private long id;
    private String img;
    private int price;
    private int quantity;
    private int weight;
    private int totalPrice;
    private String name;
    private String category;
}
