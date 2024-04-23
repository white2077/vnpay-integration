package com.white.apidoc.category;

import com.white.apidoc.core.exception.code.ErrorCode;
import com.white.apidoc.core.exception.custom.CustomException;
import com.white.apidoc.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final ICategoryRepository categoryRepository;
    public CategoryDTO.CategoryResponse addCategory(CategoryDTO.CreateRequest category) {
        log.info("CategoryService.addCategory");
        return MapperUtil
                .mapObject(
                        categoryRepository.save(
                        MapperUtil.mapObject(category, Category.class)),
                        CategoryDTO.CategoryResponse.class
                );
    }

    public CategoryDTO.CategoryResponse getCategory(String id) {
        log.info("CategoryService.getCategory");
        return MapperUtil
                .mapObject(
                        categoryRepository.findById(id).orElseThrow(() ->
                                new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.getCode(), ErrorCode.NOT_FOUND.getMessage(), null)),
                        CategoryDTO.CategoryResponse.class
                );
    }

    public void deleteCategory(String id) {
        log.info("CategoryService.deleteCategory");
        categoryRepository.deleteById(id);
    }
    public CategoryDTO.CategoryResponse updateCategory(CategoryDTO.UpdateRequest category) {
        log.info("CategoryService.updateCategory");
        Category categoryToUpdate = categoryRepository.findById(category.getId()).orElseThrow(() ->
              new CustomException(HttpStatus.NOT_FOUND, ErrorCode.CATEGORY_NOT_FOUND.getCode(), ErrorCode.CATEGORY_NOT_FOUND.getMessage(), null));
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());
        categoryToUpdate.setUpdatedAt(LocalDateTime.now());
        return MapperUtil
                .mapObject(
                        categoryRepository.save(categoryToUpdate),
                        CategoryDTO.CategoryResponse.class
                );
    }

    public List<CategoryDTO.CategoryResponse> getCategories() {
        log.info("CategoryService.getCategories");
        return MapperUtil.mapList(categoryRepository.findAll(), CategoryDTO.CategoryResponse.class);
    }
}
