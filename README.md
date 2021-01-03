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

