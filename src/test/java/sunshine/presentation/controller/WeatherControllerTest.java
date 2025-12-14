package sunshine.presentation.controller;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sunshine.infrastructure.http.OpenMeteoClient;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto.CurrentWeather;
import sunshine.infrastructure.http.dto.OpenMeteoWeatherResponseDto.Hourly;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WeatherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OpenMeteoClient openMeteoClient;

  @DisplayName("DB에 없는 도시 이름으로 날씨 조회 시 404를 반환한다")
  @Test
  void shouldReturnNotFoundWhenCityDoesNotExist() throws Exception {

    mockMvc.perform(get("/v1/weather")
            .param("city", "부산"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("지원하지 않는 도시입니다: 부산"));
  }

  @DisplayName("도시 이름으로 날씨 요약을 조회한다.")
  @Test
  void shouldReturnWeatherSummaryWhenCityExists() throws Exception {

    // given
    given(openMeteoClient.getWeatherByLocation(
        anyDouble(),
        anyDouble(),
        eq(true),
        eq("celsius"),
        eq("apparent_temperature,relative_humidity_2m")
        )).willReturn(new OpenMeteoWeatherResponseDto(
          new CurrentWeather(1.0, 0),
          new Hourly(List.of(-2.0), List.of(60))
    ));


    // when & then
    mockMvc.perform(get("/v1/weather")
            .param("city", "테스트-도시1"))
        .andExpect(status().isOk())
        .andExpect(content().string(
            "현재 날씨는 0이며, 현재 기온은 1.0°C, 체감 온도는 -2.0°C, 습도는 60%입니다."
        ));
  }
}