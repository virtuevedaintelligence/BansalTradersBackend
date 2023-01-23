package com.vvi.btb.domain.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private int productPrice;
    private int quantity; // 10 packets 20 packets
    private int weight; // 250gm 500gm
    private boolean isFeatured;
    private boolean isActive;
    private String categoryName;
}
