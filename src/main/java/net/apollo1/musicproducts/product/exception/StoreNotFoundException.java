package net.apollo1.musicproducts.product.exception;

import java.util.UUID;

public class StoreNotFoundException extends RuntimeException {
    public StoreNotFoundException(UUID storeId) {
        super("Store ID %s not found".formatted(storeId));
    }
}
