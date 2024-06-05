package io.ecp.testmall.category.controller;

import io.ecp.testmall.category.entity.Category;
import io.ecp.testmall.category.entity.CategoryDTO;
import io.ecp.testmall.category.service.CategoryService;
import io.ecp.testmall.jwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.ecp.testmall.utils.tokenValidUtils.tokenValid;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<Category> createCategoryWithSubCategories(@RequestBody CategoryDTO categoryDTO,
                                                                    @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();

        Category category = categoryService.createCategoryWithSubCategories(categoryDTO);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(@RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();

        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
