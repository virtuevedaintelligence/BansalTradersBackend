package com.vvi.btb.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private long productId;
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private int productPrice;
    private int quantity; // 10 packets 20 packets
    private int weight; // 250gm 500gm
    private boolean isFeatured;
    private String categoryName;
}
