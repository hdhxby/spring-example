package io.github.thinkframework.jdbc.repository;

import io.github.thinkframework.jdbc.repository.ThinkRepository;
import org.springframework.data.repository.CrudRepository;

@ThinkRepository
public interface PersonRepository<Person,Long> extends CrudRepository<Person,Long> {
}
