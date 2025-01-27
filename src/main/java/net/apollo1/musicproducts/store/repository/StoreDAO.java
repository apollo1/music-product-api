package net.apollo1.musicproducts.store.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "store")
@RequiredArgsConstructor
public class StoreDAO {

    @Id
    @NonNull
    private UUID id;

    @NonNull
    private String name;
}
