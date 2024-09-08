# JPA Practice Examples

* 이 프로젝트는 JPA의 다양한 매핑과 패턴을 실습하기 위한 예제 프로젝트입니다.<br>
* 모든 예제는 패키지로 구성되어 있으며, 각 패키지 내부에 MainApplication 클래스를 통해 실행할 수 있습니다.<br>
* db 의존을 제거하기 위해 h2 mem과 직접 init Data를 넣어주어 작동시킵니다.<br>
* Entity 내부에 주석을 달거나 작동을 원하는 Service의 메서드를 살펴보면 `실행 쿼리` 혹은 `추가된 설명 주석`을 보실 수 있습니다.

## 실행 환경
- Java 17 이상
- Hibernate 6.x 이상
- Gradle

---

## ManyToMany
* junctionTable을 엔티티로 격상하여 복합키 사용. MapsId로 컬럼 단순화
* DTO, Projection, Tuple 로 가져오며 구조 유지하는 방법
* [ManyToMany](src/main/java/jpa/practice/relationship/manytomany)

## OneToMany
* 기본 FETCH JOIN Entity 호출
* DTO, Projection, Tuple 로 가져오며 구조 유지하는 방법 
* [OneToMany](src/main/java/jpa/practice/relationship/onetomany)
  
## 다중 OneToMany
* MultipleBag 에러를 피하기 위해 개별 Collection별로 FETCH JOIN하여 영속성 컨텍스트 올리기
* [OneToMany - multi](src/main/java/jpa/practice/relationship/onetomany2)

## OneToOne
* 단방향 MapsId로 선언하여 테이블 컬럼 단순화
* [OneToOne](src/main/java/jpa/practice/relationship/onetoone)
 
## ElementalCollection
* 삽입, 삭제시 전체 DELETE, N개만큼 INSERT문이 발생한다.
* 따라서 잘 안변하는 값 객체들을 넣을 생각을 하고 그게 아니라면 성능을 위해 엔티티 연관관계로 풀어갈 생각하자.
* [ElementalCollection](src/main/java/jpa/practice/relationship/elementalcollection) 

## BaseEntity
* 기본적인 설정을 추가한 BaseEntity를 상속받아 사용하는 방법
  * Auditing : 생성일, 수정일, 생성자, 수정자
  * Domain : ID, Equals, HashCode 
* [BaseEntity](src/main/java/jpa/practice/relationship/base_entity)

---

## Transient 사용
* 비영속 값 혹은 필드를 선언하여 사용하는 방법
* v1 : 메서드에 @Transient 선언
* v2 : 필드에 @Transient 선언
* v3 : @Formula를 사용하여 비영속 필드에 DB에서 계산된 값을 가져오는 방법
* [Transient](src/main/java/jpa/practice/relationship/useful_transient)

## Transient 객체로 필드 능동적으로 exist 조회
* 기본 JpaRepository는 CrudRepository를 상속받아 구현되어 있어서 existById가 존재한다.
* 비영속 객체를 받아 해당 DB에 값이 동일한 엔티티가 존재하는지 체크하고 싶을 때 사용한다.
* extends QueryByExampleExecutor<Book>를 상속받아 기본 구현을 사용한다.
* ex) 도서 관리 시스템에서 책을 등록할 때 같은 제목과 저자를 가진 책이 이미 데이터베이스에 존재하는지 확인하고, 중복을 방지
* [Transient Field Check Exist](src/main/java/jpa/practice/relationship/check_exist_by_transient_entity)

## JPQL 기본 지원 function
* JPQL에서 지원하는 기본 함수들을 사용하는 방법
* 기본 함수 : 
  * ABS, CASE, COALESCE, CONCAT, LENGTH, LOCATE, LOWER, UPPER, SQRT, SUBSTRING, TRIM
* 특수 함수 :
  * INDEX, KEY, SIZE, IS EMPTY, MEMBER OF
  * TREAT, TYPE은 너무 특수한 경우라서 따로 예제를 만들지 않았다.
* 날짜 컬럼 :
  * LocalDate, LocalDateTime, LocalTime, Instant
* [JPQL Function](src/main/java/jpa/practice/relationship/jpql_basic_function)


## 다중 DataSource
* 다중 DataSource를 사용하여 다중 DB에 접근하는 방법
  * Hikari 설정도 적용하는 Configuration
* primary : h2 mem, secondary : postgresql 을 사용했다.
* Entity Scan과 Repository Scan을 각기 DataSource에 맞게 분리하여 패키지 구성
* Docker를 사용하여 postgresql을 실행.
* AbstractRoutingDataSource를 상속받아 DataSource를 선택하는 방법을 추가적으로 구현하면 좋을 것 같다.
* [Multiple DataSource](src/main/java/jpa/practice/relationship/multi_datasource)


## 엔티티 변경 History 생성
* Envers를 사용하여 엔티티 변경 이력을 생성하는 방법
  * yml에 spring.jpa.properties.org.hibernate.envers.audit_strategy: org.hibernate.envers.strategy.ValidityAuditStrategy 추가
  * implementation 'org.springframework.data:spring-data-envers' 추가
