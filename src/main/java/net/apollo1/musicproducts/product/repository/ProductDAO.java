package net.apollo1.musicproducts.product.repository;

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
@Table(name = "product")
@RequiredArgsConstructor
//@ToString(exclude = {"xxx", "yyy"})
//@EqualsAndHashCode(exclude = {"xxx", "yyy"})
public class ProductDAO {

    @Id
    @NonNull
    private UUID id;

    @NonNull
    private String title;
}
