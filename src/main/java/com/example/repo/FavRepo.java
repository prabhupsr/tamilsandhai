package com.example.repo;

import com.example.model.Favorites;
import com.example.model.UserDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author mchidambaranathan 4/19/2017
 */
@Component
public interface FavRepo extends CrudRepository<Favorites, Long> {

    @Query("SELECT t FROM Favorites t where t.name = :name")
    Optional<Favorites> findByName(@Param("name") String name);

    @Query("SELECT t FROM Favorites t where t.userId = :name")
    List<Favorites> findByUserId(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Favorites t  SET t.dailyClose = :closeValue WHERE t.symbol = :symbol")
    Integer updateBySymbol(@Param("symbol") String symbol,@Param("closeValue") Double closeValue);

    @Override
    List<Favorites> findAll();
}
