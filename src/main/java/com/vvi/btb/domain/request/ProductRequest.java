package com.vvi.btb.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductRequest implements Serializable {
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private int productPrice;
    private int productPriceWithoutDiscount;
    private int quantity; // 10 packets 20 packets
    private int weight; // 250gm 500gm
    private String featured;
    private String isactive;
    private String categoryName;
}
