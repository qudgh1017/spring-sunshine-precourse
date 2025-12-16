package sunshine.domain.configuration;

import java.util.function.Function;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class FunctionConfiguration {

  @Tool(description = "weatherCode 기준으로 하늘 상태(맑음, 흐림 등)으로 translate")
  @Bean
  public Function<TranslateRequest, DataResponse> weatherCodeTranslateToSky() {
    return request -> {
      int weatherCode = request.weatherCode();
      log.info("weatherCodeTranslateToSky - weatherCode: {}", weatherCode);

      var result = "매우 흐림";
      if (weatherCode == 0) {
        result = "맑음";
      }
      else if (weatherCode == 1) {
        result = "대체로 맑음";
      }
      else if (weatherCode == 2) {
        result = "구름 조금";
      }
      else if (weatherCode == 3) {
        result = "흐림";
      }
      else if (weatherCode >= 45 && weatherCode <= 48) {
        result = "안개";
      }
      else if (weatherCode >= 51 && weatherCode <= 67) {
        result = "이슬비/비";
      }
      else if (weatherCode >= 71 && weatherCode <= 77) {
        result = "눈";
      }
      else if (weatherCode >= 80 && weatherCode <= 99) {
        result = "소나기/천둥";
      }

      log.info("weatherCodeTranslateToSky - sky: {}", result);
      return new DataResponse(result);
    };
  }
}

