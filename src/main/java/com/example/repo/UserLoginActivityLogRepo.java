package com.example.repo;

import com.example.model.UserLoginActivityLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author mchidambaranathan 4/20/2017
 */
@Component
public interface UserLoginActivityLogRepo extends CrudRepository<UserLoginActivityLog, Long> {

    @Query("SELECT t FROM UserLoginActivityLog t where t.userId = :name and t.activityType=2")
    List<UserLoginActivityLog> findByUserId(@Param("name") Long name);

    @Query("SELECT t FROM UserLoginActivityLog t where t.activityType=2 and id in (select max(u.id) from UserLoginActivityLog u "
        + " group by u.userName )")
    List<UserLoginActivityLog> findAllLoggedInUserId();

    @Query(
        "SELECT t FROM UserLoginActivityLog t where  t.activityType=2 and id= (select max(u.id) from UserLoginActivityLog u where u"
            + ".userName = :name group by u.userName )")
    Optional<UserLoginActivityLog> findLatestByUserName(@Param("name") String name);

}
