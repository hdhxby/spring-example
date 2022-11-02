package x.y.z.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FooRepositoryImpl implements FooRepository {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Override
    public String foo() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `log` (`id` bigint(20))");
        return "";
    }
}
