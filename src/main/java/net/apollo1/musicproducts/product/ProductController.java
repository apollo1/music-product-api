package net.apollo1.musicproducts.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.apollo1.musicproducts.product.model.Product;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
