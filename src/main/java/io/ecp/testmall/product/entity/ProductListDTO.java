package io.ecp.testmall.product.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductListDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private int price;
    @NotEmpty
    private int stockQuantity;
    private boolean isSoldOut;
    private String parentCategoryName;
    private List<String> subCategoryNames;
    @NotEmpty
    private String description;
    private Date regDate;
    private Date updateDate;

    public ProductListDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.isSoldOut = product.isSoldOut();
        this.parentCategoryName = product.getParentCategoryName();
        this.subCategoryNames = product.getSubCategoryNames();
        this.description = product.getDescription();
        this.regDate = product.getRegDate();
        this.updateDate = product.getUpdateDate();
    }
}