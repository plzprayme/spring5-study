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



## Chapter 3-1.

3장에서는 데이터를 저장하는 방법을 학습한다.

자바진영에서는 관계형 뎅이터베이스와 SQL을 사용할 때 `JDBC`, `JPA`를 가장 많이 사용한다.

`Spring Jdbc`와 `Spring JPA`를 활용하면 더욱 쉽게 사용할 수 있다.



### JDBC

일단 의존성을 추가하자

* groupId: `org.springframework.boot`
* artifactId: `springboot-starter-jdbc`



그리고 사용하고자 하는 데이터베이스의 의존성도 추가하자



### JdbcTemplate 사용하기

`Spring JDBC`는 `JdbcTemplate` 객체를 기반으로 Persistence Layer 동작을 수한한다.



#### Read

데이터를 조회할 때는 `query`, `queryForObject` 같은 메소드를 사용한다.



##### `query()`

`query` 메소드는 여러 형태로 제공된다.  그  중 `query(String sql, RowMapper method)` 하나 이상의 Row를 얻고 싶을 때 사용하며 다음과 같은 형태로 사용된다.

```java
@Override
public Iterable<Ingredient> findAll() {
    return jdbc.query(
    	"SELECT id, name, type FROM Ingredient" // SQL 실행된 후 RowMapper 실행
        this::mapRowIngredient); // SQL 실행 결과 Row의 수 만큼 ResultSet, rowNum이 생성되어 RowMapper에게 전달됨
}

// ResultSet에는 SQL에서 요청한 column들의 정보가 key-value 형식으로 제공됨
private Ingredient mapRowIngredient(ResultSet rs, int rowNum) throws SQLException {
    return new Ingredient(
    	rs.getString("id"),
        rs.getString("name"),
    );
}
```



##### `queryForObject()`

하나의 `Row`만 얻고 싶을 때는 `queryForObject`를 사용한다.

`query`에는 `T query(sql, rowMapper)`가 없기 때문이다. `List<T> query(sql, rowMapper)`만 있다.



```java
@Override
Ingredient findById(String id) {
    return jdbc.queryForObject(
    	"SELECT id, name, type FROM Ingredient WHERE id = ?",
        this::mapRowToIngredient, id);
}
```



위와 같이 `?`에 해당하는 녀석들을 마지막에 매개변수로 넘겨줘서 매핑할 수도 있다.



#### Create / Update / Delete

세 동작 모두 `update` 메소드를 사용한다.



##### Create

일반적인 `insert` SQL에 생성하고자 하는 값들을 매핑만 해주면 된다.

```java
@Override
public Ingredient save(Ingredient ingredient) {
    jdbc.update(
        "INSERT INTO Ingredient (id, name, type) values (?, ?, ?)",
        ingredient.getId(),
        ingredient.getName(),
        ingredient.getType());
    return ingredient;
}
```



###### 조금 더 복잡한 Create

`A`를 저장한 후 `A`의 `id`를  리턴 받아서 기억하고 있다가 `B`에 `A`의 `id`를 포함시키며 저장하는 로직을 수행해보자



위의 로직을 순서대로 살펴본다.

1. 먼저, A를 저장한다. 
   일반적인 `String` 타입의 SQL을 사용하지 않고 `PreparedStatementCreatorFactory`를 활용하여 `PreparedStatement`를 생성한다.

```java
private long saveAInfo(A a) {
    ...
    
	// Factory가 생성하는 PreparedStatement에는 ?에 해당하는 데이터 타입만 매핑해준다.
    PreparedStatement psc = new PreparedStatementCreatorFactory(
	"INSERT INTO A (name, createdAt) values (?, ?))", 
    Types.VARCHAR, Types.TIMESTAMP 
    ).newPreparedStatementCreator( // 위에서 등록한 ?에 매핑할 데이터들을 매핑
        Arrays.asList(
            a.getName(),
            new Timestamp(a.getCreatedAt().getTime())
        )
    );
    
    ...
}
```



2. 저장한 A의 id를 리턴 받을 `KeyHolder` 객체를 생성하고 
   `jdbc.update(PreparedStatement, KeyHolder)` 메소드를 통해 저장한다.

```java
private long saveAInfo(A a) {
    ...
    
	// Factory가 생성하는 PreparedStatement에는 ?에 해당하는 데이터 타입만 매핑해준다.
    PreparedStatement psc = new PreparedStatementCreatorFactory(
	"INSERT INTO A (name, createdAt) values (?, ?))", 
    Types.VARCHAR, Types.TIMESTAMP 
    ).newPreparedStatementCreator( // 위에서 등록한 ?에 매핑할 데이터들을 매핑
        Arrays.asList(
            a.getName(),
            new Timestamp(a.getCreatedAt().getTime())
        )
    );
    
	KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbc.update(psc, keyHolder);
    return keyHolder.getKey().longValue();
}
```

리턴 받은 id를 활용하여 나머지 저장 로직을 작성하면 된다.





###### `SimpleJdbcInsert`로 Create 로직 쉽게 수행하기

SimpleJdbcInsert를 사용하면 훨씬 코드가 줄어든다.

1. 생성자에서 작업을 하고 싶은 테이블에 해당하는 `SimpleJdbcInserter`를 생성한다.

```java
private SimpleJdbcInsert orderInserter;
    
@Autowired
public JdbcOrderRepository(JdbcTemplate jdbc) {
    orderInserter = new SimpleJdbcInsert(jdbc)
        .withTableName("Order")
        .usingGeneratedKeyColumns("id");
}
```



