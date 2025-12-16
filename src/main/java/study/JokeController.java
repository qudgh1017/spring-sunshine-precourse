package study;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class JokeController {
  private final ChatClient.Builder chatClientBuilder;

  // String 방식
  @GetMapping(path = "/v1/joke")
  public String joke1(
      @RequestParam(defaultValue = "Tell me a joke") String message)
  {
    return chatClientBuilder
        .build()
        .prompt(message)
        .call()
        .content();
  }

  // chat Response 방식
  @GetMapping(path = "/v2/joke")
  public ChatResponse joke2(
    @RequestParam(defaultValue = "Bob") String name,
    @RequestParam(defaultValue = "pirate") String voice)
  {
    // UserPrompt
    var userMessage = new UserMessage("""
      Tell me about three famous pirates from the Golden Age of Piracy and what they did.
      Write at least one sentence for each pirate.
      """
    );

    // SystemPrompt (name, voice)
    var systemPromptTemplate = new SystemPromptTemplate("""
      You are a helpful AI assistant.
      You are an AI assistant that helps people find information.
      Your name is {name}.
      You should reply to the user's request using your name and in the style of a {voice}.
      """
    );
    var systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));
    var prompt = new Prompt(userMessage, systemMessage);

    return chatClientBuilder
        .build()
        .prompt(prompt)
        .call()
        .chatResponse();
  }

  // OutputConverter
  @GetMapping(path = "/v1/actors")
  public ActorsFilms actors(
      @RequestParam(defaultValue = "Tom Cruise") String actor) {
    var beanOuputConverter = new BeanOutputConverter<>(ActorsFilms.class);
    var format = beanOuputConverter.getFormat();
    var userMessage = """
      Generate the filmography of 5 movies for {actor}.
      {format}
      """;
    log.info(format);

    var prompt = new PromptTemplate(userMessage)
        .create(Map.of("actor", actor, "format", format));

    var text =  chatClientBuilder
        .build()
        .prompt(prompt)
        .call()
        .content();
    log.info(text);

    return beanOuputConverter.convert(text);
  }

  // 함수호출 (Function Calling)
  @GetMapping("/v1/addDays")
  public String addDays (
      @RequestParam(defaultValue = "0") int days
  ) {
    var template = new PromptTemplate("오늘 기준으로 {days}일 뒤 날짜를 알려줘.");
    var prompt = template.render(Map.of("days", days));
    return chatClientBuilder
        .build()
        .prompt(prompt)
        .toolNames("addDaysFormToday")
        .call()
        .content();
  }

}
