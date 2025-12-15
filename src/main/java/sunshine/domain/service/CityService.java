package sunshine.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sunshine.domain.City;
import sunshine.domain.exception.CityNotFoundException;
import sunshine.domain.infra.CityInfra;

@Service
@RequiredArgsConstructor
public class CityService {

  private final CityInfra cityInfra;

  @Transactional(readOnly = true)
  public City getCity(String cityName) {
    return cityInfra.findByName(cityName)
        .orElseThrow(() -> new CityNotFoundException(cityName));
  }
}
