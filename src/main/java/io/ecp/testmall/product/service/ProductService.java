package io.ecp.testmall.product.service;

import io.ecp.testmall.category.entity.Category;
import io.ecp.testmall.category.entity.CategoryProduct;
import io.ecp.testmall.category.repository.CategoryRepository;
import io.ecp.testmall.product.entity.Product;
import io.ecp.testmall.product.entity.ProductDTO;
import io.ecp.testmall.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> searchAll(Pageable pageable) {
            return productRepository.searchAll(pageable);
    }

    @Transactional(readOnly = false)
    public void saveProduct(ProductDTO productDTO) {
        Category parentCategory = categoryRepository.findByName(productDTO.getParentCategory())
                .orElseThrow(() -> new IllegalArgumentException("Invalid parent category name"));

        Category childCategory = parentCategory.getSubCategories().stream()
                .filter(category -> category.getName().equals(productDTO.getChildCategory()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid child category name"));

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .description(productDTO.getDescription())
                .imageUrl(productDTO.getImageUrl())
                .build();
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setCategory(childCategory);
        categoryProduct.setProduct(product);

        product.addCategoryProduct(categoryProduct);
        productRepository.save(product);
    }
}
