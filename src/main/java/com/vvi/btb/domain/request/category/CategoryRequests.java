package com.vvi.btb.domain.request.category;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryRequests implements Serializable {

    List<CategoryRequest> categoryRequests;
}
