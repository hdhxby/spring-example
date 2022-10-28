package io.github.thinkframework.jdbc.service;

import com.hdhxby.example.entity.Person;
import io.github.thinkframework.jdbc.repository.PersonRepository;
import io.github.thinkframework.jdbc.repository.ThinkAutowiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @ThinkAutowiredRepository
    private CrudRepository<Person,Long> crudRepository;

    @Autowired
    private PersonRepository personRepository;

    public void findAll(){
        personRepository.findAll();
        crudRepository.findAll();
    }
}
