package net.apollo1.musicproducts.product;

import lombok.AllArgsConstructor;
import net.apollo1.musicproducts.product.exception.ProductNotFoundException;
import net.apollo1.musicproducts.product.model.Product;
import net.apollo1.musicproducts.product.repository.ProductDAO;
import net.apollo1.musicproducts.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;

    public Product getProduct(UUID id) {

        ProductDAO productDAO = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return Product.builder()
                .id(productDAO.getId())
                .title(productDAO.getTitle())
                .build();
    }
}
