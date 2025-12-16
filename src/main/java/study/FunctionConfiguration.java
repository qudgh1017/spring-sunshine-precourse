package study;

import java.time.LocalDate;
import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class FunctionConfiguration {

  @Description("Calculate a date after adding days from today")
  @Bean
  public Function<AddDayRequest, DateResponse> addDaysFormToday() {
    return request -> {
      var result = LocalDate.now().plusDays(request.days());
      return new DateResponse(result.toString());
    };
  }

}

