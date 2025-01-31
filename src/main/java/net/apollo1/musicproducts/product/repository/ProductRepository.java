package net.apollo1.musicproducts.product.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductDAO, UUID>, JpaSpecificationExecutor<ProductDAO> {

    default List<ProductDAO> getFilteredProducts(String storeName, String productGroupTitle, LocalDate productGroupReleaseDateFrom, LocalDate productGroupReleaseDateTo, String productTitle, LocalDate productReleaseDateFrom, LocalDate productReleaseDateTo) {
        Specification<ProductDAO> spec = Specification.where(null);

        if (hasValue(storeName)) {
            spec = spec.and(ProductSpecification.hasStore(storeName));
        }

        if (hasValue(productGroupTitle)) {
            spec = spec.and(ProductSpecification.hasGroup(productGroupTitle));
        }

        if (hasValue(productGroupReleaseDateFrom) && hasValue(productGroupReleaseDateTo)) {
            spec = spec.and(ProductSpecification.productGroupReleaseDateBetween(productGroupReleaseDateFrom, productGroupReleaseDateTo));
        }

        if (hasValue(productTitle)) {
            spec = spec.and(ProductSpecification.hasTitle(productTitle));
        }

        if (hasValue(productReleaseDateFrom) && hasValue(productReleaseDateTo)) {
            spec = spec.and(ProductSpecification.productReleaseDateBetween(productReleaseDateFrom, productReleaseDateTo));
        }

        return findAll(spec);
    }

    private static boolean hasValue(String filterName) {
        return Optional.ofNullable(filterName).map(String::isEmpty).isPresent();
    }

    private static boolean hasValue(LocalDate filterName) {
        return Optional.ofNullable(filterName).isPresent();
    }
}
