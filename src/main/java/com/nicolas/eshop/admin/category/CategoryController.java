package com.nicolas.eshop.admin.category;

import com.nicolas.eshop.admin.category.customer.CategoryDto;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // -------------------------------- ADMIN --------------------------- //

    // Get all categories
    @GetMapping("/admin/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Get one category
    @GetMapping("/admin/category/{categoryId}")
    public ResponseEntity<Category> getOneCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(categoryService.getOneCategory(categoryId));
    }

    // Create new category
    @PostMapping("/admin/category")
    public ResponseEntity<HttpStatus> createNewCategory(@RequestBody Category category) {
        categoryService.addNewCategory(category);
        return ResponseEntity.ok().build();
    }

    // Add image to category
    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<HttpStatus> addImageToCategory(@PathVariable Integer categoryId, @RequestParam(name = "file") MultipartFile file) {
        categoryService.addImageToCategory(categoryId,file);
        return ResponseEntity.ok().build();
    }


    // --------------------------------- CUSTOMER ------------------------------- //

    @GetMapping("/public/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesForCustomer() {
        List<CategoryDto> categoryDtos = categoryService.getAllCategoriesForCustomer();
        return ResponseEntity.ok().body(categoryDtos);
    }
















































}
