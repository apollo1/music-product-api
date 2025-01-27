package net.apollo1.musicproducts.product.exception;

import java.util.UUID;

public class ProductGroupNotFoundException extends RuntimeException {
    public ProductGroupNotFoundException(UUID productGroupId) {
        super("Product Group ID %s not found".formatted(productGroupId));
    }
}
