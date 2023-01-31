package com.vvi.btb.domain.request;

import lombok.Data;
import java.io.Serializable;
@Data
public class CategoryRequest implements Serializable {
    private String categoryName;
}
