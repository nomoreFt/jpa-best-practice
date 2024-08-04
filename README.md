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

---

## Transient 사용
* 비영속 값 혹은 필드를 선언하여 사용하는 방법
* v1 : 메서드에 @Transient 선언
* v2 : 필드에 @Transient 선언
* v3 : @Formula를 사용하여 비영속 필드에 DB에서 계산된 값을 가져오는 방법
* [Transient](src/main/java/jpa/practice/relationship/useful_transient)




---
## 정보 MarkDown src/main/resources

* [DbConnection 절약](src/main/resources/DBConnection절약.md)
* [Transactional이 무시되는 경우](src/main/resources/IgnoreTransactionl상황.md)
* [Transactional 로깅 설정](src/main/resources/Transaction_로깅_설정.md)
* [Transactional readOnly와 readWrite + DTO 상태 분석](src/main/resources/TransactionalReadOnly토글테스트.md)
* [Transactional Timeout, rollback 체크 ](src/main/resources/TransactionTimeout및Rollback체크.md)
