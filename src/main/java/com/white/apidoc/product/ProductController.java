package com.white.apidoc.product;

import com.white.apidoc.core.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${spring.application.api-prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseObject<List<ProductDTO.ProductResponse>> getAllProducts() {
        return new ResponseObject<>(HttpStatus.OK, "Success", productService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseObject<ProductDTO.ProductResponse> getProduct(@PathVariable String id) {
        return new ResponseObject<>(HttpStatus.OK, "Success", productService.get(id));
    }
    @PutMapping("/update")
    public ResponseObject<ProductDTO.ProductResponse> updateProduct(@RequestBody ProductDTO.UpdateRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", productService.update(request));
    }
    @PostMapping("/add")
    public ResponseObject<ProductDTO.ProductResponse> addProduct(@Valid @RequestBody ProductDTO.CreateRequest request) {
        return new ResponseObject<>(HttpStatus.CREATED, "Success", productService.create(request));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseObject<ResponseObject.Payload<?>> deleteProduct(@PathVariable String id) {
        productService.delete(id);
        return new ResponseObject<>(HttpStatus.OK, "Success", null);
    }
}
