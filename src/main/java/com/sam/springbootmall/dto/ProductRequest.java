package com.sam.springbootmall.dto;

import com.sam.springbootmall.constant.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
public class ProductRequest {
    @NotNull
    private String productName;

    @NotNull
    private ProductCategory category;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;

    private String description;
}
