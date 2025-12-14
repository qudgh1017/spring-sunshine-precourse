package sunshine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class WeatherSnapShot {
  double temperature;
  double apparentTemperature;
  int humidity;
  int weatherCode;
}
