package sunshine.infrastructure.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sunshine.infrastructure.entity.CityEntity;

public interface CityJpaRepository extends JpaRepository<CityEntity, Long> {

  Optional<CityEntity> findByName(String name);
}
