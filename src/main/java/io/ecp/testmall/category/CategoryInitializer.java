package io.ecp.testmall.category;

import io.ecp.testmall.category.entity.ProductCategory;
import io.ecp.testmall.category.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryInitializer implements ApplicationRunner {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ProductCategory skincare = new ProductCategory();
        skincare.setName("스킨케어");

        ProductCategory makeup = new ProductCategory();
        makeup.setName("메이크업");

        ProductCategory perfume = new ProductCategory();
        perfume.setName("향수");

        // 상위 카테고리 저장
        productCategoryRepository.save(skincare);
        productCategoryRepository.save(makeup);
        productCategoryRepository.save(perfume);

        // 하위 카테고리 생성
        ProductCategory skin = new ProductCategory();
        skin.setName("스킨");
        skin.setParent(skincare);

        ProductCategory lotion = new ProductCategory();
        lotion.setName("로션");
        lotion.setParent(skincare);

        // 하위 카테고리 저장
        productCategoryRepository.save(skin);
        productCategoryRepository.save(lotion);

    }
}
