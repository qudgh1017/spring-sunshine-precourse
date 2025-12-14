package sunshine.infrastructure.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OpenMeteoWeatherResponseDto(
    @JsonProperty("current_weather")
    CurrentWeather currentWeather,

    Hourly hourly
) {

    public record CurrentWeather(
        double temperature,
        int weathercode
    ) {}

    public record Hourly(
        @JsonProperty("apparent_temperature")
        List<Double> apparentTemperature,

        @JsonProperty("relative_humidity_2m")
        List<Integer> relativeHumidity2m
    ) {}
}
