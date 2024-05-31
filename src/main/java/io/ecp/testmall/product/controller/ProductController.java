package io.ecp.testmall.product.controller;

import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.product.entity.Product;
import io.ecp.testmall.product.entity.ProductDTO;
import io.ecp.testmall.product.entity.ProductListDTO;
import io.ecp.testmall.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String token) {
        String t = JwtUtils.extractToken(token);
        if (!JwtUtils.validateToken(t)) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        productService.saveProduct(productDTO);

        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/productList")
    public Page<ProductListDTO> productList(@PageableDefault(size = 10) Pageable pageable, @RequestHeader("Authorization") String token) {
        String t = JwtUtils.extractToken(token);
        if (!JwtUtils.validateToken(t)) {
            return null;
        }
        Page<Product> products = productService.searchAll(pageable);
        return products.map(ProductListDTO::new);
    }
}
