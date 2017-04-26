package com.example.repo;

import com.example.model.Favorites;
import com.example.model.StockDetails;
import com.example.model.UserDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author mchidambaranathan 4/21/2017
 */
@Component
public interface StockDetailsRepo extends CrudRepository<StockDetails, Long> {

    List<StockDetails> findByType(String type);

    @Query("SELECT t FROM StockDetails t where t.id < 6")
    List<StockDetails> findTenStockDetails();

    @Override
    List<StockDetails> findAll();
}