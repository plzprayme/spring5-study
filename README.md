# 스프링 인 액션 5 따라하기

## Chapter 1.

### Spring Application Context

스프링은 **Spring Application Context**라는 **Container**를 제공하는데, 이 녀석은 **Application Component**들을 생성하고 관리한다.

**Application Component** 혹은 **Bean**들은 **Spring Application Context** 내부에서 서로 연결되어 완전한 어플리케이션을 만든다. 

**Bean**의 상호 연결은 **의존성 주입_(Dependency Injection_**)이라고 알려진 패턴을 기반으로 수행된다. **Application Component**에서 의존하는 다른 빈의 생성과 관리를 각각의 빈이 하는 것이 아니라 **Container**가 해준다.

즉, **Container**는 모든 컴포넌트를 생성, 관리하고 해당 컴포넌트를 필요로 하는 **Bean**에 주입한다. 일반적으로 생성자 인자 또는 속성의 접근자 메서드를 통해 처리된다.



### Annotations

#### `@SpringBootApplication`

`@SpringBootConfiguration`, `@EnableAutoConfiguration`, `@ComponentScan`을 합쳐놓은 녀석이다.

`@EnableAutoconfiguration`: 자동 구성(Auto Configuration)을 활성화한다.

`@SpringBootConfiguration`: 현재 클래스를 `Configure Class`로 지정한다. (`@Configuration`의 특화된 형태이다.)

`@ComponentScan`: 컴포넌트 검색을 활성화한다. `@Component`, `@Controller`, `@Service` 등의 애노테이션이 포함된 클래스를 탐색해서 Spring Application Context에 컴포넌트로 등록한다.



#### `@Configuration`

해당 클래스를 `Configure Class`라는 것을 스프링에게 알려준다. `Configure Class`에는 **Bean**을 등록해놓는 클래스이다.



#### `@Controller`

해당 클래스가 컴포넌트로 식별되게 하는 것이 주 목적이므로 많은 기능을 하지는 않는다.

`@Controller`가 붙은 클래스는 Spring Application Context의 Bean에 인스턴스로 생성된다.

`@Service`, `@Repository`를 포함해서 소수의 애노테이션들이 `@Controller`와 동일한 기능을 제공하므로 어떤 것을 사용해도 된다. 그렇지만 역할에 맞는 애너테이션을 사용함으로써 컴포넌트의 역할을 명확하게 설명할 수 있다.



#### `@WebMvcTest(*.class)`

테스트를 위해서 Spring Mvc 형태로 테스트를 실행하도록 한다. 

실제 서버를 시작하는 대신 Spring Mvc Mocking Machanism을 사용한다. 

```java
mockMvc.perform(get("/"))             // GET /를 수행한다.
    .andExpect(status().isOk)         // HTTP 200인가?
    .andExpect(view().name("home"))   // home view가 있는가?
    andExpect(content().string(containsString("..."))); // "..."이 포함되어 있는가
```



### DevTools

* 코드가 변경될 때 자동으로 애플리케이션을 다시 시작시킨다.
* 브라우저로 전송되는 리소스_(templages, javascript, stylesheet)_가 변경될 때 자동으로 부라우저를 새로고침한다.
* 템플릿 캐시를 자동으로 비활성화한다.
* 만일 H2 데이터베이스가 사용 중이라면 자동으로 H2 콘솔을 활성화한다.



### Spring Web, Thymeleaf

`Spring Web`, `Thymeleaf` 의존성들은 `Spring Mvc Framework`, `Embedded Tomcat`, `Thymeleaf layout dialect` 의존성도 포함시킨다.

이때 SpringBoot의 Auto-Configuration Library도 개입되므로 애플리케이션이 시작될 때 SpringBoot의 Auto-Configuration에서 의존성 라이브러리들을 자동으로 감지한 후 아래와 같은 일들을 수행한다.

* `Spring Mvc`를 활성화하기 위해 **Spring Application Context**에  `Spring Mvc`에 관련된 **Bean**들을 구성한다.
* `Embedded Tomcat` 서버를 **Spring Application Context**에 구성한다.

* `Thymeleaf`를 사용하는 `Spring Mvc View`를 나타내기 위해 `Thymeleaf View resolver`를 구성한다.



위와 같은 감춰진 작업들로 인해 우리는 서버의 로직에 집중할 수 있다.



### Spring Framework

`Spring Initializr`를 살펴보면 100개가 넘는 의존성이 존재한다. 그 중 몇 가지 중요한 것을 알아보자.

#### Core Spring Framework

스프링의 거의 모든 것의 기반이 된다. 핵심 컨테이너와 의존성 주입 외에 몇 가지 다른 기능도 제공한다.

몇 가지 기능 중 하나는 `Spring Mvc`이다. 이번 장에서는 `Spring Mvc`를 사용해서 웹 요청을 처리하는 `Controller` 클래스를 작성했다.  `Spring Mvc`를 사용하여 `REST API`도 만들 수 있다.

또한, `JdbcTemplate`를 포함한 기본적인 data persistence도 제공한다. 

가장 최신 버전에서는 `Spring WebFlux`를 활용하여 리액티브 프로그래밍이 가능하다.



#### SpringBoot

SpringBoot를 활용하면 스타터 의존성과 Auto-Configuration을 통해 편리하게 서버의 로직에 집중할 수 있다. 이외에도 추가 기능이 있다.



