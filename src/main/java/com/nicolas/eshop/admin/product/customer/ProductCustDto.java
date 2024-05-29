package com.nicolas.eshop.admin.product.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCustDto {

    private Integer id;

    private String productNum;

    private String title;

    private String description;

    private Integer amount;

    private Integer sellPrice;

    private String image;
}
