package com.nicolas.eshop.customer.shopingcart;

import com.nicolas.eshop.admin.product.Product;
import com.nicolas.eshop.customer.orders.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "shoppingCart")
    private Orders orders;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shopping_cart_product",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();



























}
