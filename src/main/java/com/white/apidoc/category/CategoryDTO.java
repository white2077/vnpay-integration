package com.white.apidoc.category;

import lombok.*;

public abstract class CategoryDTO {
    @Builder
    @Getter
    @Setter
    public static class CreateRequest {
        public String name;
        public String description;
    }
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {
        public String id;
        public String name;
        public String description;
    }
    @Getter
    @Builder
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryResponse {
        public String id;
        public String name;
        public String description;
    }
}
