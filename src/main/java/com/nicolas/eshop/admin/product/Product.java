package com.nicolas.eshop.admin.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolas.eshop.admin.category.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productNum;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer amount;

    private Integer buyPrice;

    private Integer sellPrice;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    public Product(Integer id, String title, String description, Integer amount, Integer buyPrice, Integer sellPrice, String productNum) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.productNum = productNum;

    }

    public Product(String title, String description, Integer amount, Integer buyPrice, Integer sellPrice, String productNum) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.productNum = productNum;
    }
}
