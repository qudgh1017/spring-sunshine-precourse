package sunshine.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sunshine.domain.City;

@Entity
@Table(name = "city")
@NoArgsConstructor
@AllArgsConstructor
public class CityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private double latitude;
  private double longitude;

  public City toDomain() {
    return new City(name, latitude, longitude);
  }
}
