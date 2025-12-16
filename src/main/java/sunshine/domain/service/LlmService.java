package sunshine.domain.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers.Snapshot;
import sunshine.domain.City;
import sunshine.domain.WeatherSnapShot;

@Service
@RequiredArgsConstructor
@Log4j2
public class LlmService {

  private final ChatClient.Builder chatClientBuilder;

  public City getLocation(String cityName) {
    log.info("getLocation - cityName: " + cityName);

    var beanOuputConverter = new BeanOutputConverter<>(City.class);
    var format = beanOuputConverter.getFormat();
    var userMessage = """
      {cityName} 구역의 대표적인 장소 한 군데의 위도, 경도 알려줘
      {format}
      """;
    var template = new PromptTemplate(userMessage);
    var prompt = template.create(Map.of("cityName", cityName, "format", format));

    var text =  chatClientBuilder
        .build()
        .prompt(prompt)
        .call()
        .content();

    log.info(text);

    return beanOuputConverter.convert(text);
  }

  public String translateSky(int weatherCode) {

    log.info("translateSky - weatherCode: " + weatherCode);

    var text = "맑음";
    try {
      var template = new PromptTemplate("weatherCode = {weatherCode} 기준으로 하늘 상태(맑음, 흐림 등)을 알려줘. weatherCode를 Sky로 변환할 때는 translate tool을 사용해서 String 값인 {sky}만 반환해줘");
      var prompt = template.render(Map.of("weatherCode", weatherCode));
      text = chatClientBuilder
          .build()
          .prompt(prompt)
          .toolNames("weatherCodeTranslateToSky")
          .call()
          .content();

    } catch (Exception e) {
      log.info("translateSky - Exception: " + e.getMessage());
    }

    log.info(text);
    return text;
  }

  public String suggestClothes(WeatherSnapShot weatherSnapShot) {
    double temperature = weatherSnapShot.getTemperature();
    double apparentTemperature = weatherSnapShot.getApparentTemperature();

    var template = new PromptTemplate("온도 = {temperature}, 체감온도 = {apparentTemperature} 기준으로 옷차림을 추천해줘 대신에 예제처럼 명사형으로 반환해줘 (ex) 코트, 반팔, 패딩 등) ");
    var prompt = template.render(Map.of("temperature", temperature, "apparentTemperature", apparentTemperature));

    var text = chatClientBuilder
        .build()
        .prompt(prompt)
        .call()
        .content();

    log.info("suggestClothes - clothes: " + text);
    return text;
  }

}
