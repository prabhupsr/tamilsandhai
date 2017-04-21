package com.example.repo;

import com.example.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mchidambaranathan 4/16/2017
 */
@Component
public interface PersonRepo extends CrudRepository<Person, Long> {
    @Override
    List<Person> findAll();
}
