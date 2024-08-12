package jpa.practice.relationship.sqlcount_assert.config;


import com.vladmihalcea.sql.SQLStatementCountValidator;

public abstract class BaseQueryTest {

    protected void initializeQueryCountTest() {
        SQLStatementCountValidator.reset();  // 쿼리 카운트 초기화
    }

    protected void assertSQLCount(int expectedSelects, int expectedInserts, int expectedUpdates, int expectedDeletes) {
        SQLStatementCountValidator.assertSelectCount(expectedSelects);
        SQLStatementCountValidator.assertInsertCount(expectedInserts);
        SQLStatementCountValidator.assertUpdateCount(expectedUpdates);
        SQLStatementCountValidator.assertDeleteCount(expectedDeletes);
    }
}
