package com.nicolas.eshop.customer.shopingcart;

import com.nicolas.eshop.admin.product.Product;
import com.nicolas.eshop.admin.product.customer.ProductCustDto;
import com.nicolas.eshop.customer.customer.Customer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ShoppingCartController {

    private final ShoppingCartServiceImpl shoppingCartService;
    @Autowired
    public ShoppingCartController(ShoppingCartServiceImpl shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/public/cart/add/{productId}")
    public ResponseEntity<HttpStatus> addToCar(@PathVariable Integer productId) {
        shoppingCartService.addToCart(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public/cart")
    public ResponseEntity<List<ProductCustDto>> getCart() {
        List<ProductCustDto> productCustDtoList = shoppingCartService.getCart();
        return ResponseEntity.ok().body(productCustDtoList);
    }

    @GetMapping("/public/cart/items")
    public ResponseEntity<Integer> getShoppingCartItems() {
        return ResponseEntity.ok().body(shoppingCartService.getTotalItems());
    }

    @DeleteMapping("/public/cart/checkout")
    public ResponseEntity<HttpStatus> checkoutCart() {
        shoppingCartService.checkoutCart();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/public/cart/create")
    public ResponseEntity<HttpStatus> createNewOrder(@RequestBody Customer customer) {
        shoppingCartService.createNewOrder(customer);
        return ResponseEntity.ok().build();
    }













}
