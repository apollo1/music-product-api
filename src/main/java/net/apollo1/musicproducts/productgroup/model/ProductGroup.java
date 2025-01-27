package net.apollo1.musicproducts.productgroup.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import net.apollo1.musicproducts.productgroup.repository.ProductGroupDAO;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductGroup(UUID id, String title, LocalDate releaseDate) {
    public static ProductGroup from(ProductGroupDAO dao) {
        return ProductGroup.builder()
                .id(dao.getId())
                .title(dao.getTitle())
                .releaseDate(dao.getReleaseDate())
                .build();
    }
}
