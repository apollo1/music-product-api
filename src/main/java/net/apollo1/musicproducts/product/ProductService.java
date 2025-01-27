package net.apollo1.musicproducts.product;

import lombok.AllArgsConstructor;
import net.apollo1.musicproducts.product.exception.ProductNotFoundException;
import net.apollo1.musicproducts.product.model.Product;
import net.apollo1.musicproducts.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;

    public Product getProduct(UUID id) {
        return productRepository.findById(id)
                .map(Product::from)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
