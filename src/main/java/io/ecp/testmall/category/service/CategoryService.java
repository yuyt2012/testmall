package io.ecp.testmall.category.service;

import io.ecp.testmall.category.entity.Category;
import io.ecp.testmall.category.entity.CategoryDTO;
import io.ecp.testmall.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = false)
    public Category createCategoryWithSubCategories(CategoryDTO categoryDTO) {
        // 부모 카테고리를 데이터베이스에서 검색합니다.
        Category parent = categoryRepository.findByName(categoryDTO.getName())
                .orElse(null);

        // 만약 부모 카테고리가 존재하지 않는다면, 새로운 부모 카테고리를 생성합니다.
        if (parent == null) {
            parent = Category.builder()
                    .name(categoryDTO.getName())
                    .build();
            categoryRepository.save(parent);
        }

        // 자식 카테고리를 생성하고, 이를 부모 카테고리의 하위 카테고리로 추가합니다.
        Category subCategory = Category.builder()
                .name(categoryDTO.getSubCategory())
                .build();

        parent.addSubCategory(subCategory);

        // 부모 카테고리를 데이터베이스에 저장합니다.
        return categoryRepository.save(parent);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll().stream()
                .filter(Category::isTopCategory)
                .collect(Collectors.toList());
    }
}
