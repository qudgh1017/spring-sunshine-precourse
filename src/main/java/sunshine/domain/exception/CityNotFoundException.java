package sunshine.domain.exception;

public class CityNotFoundException extends RuntimeException {
  public CityNotFoundException(String cityName) {
    super("지원하지 않는 도시입니다: " + cityName);
  }
}
