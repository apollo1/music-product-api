package net.apollo1.musicproducts.product.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.apollo1.musicproducts.productgroup.repository.ProductGroupDAO;
import net.apollo1.musicproducts.store.repository.StoreDAO;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
@RequiredArgsConstructor
public class ProductDAO {

    @Id
    @NonNull
    private UUID id;

    @NonNull
    private String title;

    @Enumerated(EnumType.STRING)
    private Distribution distribution;

    @Enumerated(EnumType.STRING)
    private Format format;

    private double priceGbp;
    private double priceUsd;
    private double priceEur;

    @NonNull
    private LocalDate releaseDate;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @NonNull
    @JoinColumn(name = "store_id", referencedColumnName = "id", updatable = true, insertable = true)
    private StoreDAO store;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "product_group_id", referencedColumnName = "id", updatable = true, insertable = true)
    private ProductGroupDAO productGroup;

    public enum Distribution {
        PHYSICAL, DIGITAL;
    }

    public enum Format {
        VINYL, CD, CASSETTE, MP3, WAV, WAV24
    }
}
