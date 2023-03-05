package com.sam.springbootmall.service;

import com.sam.springbootmall.constant.ProductCategory;
import com.sam.springbootmall.dto.ProductQueryParams;
import com.sam.springbootmall.dto.ProductRequest;
import com.sam.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    Integer countProduct(ProductQueryParams productQueryParams);
}
