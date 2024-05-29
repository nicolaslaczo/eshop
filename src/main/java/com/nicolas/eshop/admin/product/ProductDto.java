package com.nicolas.eshop.admin.product;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;

    private Integer categoryId;

    private String productNum;

    private String title;

    private String description;

    private Integer amount;

    private Integer buyPrice;

    private Integer sellPrice;

    private String image;
}
