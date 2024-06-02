package com.nicolas.eshop.customer.shopingcart;

import com.nicolas.eshop.admin.handlers.ProductNotFoundException;
import com.nicolas.eshop.admin.product.Product;
import com.nicolas.eshop.admin.product.ProductRepository;
import com.nicolas.eshop.admin.product.customer.ProductCustDto;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@SessionScope
@Getter
@Setter
public class ShoppingCartServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private List<Product> cart;

    private final ProductRepository productRepository;

    private ModelMapper mapper;

    private Integer totalItems;



    @Autowired
    public ShoppingCartServiceImpl(ProductRepository productRepository,ModelMapper mapper) {
        this.productRepository = productRepository;
        this.cart = new ArrayList<>();
        this.mapper = mapper;
        this.totalItems =0;
    }

    public void addToCart(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        cart.add(product);
        logger.info("Product added to cart: {}", product.getTitle());
        totalItems++;
    }

    public List<ProductCustDto> getCart() {
        logger.info("Returning cart with {} items", cart.size());
        return cart.stream()
                .map(item -> mapper.map(item, ProductCustDto.class))
                .collect(Collectors.toList());
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void checkoutCart() {
        cart.clear();
    }






}