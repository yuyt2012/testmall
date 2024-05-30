package io.ecp.testmall.product.controller;

import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.product.entity.ProductDTO;
import io.ecp.testmall.product.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
}
