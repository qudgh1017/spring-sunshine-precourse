package sunshine.domain.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sunshine.domain.City;
import sunshine.domain.exception.CityNotFoundException;
import sunshine.domain.service.CityService;
import sunshine.domain.service.LlmService;

@Service
@RequiredArgsConstructor
@Log4j2
public class LocationResolverService {

  private final CityService cityService;
  private final LlmService llmService;

  public City locationResolver(String cityName) {

    try {
      return cityService.getCity(cityName);
    } catch (CityNotFoundException ce) {
      log.info(">>> City Not Found로 LLM에 조회 요청");
      return llmService.getLocation(cityName);
    }
  }
}
