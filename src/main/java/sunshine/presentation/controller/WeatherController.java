package sunshine.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.domain.service.WeatherSummaryService;

@RestController
@RequiredArgsConstructor
public class WeatherController {

  private final WeatherSummaryService weatherSummaryService;

  @GetMapping(path = "/v1/weather")
  public String getWeather(@RequestParam("city") String cityName) {
    return weatherSummaryService.getWeatherSummary(cityName);
  }
}
