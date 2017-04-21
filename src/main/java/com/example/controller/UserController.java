package com.example.controller;

import com.example.model.Favorites;
import com.example.model.StockDetails;
import com.example.model.UserDetails;
import com.example.model.UserLoginActivityLog;
import com.example.pojo.BasicUserDetails;
import com.example.repo.FavRepo;
import com.example.repo.StockDetailsRepo;
import com.example.repo.UserDetailsRepo;
import com.example.repo.UserLoginActivityLogRepo;
import com.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mchidambaranathan 4/17/2017
 */
@RestController
public class UserController {

    @Autowired private UserDetailsRepo userDetailsRepo;
    @Autowired private UserLoginActivityLogRepo userLoginActivityLogRepo;
    @Autowired private StockDetailsRepo stockDetailsRepo;
    @Autowired private FavRepo favRepo;

    @RequestMapping(value = "saveUser", method = RequestMethod.GET, produces = {"application/json"})
    public Long saveUser(@ModelAttribute final UserDetails userDetails) {
        return userDetailsRepo.findByUserName(userDetails.getUserName()).orElseGet(() -> {
            userDetailsRepo.findByUserName(userDetails.getUserName());
            userDetailsRepo.save(userDetails);
            final List<Favorites> defaultFavList =
                stockDetailsRepo.findTenStockDetails().stream().map(sd -> new Favorites(sd.getDailyClose(),
                    sd.getWeeklyClose(),
                    sd.getName(),
                    sd.getSymbol(),
                    userDetails.getUserName())).collect(Collectors.toList());
            favRepo.save(defaultFavList);
            return userDetails;
        }).getUserId();
    }

    @RequestMapping(value = "validateUser", method = RequestMethod.GET, produces = {"application/json"})
    public UserDetails getRangeValues(
        @RequestParam("userName") final String userName,
        @RequestParam("password") final String password) {
        final Optional<UserDetails> byUserName = userDetailsRepo.findByUserName(userName);
        return byUserName.filter(o -> o.getPassword().equals(password)).orElse(null);
    }

    @RequestMapping(value = "getUserDetails",
        method = RequestMethod.GET,
        produces = {"application/json"})
    public BasicUserDetails getUserDetails(final HttpSession session) {
        final String uName = (String) session.getAttribute(Constants.USER_NAME);
        return userDetailsRepo.findByUserName(uName).map(o -> new BasicUserDetails(
            o.getUserName(),
            o.getLastSearchedDailyLevel(),
            o.getLastSearchedWeeklyLevel())).orElse(new BasicUserDetails());

    }

    @RequestMapping(value = "getLoginLog", method = RequestMethod.GET, produces = {"application/json"})
    public List<UserLoginActivityLog> getLoginLog(@RequestParam("userName") final String userName) {
        final Optional<UserDetails> userDetails =
            userDetailsRepo.findByUserName(userName).filter(UserDetails::getAdmin);
        return (userDetails.isPresent()) ?
            userLoginActivityLogRepo.findAllLoggedInUserId().stream().map(o -> {
                o.setStringVal();
                return o;
            }).collect(
                Collectors.toList()) : Stream.of(new UserLoginActivityLog("Requested user "
            + userName
            + "don't Have Admin Access or not exist")).collect(
            Collectors.toList());
    }

}