* `Actuator`는 애플리케이션의 내부 작동을 런타임 시에 살펴볼 수 있는 기능을 제공한다.
  * `Metric`, `Thread Dump Infomation`, `Application Status`, `Application properties` 기능이 포함된다.
* 환경 속성의 명세
* 핵심 프레임워크에 추가되는 테스트 지원

필요한 경우에 `SpringBoot CLI`도 사용할 수 있다. 



#### Spring Data

`Spring Data`를 사용하면 간단한 자바 인터페이스로 `Data Repository`를 정의할 수 있다. 

`Spring Data`는 서로 다른 종류의 데이터베이스와 함께 사용할 수 있다. `JPA`, `Mongo`, `Neo4j`를 동시에 사용할 수 있다.



#### Spring Security

인증_(Authentication)_, 허가_(Authorization)_. API 보안을 포함하는 폭넓은 범위의 보안 요구를 다룬다. 범위가 아주 넓다.



#### Spring Integration / Batch

서로 다른 어플리케이션과의 통합, 같은 어플리케이션의 서로 다른 컴포넌트의 통합이 필요할 때가 반드시 생긴다. 이런 요구사항을 해결하기 위한 어플리케이션 통합 패턴 구현을 제공한다.

`Spring Integration`은 실시간 통합을 하는 반면에 `Spring Batch`는 트리거에 의한 통합을 처리한다.



#### Spring Cloud

Spring을 활용하여 **마이크로서비스**를 구축하도록 돕는다.



## Chaper 2.

### Lombok

#### @Data

`@Getter`, `@Setter `, `@EqualsAndHashCode`, `@RequiredArgsConstructor` 어노테이션을 매핑한 것과 같은 역할을 한다.

#### @Slf4j

`logger` 필드를 생성해준다. 

```java
@Slf4j
public class LogExample{
    
}

will generate:
public class LogExample {
	private static final org.sl4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
}
```

`SpringBoot` 에서는 `logback` 이 기본 로깅 프레임워크로, 의존하고 있는 `slf4j api`와 `bridge` 모듈을 함께 포함하고 있어 로그 처리 관련 모듈을 추가하지 않아도 된다.

`logback`을 활용하면 로그를 파일로 남길 수 있지만 콘솔에서 로그만 확인하려면 `application properties`에 로그 레벨만 설정하는 것으로 로그 확인이 가능하다.

출처: https://moztiq.medium.com/lombok-slf4j-in-spring-boot-5ba85ccd7a25



`log.info()`를 사용해서 로그를 확인하자



### Spring

#### @RequestMapping

`Spring Mvc`에서는 `@RequestMappingHandlerMapping`, `Spring WebFlux`에서는 `@RequestMappingHandlerAdapter`를 통해서 제공된다.

`class` 레벨과 `mehotd` 레벨에 매핑될 수 있다. 다만, `method` 레벨에서는 `@GetMapping`, `@PostMapping`, `@PutMappint`, `@DeleteMapping`, `@PatchMapping`을 사용하는 것을 추천한다.

`class` 레벨에 사용되면 `base path`를 매핑하는 용도로 사용되며 `method` 레벨에서는 `HTTP Header`, `URL Parameters`, `produces/consumes` 등 다양한 정보를 설정할 수 있다.

#### org.springframework.ui.Model

`View`에 동적으로 등록할 `attributes`들을 홀딩하는 인터페이스이다.

`java.util.Map`을 포함한 모든 데이터 타입을 수용할 수 있다.

#### org.springframework.web.servlet.config.annotation.WebMvcConfigurer

`WebMvcConfigurer`는 인터페이스임에도 불구하고 모든  메소드가 `default method`이다. 따라서 우리가 필요한 메소드만 오버라이딩하면 된다.



아무런 로직 없이 그저 `Template`만 출력하는 경우에는 `addViewControllers(ViewController Registry)` 메소드를 오버라이딩해서 컨트롤러를 대체해보자.



위와 같이 간단한 컨트롤러를 대체할 경우 `@Configuration` 어노테이션이 필요하다.

#### Validation

`Spring Mvc`에서 폼 데이터 검증을 할 때는 다음과 같은 절차를 따른다.

`Validation API`는 `Hibernate component`에 구현되어 있고 `Hibernate component`는 `spring-boot-start`가 자동으로 의존성을 주입한다.



* 도메인 클래스에 검사 규칙을 선언한다. (`@NotNull`, `@Size`, `@Digits`, `@Pattern`, `@CreditCardNumber`,`@NotBlank` 등등)
* 유효성 검사를 수행하는 컨트롤러 메서드에 검사를 수행한다는 것을 지정한다. (`@Valid` 어노터에션을 컨트롤러 파라미터에 추가하기)
* 검사 에러를 `View` 에 매핑할 수 있도록 한다.



`thymeleaf`와 함께 사용하면 에러에 대한 처리가 상당히 편리해진다. 

`view`에 작성해놓은 `th:errors` 로직과 `controller`에 작성해놓은 `errors.hasErrors()`의 콜라보



##### `@Valid`

어노테이션을 매핑한 메소드가 실행되기 전에 먼저 어노테이션이 매핑된 변수를 검증한다.

미리 정해놓은 규칙과 맞지 않는 것이 하나라도 있다면 `Errors` 객체에 저장되어서 메소드에 전달된다. 

`errors.hasErrors()` 메소드를 호출하여 검증 값을 확인하는 절차가 필요하다.