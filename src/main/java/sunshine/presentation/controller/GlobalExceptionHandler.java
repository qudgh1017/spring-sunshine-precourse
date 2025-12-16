package sunshine.presentation.controller;


import com.google.genai.errors.ClientException;
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

  @ExceptionHandler(ClientException.class)
  public ResponseEntity<String> handleClientException(ClientException e) {
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
        .body("오늘 요청 한도를 초과했습니다.\n" + e.getMessage());
  }

}
