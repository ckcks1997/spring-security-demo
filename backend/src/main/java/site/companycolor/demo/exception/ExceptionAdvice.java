package site.companycolor.demo.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import site.companycolor.demo.dto.ApiResponse;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ApiResponse<?>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        log.error("Method Not Allowed", ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.error("Method Not Allowed: " + ex.getMessage()));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiResponse<?>> badRequestException(RuntimeException runtimeException) {
        log.error("INTERNAL_SERVER_ERROR", runtimeException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(runtimeException.getMessage()));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<ApiResponse<?>> forbiddenException(Exception exception) {
        log.error("FORBIDDEN", exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {
            BadCredentialsException.class,
            UsernameNotFoundException.class
    })
    protected ResponseEntity<ApiResponse<?>> unauthorizedException(Exception exception) {
        log.error("UNAUTHORIZED", exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ApiResponse<?>> notFoundException(Exception exception) {
        log.error("NOT_FOUND", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {
            Exception.class
    })
    protected ResponseEntity<ApiResponse<?>> internalServerErrorException(Exception exception) {
        log.error("INTERNAL_SERVER_ERROR: "+ exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(exception.getMessage()));
    }

}