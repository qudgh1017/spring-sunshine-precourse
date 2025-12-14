package sunshine.infrastructure.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto;

@FeignClient(name = "open-meteo")
public interface OpenMeteoClient {

  @GetMapping("/v1/forecast")
  OpenMeteoWeatherResponseDto getWeatherByLocation(
      @RequestParam("latitude") double latitude,
      @RequestParam("longitude") double longitude,
      @RequestParam("current_weather") boolean currentWeather,
      @RequestParam("temperature_unit") String temperatureUnit,
      @RequestParam("hourly") String hourly
  );
}
