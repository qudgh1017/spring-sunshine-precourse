package sunshine.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sunshine.domain.City;
import sunshine.domain.exception.CityNotFoundException;
import sunshine.domain.infra.CityInfra;

@Service
@RequiredArgsConstructor
public class WeatherSummaryService {

  private final CityInfra cityInfra;

  private City getCity(String cityName) {
    return cityInfra.findByName(cityName)
            .orElseThrow(() -> new CityNotFoundException(cityName));
  }

}