* 지금은 실행시 HHH015007: Illegal argument on static metamodel field injection 에러가 난다.
  * [HHH-17612](https://hibernate.atlassian.net/browse/HHH-17612) 에서 최신 버전에 대응중이다.
* RevisionRepository를 스거나 AuditReader로 더 세부적인 변경 이력을 조회할 수 있다.
* 변경자를 추가하기 위해 CustomRevisionEntity + Listener를 추가하여 사용할 수 있다.
  * [참고](https://gengminy.tistory.com/60)
* [Entity History](src/main/java/jpa/practice/relationship/hibernate_envers)
  

---

# Test 

## 실사용 테스트 예제
* BaseQueryTest를 상속받아 발생하는 쿼리 개수 검증
* @DataJpaTest나 @Transactional을 사용하지 않아서 실 서비스에 근접하게 테스트하기 위함

~~~text
테스트 코드에서 @Transactional을 데이터 초기화 용도로 사용할 때 발생할 수 있는 문제점

1. 의도치 않은 트랜잭션 적용
   문제: 실제 코드에서는 @Transactional이 누락되어 있는데, 테스트 코드에서는 데이터 초기화를 위해 @Transactional이 포함되어 있으면 테스트는 통과하지만 실제 실행 시에는 트랜잭션이 없어 오류가 발생할 수 있습니다.
   결과: 테스트 환경과 실제 환경의 불일치로 인해 테스트가 정확하지 않으며, 의도치 않은 트랜잭션 적용으로 인해 실제 환경에서 문제가 발생할 수 있습니다.
2. 트랜잭션 전파 속성 조절로 인한 테스트 롤백 실패
   문제: @Transactional이 테스트 클래스에 선언되어 있을 때, 전파 레벨이 REQUIRES_NEW인 메서드를 테스트하면, 해당 메서드에서 발생한 트랜잭션이 테스트 트랜잭션과 분리되어 롤백되지 않을 수 있습니다.
   결과: 이로 인해 테스트가 끝난 후 데이터베이스에 남은 데이터가 다른 테스트에 영향을 미치며, 각 테스트 간의 격리가 되지 않아 테스트 신뢰성이 저하됩니다.
3. 비동기 메서드 테스트 롤백 실패
   문제: @Transactional을 사용하여 테스트할 때, 비동기 메서드에서 발생하는 트랜잭션은 테스트 트랜잭션과 분리되어 있어 롤백되지 않을 수 있습니다.
   결과: 비동기 메서드 실행 후 데이터가 초기화되지 않아, 이후 테스트에 영향을 미치고, 테스트 간 격리가 이루어지지 않아 일관성 문제가 발생할 수 있습니다.
4. TransactionalEventListener 동작 실패
   문제: @Transactional이 테스트 코드에 사용되면, TransactionalEventListener가 정상적으로 동작하지 않을 수 있습니다. 이는 테스트 트랜잭션이 커밋되지 않아 AFTER_COMMIT 단계에서 실행되는 이벤트 리스너가 호출되지 않기 때문입니다.
   결과: 이벤트 기반의 구현이 테스트에서 제대로 검증되지 않아, 실제 운영 환경에서 발생할 수 있는 이벤트 처리 문제를 놓칠 수 있습니다.
~~~
* [향로님의 블로그 참고](https://jojoldu.tistory.com/761)
* [발생쿼리 검증추가 Test코드 예제](src/test/java/jpa/practice/relationship/sqlcount_assert)

---
## 정보 MarkDown 

### Config
* [DbConnection 절약](src/main/resources/DBConnection절약.md)
* [Transactional 로깅 설정](src/main/resources/Transaction_로깅_설정.md)
* [Embedded H2에서 SLEEP 구현](src/main/resources/EmbeddedH2에서_SLEEP_구현.md)
* [1차 캐시 로깅 설정](src/main/java/jpa/practice/relationship/logging_persistence_context) 

### 기능 및 성능
* [Transactional이 무시되는 경우](src/main/resources/IgnoreTransactionl상황.md)
* [Transactional readOnly와 readWrite + DTO 상태 분석](src/main/resources/TransactionalReadOnly토글테스트.md)
* [Transactional Timeout, rollback 체크 ](src/main/resources/TransactionTimeout및Rollback체크.md)
* [Query Plan Cache](src/main/java/jpa/practice/relationship/query_plan_cache)
  * Hibernate Statistics를 사용하여 Query Plan Cache를 확인하는 방법
  * In절의 Padding설정에 따라 Query Plan Cache의 변화
    * 각 In 파라미터의 값, 개수별로 따로 실행계획이 저장되는 줄 알았는데 파라미터와 상관이 없나보다?
    * H2 내장 db라 그런지 체크가 필요하다.
#### Hibernate5
* [Hibernate5에서 1:다 조회시 부모중복조회이슈 Distinct 최적화](src/main/resources/Hibernate5_Distinct최적화.md)
 
<br>

* [SLOW_QUERY_LOGGING SETTING](src/main/resources/SLOW_QUERY_LOGGING.md)
  * [예시 코드](src/main/java/jpa/practice/relationship/slow_query_analyzer)

<br>




