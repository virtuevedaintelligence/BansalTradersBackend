package com.vvi.btb.domain.mapper;

import com.vvi.btb.domain.entity.Category;
import com.vvi.btb.domain.response.CategoryResponse;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public record CategoryMapper() implements Function<Category, CategoryResponse> {
    @Override
    public CategoryResponse apply(Category category) {
        return new CategoryResponse(category.getId(), category.getCategoryName(), category.getCategoryType());
    }
}
