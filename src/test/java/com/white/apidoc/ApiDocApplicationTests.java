package com.white.apidoc;

import com.white.apidoc.category.ICategoryRepository;
import com.white.apidoc.product.IProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ApiDocApplicationTests {
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Test
    void contextLoads() {
        iProductRepository.findAll().forEach(System.out::println);
        iCategoryRepository.findAll().forEach(System.out::println);
    }

    @Test
    void mapperTest() {
    }
}
