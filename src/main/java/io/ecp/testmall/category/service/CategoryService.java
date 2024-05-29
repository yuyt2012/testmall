package io.ecp.testmall.category.service;

import io.ecp.testmall.category.entity.Category;
import io.ecp.testmall.category.entity.CategoryDTO;
import io.ecp.testmall.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = false)
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = false)
    public Category createSubCategory(Long parentId, CategoryDTO categoryDTO) {
        Category parent = categoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Category subCategory = Category.builder()
                .name(categoryDTO.getName())
                .build();
        parent.addSubCategory(subCategory);
        categoryRepository.save(parent);
        return subCategory;
    }
}
