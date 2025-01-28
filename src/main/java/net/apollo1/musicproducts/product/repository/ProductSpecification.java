package net.apollo1.musicproducts.product.repository;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProductSpecification {

    private ProductSpecification() {
        // Private constructor to prevent instantiation
    }

    public static Specification<ProductDAO> hasStore(String storeName) {
        return (root, query, cb) -> cb.equal(root.get("store").get("name"), storeName);
    }

    public static Specification<ProductDAO> hasGroup(String groupTitle) {
        return (root, query, cb) -> cb.equal(root.get("productGroup").get("title"), groupTitle);
    }

    public static Specification<ProductDAO> productGroupReleaseDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> cb.between(root.get("productGroup").get("releaseDate"), start, end);
    }

    public static Specification<ProductDAO> hasTitle(String title) {
        return (root, query, cb) -> cb.equal(root.get("title"), title);
    }

    public static Specification<ProductDAO> productReleaseDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> cb.between(root.get("releaseDate"), start, end);
    }
}
