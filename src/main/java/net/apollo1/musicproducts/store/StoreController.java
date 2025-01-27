package net.apollo1.musicproducts.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.apollo1.musicproducts.product.model.Product;
import net.apollo1.musicproducts.productgroup.model.ProductGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("/stores")
@AllArgsConstructor
public class StoreController {

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductGroup> getStore(@PathVariable("id") UUID id) {

        log.debug("Get store requested for id: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping
    public ResponseEntity<Product> getStores() {

        log.debug("Get all stores");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

    }
}
