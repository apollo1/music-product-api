package net.apollo1.musicproducts.productgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductGroupRepository extends JpaRepository<ProductGroupDAO, UUID> {
}
