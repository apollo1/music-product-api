package net.apollo1.musicproducts.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<StoreDAO, UUID> {
}
