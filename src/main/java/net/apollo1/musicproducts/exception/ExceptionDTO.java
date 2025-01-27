package net.apollo1.musicproducts.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExceptionDTO(
        @JsonProperty("error_code")
        ErrorCode errorCode,
        String title,
        String message) {
}
