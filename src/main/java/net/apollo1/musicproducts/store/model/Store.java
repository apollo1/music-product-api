package net.apollo1.musicproducts.store.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import net.apollo1.musicproducts.store.repository.StoreDAO;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Store(UUID id, String name) {
    public static Store from(StoreDAO dao) {
        return Store.builder()
                .id(dao.getId())
                .name(dao.getName())
                .build();
    }
}
