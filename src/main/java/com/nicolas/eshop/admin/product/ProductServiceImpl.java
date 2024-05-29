package com.nicolas.eshop.admin.product;

import com.nicolas.eshop.admin.category.Category;
import com.nicolas.eshop.admin.category.CategoryRepository;
import com.nicolas.eshop.admin.category.CategoryService;
import com.nicolas.eshop.admin.handlers.CategoryNotFoundException;
import com.nicolas.eshop.admin.handlers.ProductNotFoundException;
import com.nicolas.eshop.admin.product.customer.ProductCustDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    // --------------------------------- ADMIN ------------------------ //


    // Add new product
    public void addNewProduct(Integer categoryId,Product product) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            Optional<Product>optionalProduct = productRepository.findByProductNum(product.getProductNum());
            if (optionalProduct.isPresent()) {
                Product existingProduct = optionalProduct.get();
                existingProduct.setAmount(existingProduct.getAmount() + product.getAmount());
                productRepository.save(existingProduct);

            }else {
                Product newProduct = new Product();
                newProduct.setTitle(product.getTitle());
                newProduct.setDescription(product.getDescription());
                newProduct.setAmount(product.getAmount());
                newProduct.setBuyPrice(product.getBuyPrice());
                newProduct.setSellPrice(product.getSellPrice());
                newProduct.setProductNum(product.getProductNum());
                newProduct.setCategory(existingCategory);
                productRepository.save(newProduct);
            }

        } else {
            throw new CategoryNotFoundException(categoryId);
        }
    }

    // Add main image to product
    public void addImageToProduct(Integer productId, MultipartFile file) {
        Optional<Product>optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                throw new IllegalArgumentException(fileName + "is not a valid");
            }
            try {
                existingProduct.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
                productRepository.save(existingProduct);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new ProductNotFoundException(productId);
        }
    }


    // Get all products in database
    public Page<ProductDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductDto> productDtos = productPage.stream()
                .map(product -> {
                    ProductDto dto = mapper.map(product, ProductDto.class);
                    dto.setCategoryId(product.getCategory().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(productDtos, pageable, productPage.getTotalElements());
    }

    // Get products for category
    public List<Product> productForCategory(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Get one product
    @Override
    public ProductDto getOneProduct(Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            return mapper.map(existingProduct,ProductDto.class);
        }else {
            throw new ProductNotFoundException(productId);
        }
    }

    public void updateProduct(Integer productId, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            Category productCategory = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(productDto.getCategoryId()));

            // Aktualizácia hodnôt produktu z productDto iba ak nie sú null
            if (productDto.getCategoryId() != null) {
                existingProduct.setCategory(productCategory);
            }
            if (productDto.getProductNum() != null) {
                existingProduct.setProductNum(productDto.getProductNum());
            }
            if (productDto.getTitle() != null) {
                existingProduct.setTitle(productDto.getTitle());
            }
            if (productDto.getDescription() != null) {
                existingProduct.setDescription(productDto.getDescription());
            }
            if (productDto.getAmount() != null) {
                existingProduct.setAmount(productDto.getAmount());
            }
            if (productDto.getBuyPrice() != null) {
                existingProduct.setBuyPrice(productDto.getBuyPrice());
            }
            if (productDto.getSellPrice() != null) {
                existingProduct.setSellPrice(productDto.getSellPrice());
            }


            productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    // ------------------------------------ CUSTOMER -------------------------- //

    // Get products for category
    @Override
    public List<ProductCustDto> productForCategoryForCustomer(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (! products.isEmpty()) {
            return products.stream()
                    .map(product -> mapper.map(product, ProductCustDto.class))
                    .collect(Collectors.toList());
        }else {
            return new ArrayList<>();
        }
    }




}
