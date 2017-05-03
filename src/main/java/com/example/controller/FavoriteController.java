package com.example.controller;

import com.example.model.Favorites;
import com.example.pojo.MessageUpdater;
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

/**
 * @author mchidambaranathan 4/19/2017
 */
@RestController
public class FavoriteController {

    @Autowired private FavRepo favRepo;
    @Autowired private UserLoginActivityLogRepo userLoginActivityLogRepo;

    @RequestMapping(value = "updateFav", method = RequestMethod.POST, produces = {"application/json"})
    public MessageUpdater updateFavorites(@RequestBody final Favorites favorites, final HttpSession session) {
        final String uName = (String) session.getAttribute(Constants.USER_NAME);
        userLoginActivityLogRepo.findLatestByUserName(uName).map(o -> {
            o.setLastActivityTime(new Date());
            return o;
        }).ifPresent(userLoginActivityLogRepo::save);
        final Favorites availFavorites = favRepo.findByUserId(uName).get(favorites.getPos());
        availFavorites.setName(favorites.getName());
        availFavorites.setDailyClose(favorites.getDailyClose());
        availFavorites.setWeeklyClose(favorites.getWeeklyClose());
        availFavorites.setOldName(favorites.getOldName());
        favRepo.save(availFavorites);
        return new MessageUpdater("Favorites Successfully Updated");
    }

    @RequestMapping(value = "getFavorites", method = RequestMethod.GET, produces = {"application/json"})
    public List<Favorites> getFavorites(final HttpSession session) {
        final String uName = (String) session.getAttribute(Constants.USER_NAME);
        return favRepo.findByUserId(uName);
    }

}
