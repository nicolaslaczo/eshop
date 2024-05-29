package com.nicolas.eshop.admin.category;

import com.nicolas.eshop.admin.category.customer.CategoryDto;
import com.nicolas.eshop.admin.handlers.CategoryNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    // ----------------------------- ADMIN --------------------- //

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getOneCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    @Override
    public void addNewCategory(Category category) {
        validateCategoryName(category.getCategoryName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        Category existingCategory = getOneCategory(categoryId);
        categoryRepository.delete(existingCategory);
    }

    @Override
    public void addImageToCategory(Integer categoryId, MultipartFile file) {
        Category existingCategory = getOneCategory(categoryId);
        validateFile(file);
        try {
            existingCategory.setImage(encodeFileToBase64(file));
            categoryRepository.save(existingCategory);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image for category: " + existingCategory.getCategoryName(), e);
        }
    }

    private void validateCategoryName(String categoryName) {
        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new IllegalArgumentException("Category with name '" + categoryName + "' already exists.");
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            throw new IllegalArgumentException("File name contains invalid path sequence: " + fileName);
        }
    }

    private String encodeFileToBase64(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }


    // -------------------------------- CUSTOMER ------------------------------ //

    // Show all categories
    @Override
    public List<CategoryDto> getAllCategoriesForCustomer() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }






















}



