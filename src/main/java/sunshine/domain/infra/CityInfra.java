package sunshine.domain.infra;

import java.util.List;
import java.util.Optional;
import sunshine.domain.City;

public interface CityInfra {

  Optional<City> findByName(String name);
  List<City> findAll();
}
