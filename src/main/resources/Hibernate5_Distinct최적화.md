## 하이버네이트 5의 경우

```java
@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<Author, Long> {
    /**
     * 각 Book의 개수만큼 부모가 중복으로 포함되어 조회된다.(자식 개수만큼 부모가 조회)
     * ex) 1개의 Author가 3개의 Book을 가지고 있다면, 3개의 Author가 각각 Book3개와 함께 중복 조회된다.
     * 
     * why? left outer join으로 가져오기 때문이다. 5명의 Author, 각 저자가 3개의 책을 가진 경우 5*3=15개의 결과가 나온다.
     */
    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> fetchWithDuplicates();

    /**
     * 위의 중복 문제를 해결하기 위해 DISTINCT를 사용한다.
     * 근데 DISTINCT는 성능상 좋지 않다.
     * 결과세트를 가져온 후 중복을 제거하기 때문에 성능이 떨어진다.
     */
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> fetchWithoutHint();

    /**
     * 힌트를 사용하면 실제 쿼리에 DISTINCT가 적용되지 않는다.
     * DTO, 스칼라 쿼리는 힌트를 사용할 수 없다.
     */
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    @QueryHints(value = @QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
    List<Author> fetchWithHint();
}
```


## 하이버네이트 6

하이버네이트 6의 경우 더이상 부모 엔티티가 중복으로 조회되지 않는다.

```java     

@Query("SELECT a FROM Author a LEFT JOIN FETCH a.books")
List<Author> fetchWithNotDuplicates();

```

의도대로 잘 작동한다.<br>
HINT_PASS_DISTINCT_THROUGH는 아예 없어졌다.<br>

>DISTINCT
> Starting with Hibernate ORM 6 it is no longer necessary to use distinct in JPQL and HQL to filter out the same parent entity references when join fetching a child collection. The returning duplicates of entities are now always filtered by Hibernate.
> 
> Which means that for instance it is no longer necessary to set QueryHints#HINT_PASS_DISTINCT_THROUGH to false in order to skip the entity duplicates without producing a distinct in the SQL query.
> 
> From Hibernate ORM 6, distinct is always passed to the SQL query and the flag QueryHints#HINT_PASS_DISTINCT_THROUGH has been removed.