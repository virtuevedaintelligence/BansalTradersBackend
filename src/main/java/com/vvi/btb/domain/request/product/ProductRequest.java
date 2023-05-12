package com.vvi.btb.domain.request.product;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductRequest implements Serializable {
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private int productPrice;
    private int productPriceWithoutDiscount;
    private int quantity; // 10 packets 20 packets
    private int weight; // 250gm 500gm
    private int featured;
    private int isactive;
    private String categoryName;
}
