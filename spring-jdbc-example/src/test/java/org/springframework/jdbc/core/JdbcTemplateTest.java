package org.springframework.jdbc.core;

import org.h2.jdbcx.JdbcDataSource;
import static org.junit.jupiter.api.Assertions.*;;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import x.y.z.config.tx.TransactionConfiguration;
import x.y.z.service.FooService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplateTest {

    private DataSource dataSource;

    @BeforeEach
    public void setup(){
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:cert;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        this.dataSource = dataSource;
    }


    @Test
    public void TransactionTemplate() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);

        SQLErrorCodeSQLExceptionTranslator sqlErrorCodeSQLExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
        JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager(dataSource);
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        Object result = transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                DefaultTransactionStatus defaultTransactionStatus = (DefaultTransactionStatus) status;
                JdbcTransactionObjectSupport transactionStatusTransaction = (JdbcTransactionObjectSupport) defaultTransactionStatus.getTransaction();
                Connection connection = transactionStatusTransaction.getConnectionHolder().getConnection();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("select 1");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        return resultSet.getInt(1);
                    }
                    return null;
                } catch (SQLException e) {
                    throw sqlErrorCodeSQLExceptionTranslator.translate(null,null,e);
                }
            }
        });
        assertEquals(Integer.valueOf(1), jdbcTemplate.queryForObject("select 1",Integer.class));
    }


    @Test
    public void TransactionTemplate2() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager(dataSource);
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        Object result = transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                DefaultTransactionStatus defaultTransactionStatus = (DefaultTransactionStatus) status;
                JdbcTransactionObjectSupport transactionStatusTransaction = (JdbcTransactionObjectSupport) defaultTransactionStatus.getTransaction();
                Connection connection = transactionStatusTransaction.getConnectionHolder().getConnection();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("select 1");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        return resultSet.getInt(1);
                    }
                    return null;
                } catch (SQLException e) {
                    throw jdbcTransactionManager.getExceptionTranslator().translate(null,null,e);
                }
            }
        });
        assertEquals(Integer.valueOf(1), jdbcTemplate.queryForObject("select 1",Integer.class));
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


    @Test
    public void testAnnotationAwareAspectJAutoProxyCreator(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TransactionConfiguration.class);
        assertEquals(1l,applicationContext.getBean(FooService.class).count());
    }
}
