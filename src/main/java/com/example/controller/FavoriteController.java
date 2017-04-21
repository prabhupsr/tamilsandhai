package com.example.controller;

import com.example.model.Favorites;
import com.example.repo.FavRepo;
import com.example.repo.UserLoginActivityLogRepo;
import com.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author mchidambaranathan 4/19/2017
 */
@RestController
public class FavoriteController {

    @Autowired private FavRepo favRepo;
    @Autowired private UserLoginActivityLogRepo userLoginActivityLogRepo;

    @RequestMapping(value = "updateFav", method = RequestMethod.POST, produces = {"application/json"})
    public String updateFavorites(@RequestBody final Favorites favorites, final HttpSession session) {
        final String uName = (String) session.getAttribute(Constants.USER_NAME);
        userLoginActivityLogRepo.findLatestByUserName(uName).map(o -> {
            o.setLastActivityTime(new Date());
            return o;
        }).ifPresent(userLoginActivityLogRepo::save);
        final Optional<Favorites> byUserName = favRepo.findByName(favorites.getOldName());
        if (byUserName.isPresent()) {
            final Favorites favorites1 = byUserName.get();
            favorites1.setName(favorites.getName());
            favorites1.setDailyClose(favorites.getDailyClose());
            favorites1.setWeeklyClose(favorites.getWeeklyClose());
            favorites1.setOldName(favorites.getOldName());
            favRepo.save(favorites1);
        } else {
            favorites.setUserId(uName);
            favRepo.save(favorites);
        }
        return "abc";
    }

    @RequestMapping(value = "getFavorites", method = RequestMethod.GET, produces = {"application/json"})
    public List<Favorites> getFavorites(final HttpSession session) {
        final String uName = (String) session.getAttribute(Constants.USER_NAME);
        return favRepo.findByUserId(uName);
    }

}
