package com.white.apidoc.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.white.apidoc.category.CategoryDTO;
import jakarta.persistence.Column;
import lombok.*;

import java.util.List;
import java.util.Set;

public abstract class ProductDTO {

    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CreateRequest {
        private String name;
        private String description;
        private double price;
        private int stock;
        public Set<String> categoryIds;

    }
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {
        public String id;
        public String name;
        public String description;
        public double price;
        public int stock;
        public Set<String> categoryIds;
    }
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductResponse {
        public String id;
        public String name;
        public double price;
        public int stock;
        public String description;
        public List<CategoryDTO.CategoryResponse> categories;
    }
}
