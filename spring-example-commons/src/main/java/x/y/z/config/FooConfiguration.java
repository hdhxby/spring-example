package x.y.z.config;

import lombok.extern.java.Log;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import x.y.z.aop.LoggingAspect;

import javax.sql.DataSource;

@ComponentScans({
        @ComponentScan("x.y.z.**")})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Configuration
public class FooConfiguration {


    @Bean
    public DataSource dataSource(){
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:foo;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Bean
    public LoggingAspect loggingAspect(){
        return new LoggingAspect();
    }

//    @Bean
//    public TransactionManager transactionManager(){
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(){
//        return new JdbcTemplate(dataSource());
//    }
}
