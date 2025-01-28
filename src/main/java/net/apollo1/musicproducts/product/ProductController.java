package net.apollo1.musicproducts.product;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.apollo1.musicproducts.product.model.Product;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(path = "/{id}")
    public Product getProduct(@PathVariable("id") UUID id) {
        log.debug("Get product requested for id: {}", id);
        return productService.getProduct(id);
    }

    @GetMapping
    public List<Product> getProducts() {
        log.debug("Get all products");
        return productService.getProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody @Valid Product product) {
        log.debug("Create new product for {}", product.title());
        return productService.saveProduct(product);
    }

//    @PutMapping(path = "/{id}")
//    public Product updateProduct(@PathVariable("id") UUID id) {
//
//        log.debug("Update product for id: {}", id);
//        return productService.updateProduct(id);
//    }

    @DeleteMapping(path = "/{id}")
    public void deleteProduct(@PathVariable("id") UUID id) {
        log.debug("Delete product for id: {}", id);
        productService.deleteProduct(id);
    }

    @GetMapping("/filter")
    public List<Product> filterProducts(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String productGroupTitle,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate productGroupReleaseDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate productGroupReleaseDateTo,
            @RequestParam(required = false) String productTitle,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate productReleaseDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate productReleaseDateTo
    ) {
        log.debug("Filter products given criteria");
        return productService.getFilteredProducts(storeName, productGroupTitle, productGroupReleaseDateFrom, productGroupReleaseDateTo, productTitle, productReleaseDateFrom, productReleaseDateTo);
    }
}
