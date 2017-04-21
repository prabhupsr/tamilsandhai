package com.example.repo;

import com.example.model.WeeklyLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mchidambaranathan 4/19/2017
 */
@Component
public interface WeeklyLevelRepo extends CrudRepository<WeeklyLevel, Long> {
    @Override
    List<WeeklyLevel> findAll();
}
