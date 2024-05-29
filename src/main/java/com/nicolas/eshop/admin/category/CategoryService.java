package com.nicolas.eshop.admin.category;

import com.nicolas.eshop.admin.category.customer.CategoryDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    // -------------------------- ADMIN ----------------------- //

    List<Category> getAllCategories();

    void addNewCategory(Category category);

    void deleteCategoryById(Integer categoryId);

    public Category getOneCategory(Integer categoryId);

    void addImageToCategory(Integer categoryId, MultipartFile file);



    // --------------------------- CUSTOMER --------------------- //

    List<CategoryDto> getAllCategoriesForCustomer();
















}
