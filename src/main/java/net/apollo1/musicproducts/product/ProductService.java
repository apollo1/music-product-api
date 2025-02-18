package net.apollo1.musicproducts.product;

import lombok.AllArgsConstructor;
import net.apollo1.musicproducts.product.exception.ProductNotFoundException;
import net.apollo1.musicproducts.product.model.Product;
import net.apollo1.musicproducts.product.repository.ProductDAO;
import net.apollo1.musicproducts.product.repository.ProductRepository;
import net.apollo1.musicproducts.product.repository.SaveProductDaoMapper;
import net.apollo1.musicproducts.product.repository.UpdateProductDaoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;
    SaveProductDaoMapper saveProductDaoMapper;
    UpdateProductDaoMapper updateProductDaoMapper;

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
        ProductDAO productDAO = saveProductDaoMapper.apply(product);
        return Product.from(productRepository.save(productDAO));
    }

    public Product updateProduct(UUID id, Product product) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        ProductDAO productDAO = updateProductDaoMapper.apply(product);
        return Product.from(productRepository.save(productDAO));
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public List<Product> getFilteredProducts(String storeName, String productGroupTitle, LocalDate productGroupReleaseDateFrom, LocalDate productGroupReleaseDateTo, String productTitle, LocalDate productReleaseDateFrom, LocalDate productReleaseDateTo) {
        return productRepository.getFilteredProducts(storeName, productGroupTitle, productGroupReleaseDateFrom, productGroupReleaseDateTo, productTitle, productReleaseDateFrom, productReleaseDateTo)
                .stream().map(Product::from)
                .toList();
    }

}
