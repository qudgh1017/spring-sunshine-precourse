package sunshine.presentation.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sunshine.domain.exception.CityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CityNotFoundException.class)
  public ResponseEntity<String> handleCityNotFoundException(CityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
  }

}
