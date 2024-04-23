package com.white.apidoc.category;

import com.white.apidoc.core.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${spring.application.api-prefix}/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseObject<List<CategoryDTO.CategoryResponse>> getAll() {
        return new ResponseObject<>(HttpStatus.OK, "Fetch all category completed",categoryService.getCategories());
    }
    @PostMapping("/add")
    public ResponseObject<CategoryDTO.CategoryResponse> add(@RequestBody CategoryDTO.CreateRequest createRequest) {
        return new ResponseObject<>(HttpStatus.CREATED, "Add category completed", categoryService.addCategory(createRequest));
    }
    @PutMapping("/update")
    public ResponseObject<CategoryDTO.CategoryResponse> update(@RequestBody CategoryDTO.UpdateRequest updateRequest) {
        return new ResponseObject<>(HttpStatus.ACCEPTED, "Update category completed", categoryService.updateCategory(updateRequest));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseObject<ResponseObject.Payload<?>> delete(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return new ResponseObject<>(HttpStatus.OK, "Delete category completed", null);
    }
}
