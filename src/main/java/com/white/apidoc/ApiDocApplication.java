package com.white.apidoc;

import com.white.apidoc.category.CategoryDTO;
import com.white.apidoc.category.CategoryService;
import com.white.apidoc.category.ICategoryRepository;
import com.white.apidoc.product.IProductRepository;
import com.white.apidoc.product.Product;
import com.white.apidoc.product.ProductDTO;
import com.white.apidoc.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
public class ApiDocApplication implements CommandLineRunner {
    private final CategoryService categoryService;
    private final ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(ApiDocApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        categoryService.addCategory(CategoryDTO.CreateRequest.builder().name("Category 1").description("Description 1").build());
        categoryService.addCategory(CategoryDTO.CreateRequest.builder().name("Category 2").description("Description 2").build());
        categoryService.addCategory(CategoryDTO.CreateRequest.builder().name("Category 3").description("Description 3").build());

        productService.create(ProductDTO.CreateRequest
                .builder()
                .name("Product 1")
                .description("Description 1")
                .price(1000)
                .categoryIds(categoryService.getCategories().stream().map(CategoryDTO.CategoryResponse::getId).collect(Collectors.toSet()))
                .build());
    }
}
