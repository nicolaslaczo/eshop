package com.nicolas.eshop.admin.product.customer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductCustDto {

    private Integer id;

    private String productNum;

    private String title;

    private String description;

    private Integer amount;

    private Integer sellPrice;

    private String image;

    private Integer quantity;
}
