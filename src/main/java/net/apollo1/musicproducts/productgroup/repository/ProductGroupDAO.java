package net.apollo1.musicproducts.productgroup.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product_group")
@RequiredArgsConstructor
public class ProductGroupDAO {

    @Id
    @NonNull
    private UUID id;

    @NonNull
    private String title;

    @NonNull
    private LocalDate releaseDate;
}
