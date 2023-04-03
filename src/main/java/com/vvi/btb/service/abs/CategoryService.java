package com.vvi.btb.service.abs;

import com.vvi.btb.domain.request.category.CategoryRequest;
import com.vvi.btb.domain.request.category.CategoryRequests;
import com.vvi.btb.domain.response.CategoryResponse;
import com.vvi.btb.exception.domain.CategoryException;

import java.util.Deque;

public interface CategoryService {

    CategoryResponse saveCategory(CategoryRequest categoryRequest) throws CategoryException;

    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) throws CategoryException;
    boolean importCategories(CategoryRequests categoryRequests) throws CategoryException;
    boolean deleteCategory(Long id) throws CategoryException;

    Deque<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryByName(String categoryName) throws CategoryException;
}
