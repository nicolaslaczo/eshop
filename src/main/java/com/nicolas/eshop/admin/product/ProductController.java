package com.nicolas.eshop.admin.product;

import com.nicolas.eshop.admin.product.customer.ProductCustDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    // Add new product
    @PostMapping("/admin/category/{categoryId}/product")
    public ResponseEntity<HttpStatus> addNewProduct(@PathVariable Integer categoryId,@RequestBody Product product) {
        productService.addNewProduct(categoryId,product);
        return ResponseEntity.ok().build();
    }

    // Get all products
    @GetMapping("/admin/product")
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(productService.getAllProducts(page,size));

    }

    // Get products by category id
    @GetMapping("/admin/category/{categoryId}/product")
    public ResponseEntity<List<Product>> getProductByCategoryId(@PathVariable Integer categoryId) {
        List<Product> productList = productService.productForCategory(categoryId);
        return ResponseEntity.ok().body(productList);
    }

    // Get product by id
    @GetMapping("/admin/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer productId) {
        ProductDto productDto = productService.getOneProduct(productId);
        return ResponseEntity.ok().body(productDto);
    }

    // Add main image to product
    @PostMapping("/admin/product/{productId}/image")
    public ResponseEntity<HttpStatus>addImageToProduct(@PathVariable Integer productId, @RequestParam("file")MultipartFile file) {
        productService.addImageToProduct(productId,file);
        return ResponseEntity.ok().build();
    }

    // Update product
    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable Integer productId,@RequestBody ProductDto productDto) {
        productService.updateProduct(productId,productDto);
        return ResponseEntity.ok().build();
    }
    // --------------------------------------------- CUSTOMER ----------------------- //

    @GetMapping("/public/category/{categoryId}/product")
    public ResponseEntity<List<ProductCustDto>> getProductsForCategoryCustomer(@PathVariable Integer categoryId) {
        List<ProductCustDto> productCustDtos = productService.productForCategoryForCustomer(categoryId);
        return ResponseEntity.ok().body(productCustDtos);
    }

























}
