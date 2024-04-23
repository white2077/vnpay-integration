package com.white.apidoc.core.exception.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_PARAMETER("E001", "Invalid Parameter"),
    NOT_FOUND("E002", "Not Found"),
    INTERNAL_SERVER_ERROR("E003", "Internal Server Error"),
    DUPLICATE_CATEGORY("E004", "Duplicate Category"),
    CATEGORY_NOT_FOUND("E005", "Category not found"),
    PRODUCT_NOT_FOUND("E006", "Product not found"),
    INVALID_REQUEST_BODY("E007", "Invalid Request Body"),
    CREATE_PAYMENT_FAILED("E008", "Create payment failed");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
