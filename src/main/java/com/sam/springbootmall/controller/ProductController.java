package com.sam.springbootmall.controller;

import com.sam.springbootmall.constant.ProductCategory;
import com.sam.springbootmall.dto.ProductQueryParams;
import com.sam.springbootmall.dto.ProductRequest;
import com.sam.springbootmall.model.Product;
import com.sam.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProduct(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            // 排序Sorting
            @RequestParam(defaultValue = "create_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort) {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);

        List<Product> productList = productService.getProducts(productQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody @Valid ProductRequest productRequest) {
        // 檢查product是否存在
        Product product = productService.getProductById(productId);
        if (Objects.isNull(product)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 修改商品的數據
        productService.updateProduct(productId, productRequest);
        Product updatedProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public static void main(String[] args) {
        String i = "轉不過啦";
        int a = parseInt(i);
        System.out.println(a);
    }

    public static int parseInt(String i) {
        try {
            int ii = Integer.parseInt(i);
            return ii;
        } catch (NumberFormatException n) {
            return 0;
        }
    }
}
