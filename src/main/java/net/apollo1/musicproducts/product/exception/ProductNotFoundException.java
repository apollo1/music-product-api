package net.apollo1.musicproducts.product.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID productId) {
        super("Product ID %s not found".formatted(productId));
    }
}
