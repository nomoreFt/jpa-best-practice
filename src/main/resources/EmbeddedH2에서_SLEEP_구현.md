종종 인위적으로 DB-Connection을 가지고 오랜 시간이 걸리는 작업을 구현해야 할 때가 있다.<br>
H2는 `sleep` 함수를 제공하지 않기 때문에, `CREATE ALIAS`를 사용하여 `sleep` 함수를 구현해야 한다.<br>

---

# sleep Function 기능 구현

~~~java
public class CustomFunctions {
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
~~~


# Function Provider 구현

~~~java
@Service
public class FunctionProvider {
    private final EntityManager entityManager;

    public FunctionProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addFunction() {
        entityManager.createNativeQuery("CREATE ALIAS SLEEP FOR \"jpa.practice.relationship.sqlcount_assert.config.CustomFunctions.sleep\"")
                .executeUpdate();
    }
}

~~~


# 실제 사용모습

~~~java
@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<Author, Long>{

    @Query(value = "SELECT * FROM  author WHERE sleep(5000)", nativeQuery = true)
    Author findByName();
}
~~~


~~~sql
Name:DATA_SOURCE_PROXY, Connection:8, Time:5006, Success:True
Type:Prepared, Batch:False, QuerySize:1, BatchSize:0
Query:["SELECT * FROM  author WHERE sleep(5000)"]
Params:[()]
~~~


---


# Test시

~~~java
@BeforeEach
void before() {
    functionProvider.addFunction();
}
~~~