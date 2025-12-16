# spring-sunshine-precourse

## 기능 요구 사항
주어진 도시 이름을 입력받아 외부 REST API를 호출해 해당 도시의 날씨 정보를 조회하고, 이를 정리해 반환하는 간단한 웹 서비스를 스프링 프레임워크로 구현한다.

- 조회한 날씨 데이터를 입력으로 LLM API를 호출해 요약을 생성하고 반환한다.
  - 설정에 따라 요약을 새로 생성하지 않고, 기존 요약(우리가 만든 LLM)을 재사용할 수 있다. 
=> LLM Flag 적용 (비슷한 대답을 유도하는) 
  
- 도시는 물론, 권역 단위로도 날씨를 조회할 수 있다.
  - e.g. 서울 전체, 수도권, 특정 구/동 등 
=> DB 또는 캐싱에 없으면 권역의 위도/경도를 LLM으로부터 조회 
 
- 날씨 또는 기온을 기준으로 복장을 추천한다.
  - e.g. 기온 구간, 강수 여부, 체감온도, 바람 등을 기준으로 추천 규칙을 적용한다. 
=> 프롬프트 설정

- 각 요청에 대해 사용량과 비용 추정치를 로그로 남긴다.
  - 예: 입력 토큰, 출력 토큰, 총 토큰, 모델명, 캐시 사용 여부, 추정 비용 
=> 로깅 방식


```text
[Client]
|
v
[Weather Controller]
|
+--> Location Resolver
|        - DB/Cache
|        - LLM (fallback: 위도/경도 추론)
|
+--> Weather Provider
|        - External Weather API (OpenWeather, KMA 등)
|        - Cache
|
+--> Weather Summary Service
|        - LLM 요약 (Flag 적용)
|        - Summary Cache
|
+--> Outfit Recommendation Engine
|        - Rule 기반 + LLM Prompt
|
+--> Usage Logger
- 토큰/비용/캐시 여부
```
