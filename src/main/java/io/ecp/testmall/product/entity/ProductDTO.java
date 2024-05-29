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
    private Category category;
    @NotEmpty
    private String description;
    private Date regDate;
    private Date updateDate;
}
