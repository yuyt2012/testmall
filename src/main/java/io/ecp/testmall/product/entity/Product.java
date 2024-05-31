package io.ecp.testmall.product.entity;

import io.ecp.testmall.category.entity.Category;
import io.ecp.testmall.category.entity.CategoryProduct;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private boolean isSoldOut;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CategoryProduct> categoryProducts = new ArrayList<>();
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public void addCategoryProduct(CategoryProduct categoryProduct) {
        categoryProducts.add(categoryProduct);
        categoryProduct.setProduct(this);
    }

    public void removeCategoryProduct(CategoryProduct categoryProduct) {
        categoryProducts.remove(categoryProduct);
        categoryProduct.setProduct(null);
    }

    public String getParentCategoryName() {
        return this.categoryProducts.stream()
                .map(CategoryProduct::getCategory)
                .map(Category::getParent)
                .map(Category::getName)
                .findFirst()
                .orElse(null);
    }

    public List<String> getSubCategoryNames() {
        return this.getCategoryProducts().stream()
                .filter(categoryProduct -> !categoryProduct.getCategory().isTopCategory())
                .map(categoryProduct -> categoryProduct.getCategory().getName())
                .collect(Collectors.toList());
    }

    @PrePersist
    protected void onRegDate() {
        regDate = new Date();
        updateDate = new Date();
    }

    @PreUpdate
    protected void onUpdateDate() {
        updateDate = new Date();
    }
}
