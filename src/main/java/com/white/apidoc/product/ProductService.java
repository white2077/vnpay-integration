package com.white.apidoc.product;

import com.white.apidoc.category.Category;
import com.white.apidoc.category.ICategoryRepository;
import com.white.apidoc.core.exception.code.ErrorCode;
import com.white.apidoc.core.exception.custom.CustomException;
import com.white.apidoc.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    public ProductDTO.ProductResponse create(ProductDTO.CreateRequest request) {
        log.info("ProductService.create");
            Product product = MapperUtil.mapObject(request, Product.class);
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
            if (categories.isEmpty()) throw new CustomException(HttpStatus.BAD_REQUEST,
                    ErrorCode.CATEGORY_NOT_FOUND.getCode(),
                    ErrorCode.CATEGORY_NOT_FOUND.getMessage(),
                    null);
            product.setCategories(categories);
            return MapperUtil.mapObject(productRepository.save(product), ProductDTO.ProductResponse.class);
    }

    public ProductDTO.ProductResponse get(String id) {
        log.info("ProductService.get");
        return MapperUtil.mapObject(productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Product not found")), ProductDTO.ProductResponse.class);
    }
    public ProductDTO.ProductResponse update(ProductDTO.UpdateRequest request) {
        log.info("ProductService.update");
        Product product = productRepository.findById(request.id).orElseThrow(() ->
                new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.PRODUCT_NOT_FOUND.getCode(), ErrorCode.PRODUCT_NOT_FOUND.getMessage(),null));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
        product.setCategories(categories);
        return MapperUtil.mapObject(productRepository.save(product), ProductDTO.ProductResponse.class);
    }
    public void delete(String id) {
        log.info("ProductService.delete");
        productRepository.deleteById(id);
    }
    public List<ProductDTO.ProductResponse> getAll() {
        log.info("ProductService.getAll");
        return MapperUtil.mapList(productRepository.findAll(), ProductDTO.ProductResponse.class);
    }
}
