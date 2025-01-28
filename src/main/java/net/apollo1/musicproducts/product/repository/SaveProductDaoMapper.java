package net.apollo1.musicproducts.product.repository;

import lombok.AllArgsConstructor;
import net.apollo1.musicproducts.product.exception.ProductGroupNotFoundException;
import net.apollo1.musicproducts.product.exception.StoreNotFoundException;
import net.apollo1.musicproducts.product.model.Product;
import net.apollo1.musicproducts.productgroup.repository.ProductGroupRepository;
import net.apollo1.musicproducts.store.repository.StoreRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class SaveProductDaoMapper implements Function<Product, ProductDAO> {

    StoreRepository storeRepository;
    ProductGroupRepository productGroupRepository;

    @Override
    public ProductDAO apply(Product product) {
        return ProductDAO.builder()
                .id(UUID.randomUUID())
                .title(product.title())
                .distribution(ProductDAO.Distribution.valueOf(product.distribution()))
                .format(ProductDAO.Format.valueOf(product.format()))
                .priceGbp(Double.parseDouble(product.priceGbp()))
                .priceUsd(Double.parseDouble(product.priceUsd()))
                .priceEur(Double.parseDouble(product.priceEur()))
                .releaseDate(product.productReleaseDate())
                .store(storeRepository.findById(product.store().id()).orElseThrow(() -> new StoreNotFoundException(product.store().id())))
                .productGroup(productGroupRepository.findById(product.productGroup().id()).orElseThrow(() -> new ProductGroupNotFoundException(product.productGroup().id())))
                .build();
    }
}
