package com.nicolas.eshop.admin.product;

import com.nicolas.eshop.admin.product.customer.ProductCustDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    // ----------------- ADMIN ---------------------------//

    void addNewProduct(Integer categoryId,Product product);

    void addImageToProduct(Integer productId, MultipartFile file);

    Page<ProductDto> getAllProducts(int page, int size);

    List<Product> productForCategory(Integer categoryId);

    ProductDto getOneProduct(Integer productId);

    void updateProduct(Integer productId, ProductDto productDto);

    // ------------------------- CUSTOMER ------------------- //

    List<ProductCustDto> productForCategoryForCustomer(Integer categoryId);


}
