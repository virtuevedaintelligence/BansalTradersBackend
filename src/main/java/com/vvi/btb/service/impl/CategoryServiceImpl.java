package com.vvi.btb.service.impl;

import com.vvi.btb.constant.CategoryImplConstant;
import com.vvi.btb.domain.entity.Category;
import com.vvi.btb.domain.mapper.CategoryMapper;
import com.vvi.btb.domain.request.CategoryRequest;
import com.vvi.btb.domain.response.CategoryResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.dao.CategoryDao;
import com.vvi.btb.service.abs.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public record CategoryServiceImpl(CategoryDao categoryDao,
                                  CategoryMapper categoryMapper) implements CategoryService {



    @Override
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) throws CategoryException {

        Category category = new Category();
        CategoryResponse categoryResponse = new CategoryResponse();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryType(categoryRequest.getCategoryType());
        try {
            Category savedCategory = categoryDao.save(category);
            categoryResponse.setCategoryName(savedCategory.getCategoryName());
            categoryResponse.setCategoryType(savedCategory.getCategoryType());
        }
        catch (Exception ex){
            throw new CategoryException(ex.getMessage(), CategoryImplConstant.CATEGORY_CREATE_ERROR_MESSAGE);
        }
        return categoryResponse;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) throws CategoryException {
        Optional<Category> category = categoryDao.findById(id);
        CategoryResponse categoryResponse = new CategoryResponse();
        Category cat;
        if(category.isPresent()){
            cat = category.get();
            cat.setCategoryName(categoryRequest.getCategoryName());
            cat.setCategoryType(categoryRequest.getCategoryType());
            try{
                categoryResponse = categoryMapper.apply(categoryDao.save(cat));
            }catch (Exception ex){
                throw new CategoryException(ex.getMessage(), CategoryImplConstant.CATEGORY_UPDATE_ERROR_MESSAGE);
            }
        }
        return categoryResponse;
    }

    @Override
    public boolean deleteCategory(Long id) throws CategoryException {
        try{
            categoryDao.deleteById(id);
        }
        catch (Exception ex){
            throw new CategoryException(ex.getMessage(), CategoryImplConstant.CATEGORY_DELETE_ERROR_MESSAGE);
        }
        return true;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return buildCategories(categoryDao.findAll());
    }

    @Override
    public CategoryResponse getCategoryByName(String categoryName) throws CategoryException {
        Optional<Category> category = categoryDao.findByCategoryName(categoryName);
        CategoryResponse categoryResponse = null;
        if(category.isPresent()){
        categoryResponse = categoryMapper.apply(category.get());
        }
        else {
            log.info(CategoryImplConstant.CATEGORY_NOT_FOUND);
        }
        return categoryResponse;
    }

    private List<CategoryResponse> buildCategories(List<Category> categories){
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        categories.forEach(category -> {
            categoryResponses.add(categoryMapper.apply(category));
        });
        return categoryResponses;
    }
}
