package com.example.repo;

import com.example.model.DailyLevel;
import com.example.model.UserDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author mchidambaranathan 4/17/2017
 */
@Component
public interface UserDetailsRepo extends CrudRepository<UserDetails, Long> {

    @Query("SELECT t FROM UserDetails t where t.userName = :name")
    Optional<UserDetails> findByUserName(@Param("name") String name);
    @Override
    List<UserDetails> findAll();
}
