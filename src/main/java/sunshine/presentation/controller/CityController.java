package sunshine.presentation.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sunshine.domain.City;
import sunshine.domain.infra.CityInfra;

@RestController
@RequiredArgsConstructor
public class CityController {

  public final CityInfra cityInfra;

  @GetMapping(path = "/v1/cities")
  public List<String> getCities() {
    return cityInfra.findAll()
            .stream()
            .map(City::getName)
            .toList();
  }

}