2. 생성한 `SimpleJdbcinsert` 인스턴스를 활용하여 `Create` 로직을 수행한다.

```java
private long saveOrder(Order order) {
    // Map 형태의 파라미터를 넘겨주면 create가 진행된다.
    Map<String, Object> values = objectMapper.convertvalue(order, Map.class);
    long orderId = orderInserter
        .executeAndReturnKey(values)
        .longValue();
}
```



### `org.springframework.core.convert.converter`

`Converter`는  `Interface`로서 클래스로 구현해서 사용하면 된다. `Converter<S, T>`의 형태이며 `S`를 `T` 타입으로 변환해준다.

`thread-safe`하며 공유될 수 있다.

```java
public class MyConverter implements Converter<String, Ingredient> {
    return ingredientRepository.findById(id);
}
```





### 새로 알게된 Annotation

#### `@Repository`

스테레오 타입 애노테이션 중 하나이다. 스프링에게 이 클래스가 어떤 역할을 하는지 알려준다. Component Scan이 자동으로 찾아와서 스프링 애플리케이션 컨텐스트의 빈으로 생성해준다.



#### `@ModelAttribute`

바인딩한 메소드 파라미터나 메소드 리턴값을 지정한 이름의 `model attribute`에 넣어서 `web view`에 출력해준다.



##### 메소드 레벨

메소드 레벨에 바인딩한다면 해당 메소드가 하나 이상의 `model attribute`를 추가하기 위한 목적이라는 것을 나타낸다. `@RequestMapping`, `@GetMapping`, `@PostMapping` 등의 어노테이션도 `model attribute` 추가를 허용하지만 `@ModelAttribute`는 요청에 직접적으로 매핑할 수는 없다.



**만약 `@ModelAttribute`가 붙은 메소드가 존재한다면 `Spring-MVC`는 `@RequestMapping` 메소드들을 호출하기 전에 가장 먼저 `@ModelAttribute`가 붙은 메소드를 호출한다.**



##### 메소드 파라미터 레벨

HTTP 요청에 들어있는 속성값들을 파라미터에 매핑해준다. `View`와의 상호작용이 필요하다. 주로 `form` 데이터를 받을 때 많이 사용한다.



#### `@SessionAttributes` 

애노테이션에 설정한 이름의 데이터들을 세션에 넣어주는 역할을 한다. 

특히, `@ModelAttribute`는 세션에 존재하는 데이터들도 바인딩하기 때문에 `@SessionAttribute`와 `@ModelAttribute`를 조합하여 사용하면 서로 다른 페이지에서 유지하고 있는 정보들을 공유할 수 있다.

사용이 끝난 세션 데이터는 `SessionStatus.setComplete()`를 호출하여 cleanup을 통해 서버의 과부하를 막아야 한다.



#### `@SuppressWarnings`

IntelliJ 기준으로 컴파일러에 의해 노란색 경고가 나타나는 것을 방지해준다.

IDE가 정적분석을 할 떄 해당 노란색 경고를 무시해달라고 요청하는 어노테이션이다.

파라미터는 여러가지 `String` 타입이 존재한다.



### SQL 쿼리 실행시키기

만약 **테이블 정의**에 관한 SQL을 실행시키고 싶다면  테이블 생성 SQL이 작성된 `schema.sql`  파일을 `src/main/resources`에 저장하면 된다.

만약 **데이터 생성**에 관한 SQL을 실행시키고 싶다면 데이터 생성 SQL이 작성된 `data.sql` 파일을 `src/main/resources`에 저장하면 된다.

그러면 `Spring Application`이 시작될 때 두 SQL 파일을 실행시킨다.



## Chapter 3-2.

### `javax.persistence.*`

#### `@Entity`

해당 클래스가 테이블이라고 선언한다. 반드시 클래스안에 `@Id`가 선언되어야 한다.

클래스의 이름을 대문자를 기준으로 구분하여 `_`와 연결시켜서 생성해준다.

`ex) orderIngredient -> order_ingredient`

#### `@Id`

테이블의 기본 키와 매핑한다.

#### `@GeneratedValue( strategy= GenerationType)`

자동으로 값이 생성되게한다. `TABLE`, `SEQUENCE`, `IDENTITY`, `AUTO`전략이 있다.

* `AUTO`: `@generatedValue`의 기본값이다. hibernate가 데이터베이스에 맞는 전략을 선택한다.
* `IDENTITY`:  `auto-increment`와 같은 의미이다.
* `SEQUENCE`: `SEQUENCE`를 이용하여 PK를 생성한다. 

* `TABLE`: `TABLE`을 이용하여 PK를 생성한다.

#### `@ManyToMany`

@Data는 뭘 생성하지만 NoArgs가 생기면 없어진다. 그래서 Required추가함



#### `@PrePersist`

해당 객체가 생성되기 전에 지정한 메소드의 코드가 동작한다.

```java
@PrePersist
void createdAt() {
    this.createdAt = new Date();
}
```



### `JPA `

#### `CrudRepository<T, ID>`

Spring DSL

Spring Data Signature

List<Order> findByDeliveryZip(String deliveryZip); =>

SELECT * FROM Order WHERE deliveryZip = ?

Spring Data는 method signature를 분석하여 쿼리를 결정한다. find == read == get

개체수를 원한다면 count

orders를 무시해도 된다. Crud<T>에 해당하는 녀석이 자동으로 지정된다.

서술어는 By 다음에 나온다.

@Query()

정렬은 OrderBy

#### `JpaRepository`



### Java

#### CommandLineRunner`



### `Optional`

