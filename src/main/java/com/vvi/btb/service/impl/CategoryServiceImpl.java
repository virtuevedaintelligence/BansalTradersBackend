package com.vvi.btb.service.impl;

import com.vvi.btb.constant.CategoryImplConstant;
import com.vvi.btb.domain.entity.Category;
import com.vvi.btb.domain.request.CategoryRequest;
import com.vvi.btb.domain.response.CategoryResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.repo.CategoryDao;
import com.vvi.btb.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {


    private CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }


    @Override
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) throws CategoryException {

        Category category = new Category();
        CategoryResponse categoryResponse = new CategoryResponse();
        category.setCategoryName(categoryRequest.getCategoryName());
        try {
            Category save = categoryDao.save(category);
            categoryResponse.setCategoryName(save.getCategoryName());
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
        Category cat = null;
        if(category.isPresent()){
            cat = category.get();
            cat.setCategoryName(categoryRequest.getCategoryName());
            try{
                categoryDao.save(cat);
            }catch (Exception ex){
                throw new CategoryException(ex.getMessage(), CategoryImplConstant.CATEGORY_UPDATE_ERROR_MESSAGE);
            }
            categoryResponse.setCategoryName(cat.getCategoryName());
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
        List<CategoryResponse> categoryResponses= new ArrayList<>();
        return build(categoryDao.findAll(),categoryResponses);
    }

    @Override
    public CategoryResponse getCategoryByName(String categoryName) throws CategoryException {
        Optional<Category> category = categoryDao.findByCategoryName(categoryName);
        CategoryResponse categoryResponse = null;
        if(category.isPresent()){
            categoryResponse.setCategoryName(category.get().getCategoryName());
        }
        else {
            log.info(CategoryImplConstant.CATEGORY_NOT_FOUND);
        }
        return categoryResponse;
    }

    private List<CategoryResponse> build(List<Category> categories, List<CategoryResponse> categoryResponses){
        categories.forEach(category -> {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setCategoryName(category.getCategoryName());
            categoryResponses.add(categoryResponse);
        });
        return categoryResponses;
    }
}
