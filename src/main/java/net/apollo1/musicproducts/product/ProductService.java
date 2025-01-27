package net.apollo1.musicproducts.product;

import lombok.AllArgsConstructor;
import net.apollo1.musicproducts.product.exception.ProductNotFoundException;
import net.apollo1.musicproducts.product.model.Product;
import net.apollo1.musicproducts.product.repository.ProductDAO;
import net.apollo1.musicproducts.product.repository.ProductDaoMapper;
import net.apollo1.musicproducts.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;
    ProductDaoMapper productDaoMapper;

    public Product getProduct(UUID id) {
        return productRepository.findById(id)
                .map(Product::from)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> getProducts() {
        return productRepository.findAll()
                .stream().map(Product::from)
                .toList();
    }

    public Product saveProduct(Product product) {
        ProductDAO productDAO = productDaoMapper.apply(product);
        return Product.from(productRepository.save(productDAO));
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

}
