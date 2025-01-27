package net.apollo1.musicproducts.exception;

import jakarta.annotation.Priority;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.apollo1.musicproducts.product.exception.ProductGroupNotFoundException;
import net.apollo1.musicproducts.product.exception.ProductNotFoundException;
import net.apollo1.musicproducts.product.exception.StoreNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
@Priority(Ordered.LOWEST_PRECEDENCE)
public class ExceptionAdvice {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ExceptionDTO uncaughtException(RuntimeException e) {
        log.error("Uncaught exception encountered", e);
        return new ExceptionDTO(
                ErrorCode.SERVER_ERROR,
                "Internal Server Error",
                "The server encountered an internal error was unable to complete the request."
        );
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ExceptionDTO handleProductNotFoundException(RuntimeException e) {
        return new ExceptionDTO(
                ErrorCode.PRODUCT_NOT_FOUND,
                "Not Found",
                e.getMessage()
        );
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(StoreNotFoundException.class)
    public ExceptionDTO handleStoreNotFoundException(RuntimeException e) {
        return new ExceptionDTO(
                ErrorCode.BAD_REQUEST,
                "Store Not Found",
                e.getMessage()
        );
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ProductGroupNotFoundException.class)
    public ExceptionDTO handleProductGroupNotFoundException(RuntimeException e) {
        return new ExceptionDTO(
                ErrorCode.BAD_REQUEST,
                "Product Group Not Found",
                e.getMessage()
        );
    }

}
