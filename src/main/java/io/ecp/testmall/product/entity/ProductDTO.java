package io.ecp.testmall.product.entity;

import io.ecp.testmall.category.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private int price;
    @NotEmpty
    private int stockQuantity;
    @NotEmpty
    private String imageUrl;
    @NotEmpty
    private String parentCategory;
    @NotEmpty
    private String childCategory;
    @NotEmpty
    private String description;
}
