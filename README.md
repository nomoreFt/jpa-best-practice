## ManyToMany
* junctionTable을 엔티티로 격상하여 복합키 사용. MapsId로 컬럼 단순화
* DTO, Projection, Tuple 로 가져오며 구조 유지하는 방법
* [ManyToMany](src/main/java/jpa/practice/relationship/manytomany)

## OneToMany
* 기본 FETCH JOIN Entity 호출
* DTO, Projection, Tuple 로 가져오며 구조 유지하는 방법 

## 다중 OneToMany
* MultipleBag 에러를 피하기 위해 개별 Collection별로 FETCH JOIN하여 영속성 컨텍스트 올리기

## OneToOne
* 단방향 MapsId로 선언하여 테이블 컬럼 단순화

## ElementalCollection
* 삽입, 삭제시 전체 DELETE, N개만큼 INSERT문이 발생한다.
* 따라서 잘 안변하는 값 객체들을 넣을 생각을 하고 그게 아니라면 성능을 위해 엔티티 연관관계로 풀어갈 생각하자.



---
## 정보 MarkDown src/main/resources

* DbConnection 절약
* Transactional이 무시되는 경우
* SequenceId 전략별 생성 방법
* Transactional 로깅 설정
* Transactional readOnly와 readWrite + DTO 상태 분석
* Transactional Timeout, rollback 체크 
