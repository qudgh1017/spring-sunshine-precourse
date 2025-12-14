package sunshine.presentation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CityControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @DisplayName("지원하는 전체 도시 목록을 조회한다.")
  @Test
  void getCities() throws Exception {
    mockMvc.perform(get("/v1/cities"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(5));
  }
}