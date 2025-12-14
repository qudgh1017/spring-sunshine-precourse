package sunshine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class City {

  private final String name;
  private final double latitude;
  private final double longitude;
}
