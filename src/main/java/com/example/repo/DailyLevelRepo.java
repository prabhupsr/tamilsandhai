package com.example.repo;

import com.example.model.DailyLevel;
import com.example.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mchidambaranathan 4/17/2017
 */
@Component
public interface DailyLevelRepo extends CrudRepository<DailyLevel, Long> {
    @Override
    List<DailyLevel> findAll();
}
