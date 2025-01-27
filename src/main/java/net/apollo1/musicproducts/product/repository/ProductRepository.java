package net.apollo1.musicproducts.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductDAO, UUID> {
}
