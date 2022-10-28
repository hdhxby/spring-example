package org.springframework.jdbc.core;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
public class
JdbcTemplateTest {

    private DataSource dataSource;

    @Before
    public void setup(){
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:cert;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        this.dataSource = dataSource;
    }

    @Test
    public void testJdbcTemplate() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        assertEquals(Integer.valueOf(1), jdbcTemplate.queryForObject("select 1",Integer.class));
    }


    @Test
    public void testDataSourceTransactionManager() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);

        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        try {
            assertEquals(Integer.valueOf(1), jdbcTemplate.queryForObject("select 1",Integer.class));
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transactionStatus);
        }
    }

    @Test
    public void testTransactionTemplate() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:cert;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);

        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSourceTransactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    assertEquals(Integer.valueOf(1), jdbcTemplate.queryForObject("select 1",Integer.class));
                } catch (Exception e) {
                    status.setRollbackOnly();
                }
            }
        });

    }
}
