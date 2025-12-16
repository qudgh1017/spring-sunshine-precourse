package sunshine.domain.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sunshine.domain.City;
import sunshine.domain.WeatherSnapShot;
import sunshine.domain.service.CityService;
import sunshine.domain.service.LlmService;
import sunshine.infrastructure.http.OpenMeteoClient;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto.CurrentWeather;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto.Hourly;

@Service
@RequiredArgsConstructor
public class WeatherSummaryService {

  private final LocationResolverService locationResolverService;
  private final OpenMeteoClient weatherHttpClient;
  private final LlmService llmService;

  public String getWeatherSummary(String cityName) {
    // Location Resolver
    City city = locationResolverService.locationResolver(cityName);
    // 날씨 정보
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

  private String generateSummary(WeatherSnapShot snapshot) {
    // function call 로 sky
    String sky = llmService.translateSky(snapshot.getWeatherCode());

    // 현재 날씨 요약 + 복장 추천은 format 어느정도 줘서 하기
    String clothes = llmService.suggestClothes(snapshot);

    return String.format(
        "현재 날씨는 %s이며, 현재 기온은 %.1f°C, 체감 온도는 %.1f°C, 습도는 %d%%입니다.\n 오늘 날씨에 따라 옷차림은 \n%s\n로 추천드립니다.",
        sky,
        snapshot.getTemperature(),
        snapshot.getApparentTemperature(),
        snapshot.getHumidity(),
        clothes
    );
  }

}
