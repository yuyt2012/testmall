package io.ecp.testmall.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.product.entity.Product;
import io.ecp.testmall.product.entity.ProductDTO;
import io.ecp.testmall.product.entity.ProductListDTO;
import io.ecp.testmall.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/save")
    public ResponseEntity<?> saveProduct(@RequestPart("productDTO") MultipartFile productDTOStr,
                                         @RequestPart("image") MultipartFile imageFile,
                                         @RequestHeader("Authorization") String token) {
        String t = JwtUtils.extractToken(token);
        if (!JwtUtils.validateToken(t)) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productDTO = objectMapper.readValue(productDTOStr.getInputStream(), ProductDTO.class);

            String uploadDir = "C:/Users/Public/Documents/study/testmall/src/uploaded-images/";
            String originalFilename = imageFile.getOriginalFilename();
            String filePath = uploadDir + originalFilename;
            imageFile.transferTo(new File(filePath));
            productDTO.setImageUrl(filePath);
            productService.saveProduct(productDTO);
            return ResponseEntity.ok().body("success");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
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

    @GetMapping("/product/findall")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(productService.findAll());
    }
}
