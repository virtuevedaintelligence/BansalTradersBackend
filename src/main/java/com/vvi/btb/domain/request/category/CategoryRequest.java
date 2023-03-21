package com.vvi.btb.domain.request.category;

import lombok.Data;
import java.io.Serializable;
@Data
public class CategoryRequest implements Serializable {
    private String categoryName;
    private String categoryType;
}
