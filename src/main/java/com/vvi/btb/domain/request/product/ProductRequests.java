package com.vvi.btb.domain.request.product;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductRequests implements Serializable {
    List<ProductRequest> productRequests;
}
