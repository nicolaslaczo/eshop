package com.nicolas.eshop.customer.shopingcart;

import com.nicolas.eshop.admin.handlers.ProductNotFoundException;
import com.nicolas.eshop.admin.product.Product;
import com.nicolas.eshop.admin.product.ProductRepository;
import com.nicolas.eshop.admin.product.customer.ProductCustDto;
import com.nicolas.eshop.customer.customer.Customer;
import com.nicolas.eshop.customer.customer.CustomerRepository;
import com.nicolas.eshop.customer.orders.Orders;
import com.nicolas.eshop.customer.orders.OrdersRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private List<Integer> cartProductIds; // Udržujeme len ID produktov v košíku

    private final ProductRepository productRepository;

    private ModelMapper mapper;

    private Integer totalItems;

    private final OrdersRepository ordersRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public ShoppingCartServiceImpl(ProductRepository productRepository, ModelMapper mapper, OrdersRepository ordersRepository,CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.cartProductIds = new ArrayList<>(); // Inicializujeme na prázdny zoznam
        this.mapper = mapper;
        this.totalItems = 0;
        this.ordersRepository = ordersRepository;
        this.customerRepository = customerRepository;
    }

    public void addToCart(Integer productId) {
        cartProductIds.add(productId); // Pridáme len ID produktu do košíka
        totalItems++;
    }

    public List<ProductCustDto> getCart() {
        logger.info("Returning cart with {} items", cartProductIds.size());
        // Načítame produkty podľa ich ID
        return cartProductIds.stream()
                .map(productId -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new ProductNotFoundException(productId));
                    return mapper.map(product, ProductCustDto.class);
                })
                .collect(Collectors.toList());
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void checkoutCart() {
        cartProductIds.clear(); // Vyčistíme zoznam ID produktov v košíku
    }

    @Transactional
    public void createNewOrder(Customer customer) {
        Orders orders = new Orders();

        // Načítame zákazníka podľa jeho ID
        Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer == null) {
            customerRepository.save(customer);
        }
        orders.setCustomer(customer);

        // Načítame produkty podľa ich ID a pridáme ich do objednávky
        List<Product> products = cartProductIds.stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(productId)))
                .collect(Collectors.toList());
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProducts(products);
        orders.setShoppingCart(shoppingCart);

        ordersRepository.save(orders);

        logger.info("Order created for customer: {}", customer.getName());

        checkoutCart();
        totalItems = 0;
    }
}
