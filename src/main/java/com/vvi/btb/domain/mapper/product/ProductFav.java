package com.vvi.btb.domain.mapper.product;

import com.vvi.btb.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFav{
    Product product;
    Long userId;
}
