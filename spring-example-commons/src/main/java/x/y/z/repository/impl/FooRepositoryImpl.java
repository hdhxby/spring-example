package x.y.z.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import x.y.z.repository.FooRepository;

import java.util.List;

@Repository
public class FooRepositoryImpl implements FooRepository {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select 1",Long.class);
    }
}
