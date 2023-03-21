package com.vvi.btb.domain.mapper.category;

import com.vvi.btb.domain.entity.Category;
import com.vvi.btb.domain.request.category.CategoryRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public record CategoryEntityMapper() implements Function<CategoryRequest, Category> {
    @Override
    public Category apply(CategoryRequest categoryRequest) {
        return Category
                .builder()
                .categoryName(categoryRequest.getCategoryName())
                .categoryType(categoryRequest.getCategoryType())
                .build();
    }
}
