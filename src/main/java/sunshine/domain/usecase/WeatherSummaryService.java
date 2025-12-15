package sunshine.domain.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sunshine.domain.City;
import sunshine.domain.WeatherSnapShot;
import sunshine.domain.service.CityService;
import sunshine.infrastructure.http.OpenMeteoClient;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto.CurrentWeather;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto.Hourly;

@Service
@RequiredArgsConstructor
public class WeatherSummaryService {

  private final CityService cityService;
  private final OpenMeteoClient weatherHttpClient;

  public String getWeatherSummary(String cityName) {
    City city = cityService.getCity(cityName);
    OpenMeteoWeatherResponseDto response = getWeatherInfo(city.getLatitude(), city.getLongitude());
    WeatherSnapShot snapshot = extractCurrent(response);
    return generateSummary(snapshot);
  }

  private OpenMeteoWeatherResponseDto getWeatherInfo(double latitude, double longtitude) {
    return weatherHttpClient.getWeatherByLocation(latitude, longtitude, true,
        "celsius", "apparent_temperature,relative_humidity_2m");
  }

  private WeatherSnapShot extractCurrent(OpenMeteoWeatherResponseDto response) {
    CurrentWeather current = response.currentWeather();
    Hourly hourly = response.hourly();

    return WeatherSnapShot.builder()
        .temperature(current.temperature())
        .apparentTemperature(hourly.apparentTemperature().get(0))
        .humidity(hourly.relativeHumidity2m().get(0))
        .weatherCode(current.weathercode())
        .build();
  }

  // TODO 해당 부분 LLM 활용으로 수정
  private String generateSummary(WeatherSnapShot snapshot) {
    //String sky = weatherCodeTranslator.translate(snapshot.getWeatherCode());

    return String.format(
        "현재 날씨는 %d이며, 현재 기온은 %.1f°C, 체감 온도는 %.1f°C, 습도는 %d%%입니다.",
        snapshot.getWeatherCode(), // sky
        snapshot.getTemperature(),
        snapshot.getApparentTemperature(),
        snapshot.getHumidity()
    );
  }

}
