package com.sam.springbootmall.dto;

import com.sam.springbootmall.constant.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQueryParams {
    ProductCategory category;
    String search;
    String orderBy;
    String sort;
    Integer limit;
    Integer offset;
}
