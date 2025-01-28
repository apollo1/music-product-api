package net.apollo1.musicproducts.product.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductDAO, UUID>, JpaSpecificationExecutor<ProductDAO> {

    default List<ProductDAO> getFilteredProducts(String storeName, String productGroupTitle, LocalDate productGroupReleaseDateFrom, LocalDate productGroupReleaseDateTo, String productTitle, LocalDate productReleaseDateFrom, LocalDate productReleaseDateTo) {
        Specification<ProductDAO> spec = Specification.where(null);

        if (storeName != null && !storeName.isEmpty()) {
            spec = spec.and(ProductSpecification.hasStore(storeName));
        }

        if (productGroupTitle != null && !productGroupTitle.isEmpty()) {
            spec = spec.and(ProductSpecification.hasGroup(productGroupTitle));
        }

        if (productGroupReleaseDateFrom != null && productGroupReleaseDateTo != null) {
            spec = spec.and(ProductSpecification.productGroupReleaseDateBetween(productGroupReleaseDateFrom, productGroupReleaseDateTo));
        }

        if (productTitle != null && !productTitle.isEmpty()) {
            spec = spec.and(ProductSpecification.hasTitle(productTitle));
        }

        if (productReleaseDateFrom != null && productReleaseDateTo != null) {
            spec = spec.and(ProductSpecification.productReleaseDateBetween(productReleaseDateFrom, productReleaseDateTo));
        }

        return findAll(spec);
    }
}
