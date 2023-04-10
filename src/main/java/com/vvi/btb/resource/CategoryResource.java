package com.vvi.btb.resource;

import com.vvi.btb.constant.CategoryImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.request.category.CategoryRequest;
import com.vvi.btb.domain.request.category.CategoryRequests;
import com.vvi.btb.domain.response.CategoryResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.service.abs.CategoryService;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.vvi.btb.constant.CategoryImplConstant.CATEGORY_DELETED_SUCCESSFULLY;
import static com.vvi.btb.constant.CategoryImplConstant.PLEASE_CONTACT_ADMIN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/categories")
@Slf4j
public class CategoryResource {

    private final CategoryService categoryService;
    private final Response response;

    public CategoryResource(CategoryService categoryService, Response response) {
        this.categoryService = categoryService;
        this.response = response;
    }

    @PostMapping("/createCategory")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> createCategory(@RequestBody CategoryRequest categoryRequest) throws CategoryException {
        CategoryResponse category = categoryService.getCategoryByName(categoryRequest.getCategoryName());
        if(category != null){
            return response.response(HttpStatus.CONFLICT, CategoryImplConstant.CATEGORY_ALREADY_EXISTS, category);
        }
        CategoryResponse categoryResponse = categoryService.saveCategory(categoryRequest);
        return response.response(CREATED, CategoryImplConstant.CATEGORY_ADDED_SUCCESSFULLY, categoryResponse);
    }

    @PostMapping("/importCategories")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> importCategories(@RequestBody CategoryRequests categoryRequests) throws CategoryException {
        log.info("Inside importCategories");
        return response.response(CREATED, CategoryImplConstant.CATEGORY_ADDED_SUCCESSFULLY,
                categoryService.importCategories(categoryRequests));
    }

    @PutMapping("/updateCategory/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> updateCategory(@PathVariable("id") long id, @RequestBody CategoryRequest category) throws CategoryException {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, category);
        if(categoryResponse == null){
            return new ResponseEntity<>(new HttpResponse(400, HttpStatus.BAD_REQUEST,PLEASE_CONTACT_ADMIN,
                    "NA", null), HttpStatus.BAD_REQUEST);
        }
        return response.response(OK, CategoryImplConstant.CATEGORY_UPDATED_SUCCESSFULLY, categoryResponse);
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) throws CategoryException {
        categoryService.deleteCategory(categoryId);
        return response.response(OK, CATEGORY_DELETED_SUCCESSFULLY, null);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<HttpResponse> getAllCategories() {
         return response.response(OK,CategoryImplConstant.CATEGORY_FETCHED_SUCCESSFULLY, categoryService.getAllCategories());
    }
}
