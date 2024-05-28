package io.ecp.testmall.product.service;

import io.ecp.testmall.product.entity.Product;
import io.ecp.testmall.product.entity.ProductDTO;
import io.ecp.testmall.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Product saveProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .stockQuantity(productDTO.getStockQuantity())
                .category(productDTO.getCategory())
                .build();
        return productRepository.save(product);
    }
}
