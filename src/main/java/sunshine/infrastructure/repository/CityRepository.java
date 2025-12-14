package sunshine.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sunshine.domain.City;
import sunshine.domain.infra.CityInfra;
import sunshine.infrastructure.entity.CityEntity;

@Repository
@RequiredArgsConstructor
public class CityRepository implements CityInfra {

  private final CityJpaRepository repositry;

  @Override
  public Optional<City> findByName(String name) {
    return repositry.findByName(name)
            .map(CityEntity::toDomain);
  }

  @Override
  public List<City> findAll() {
    return repositry.findAll()
            .stream()
            .map(CityEntity::toDomain)
            .toList();
  }
}
