package io.ecp.testmall.category.controller;

import io.ecp.testmall.category.entity.Category;
import io.ecp.testmall.category.entity.CategoryDTO;
import io.ecp.testmall.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/category/{parentId}")
    public ResponseEntity<Category> createSubCategory(@PathVariable Long parentId, @RequestBody CategoryDTO categoryDTO) {
        Category subCategory = categoryService.createSubCategory(parentId, categoryDTO);
        return ResponseEntity.ok(subCategory);
    }
}
