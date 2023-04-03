package com.vvi.btb.service.impl;

import com.vvi.btb.constant.CategoryImplConstant;
import com.vvi.btb.constant.CommonImplConstant;
import com.vvi.btb.domain.entity.Category;
import com.vvi.btb.domain.mapper.category.CategoryEntityMapper;
import com.vvi.btb.domain.mapper.category.CategoryMapper;
import com.vvi.btb.domain.request.category.CategoryRequest;
import com.vvi.btb.domain.request.category.CategoryRequests;
import com.vvi.btb.domain.response.CategoryResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.dao.CategoryDao;
import com.vvi.btb.service.abs.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public record CategoryServiceImpl(CategoryDao categoryDao,
                                  CategoryMapper categoryMapper,
                                  CategoryEntityMapper categoryEntityMapper) implements CategoryService {



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
    public boolean importCategories(CategoryRequests categoryRequests) throws CategoryException {
        try {
            List<Category> categories = categoryRequests
                    .getCategoryRequests().stream()
                    .map(categoryEntityMapper::apply).toList();
            categoryDao.saveAll(categories);
            return true;
        }
        catch (Exception ex){
            throw new CategoryException(ex.getMessage(), CommonImplConstant.PLEASE_CONTACT_ADMIN);
        }
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
    public Deque<CategoryResponse> getAllCategories() {
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

    private Deque<CategoryResponse> buildCategories(List<Category> categories){
        Deque<CategoryResponse> categoryResponses = new LinkedList<>();
        categories.forEach(category -> {
            categoryResponses.add(categoryMapper.apply(category));
        });
        categoryResponses.addFirst(new CategoryResponse(101L,"all","Dryfruit"));
        return categoryResponses;
    }
}
