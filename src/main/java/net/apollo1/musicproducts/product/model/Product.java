package net.apollo1.musicproducts.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import net.apollo1.musicproducts.product.repository.ProductDAO;
import net.apollo1.musicproducts.productgroup.model.ProductGroup;
import net.apollo1.musicproducts.store.model.Store;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Product(UUID id, String title, String distribution, String format, String priceGbp, String priceUsd,
                      String priceEur, LocalDate productReleaseDate, Store store, ProductGroup productGroup) {

    public static Product from(ProductDAO dao) {
        return Product.builder()
                .id(dao.getId())
                .title(dao.getTitle())
                .distribution(dao.getDistribution().name())
                .format(dao.getFormat().name())
                .priceGbp(String.format("%.2f", dao.getPriceGbp()))
                .priceUsd(String.format("%.2f", dao.getPriceUsd()))
                .priceEur(String.format("%.2f", dao.getPriceEur()))
                .productReleaseDate(dao.getReleaseDate())
                .store(Store.from(dao.getStore()))
                .productGroup(ProductGroup.from(dao.getProductGroup()))
                .build();
    }
}
