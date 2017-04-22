package com.example.controller;

import com.example.model.StockDetails;
import com.example.model.UserDetails;
import com.example.model.UserLoginActivityLog;
import com.example.repo.StockDetailsRepo;
import com.example.repo.UserDetailsRepo;
import com.example.repo.UserLoginActivityLogRepo;
import com.example.service.StockDetailsUpdater;
import com.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * @author mchidambaranathan 4/18/2017
 */
@Controller
public class HomePageController {

    @Autowired private UserDetailsRepo userDetailsRepo;
    @Autowired private UserLoginActivityLogRepo userLoginActivityLogRepo;
    @Autowired private StockDetailsUpdater stockDetailsUpdater;
    @Autowired private StockDetailsRepo stockDetailsRepo;

    @RequestMapping("/")
    public String getHomes() {
        return "homepge.html";
    }

    @RequestMapping(value = "/landingPage")
    public String getlogin(@ModelAttribute final UserDetails userDetails, final HttpSession session) {
        session.setAttribute(Constants.USER_NAME, userDetails.getUserName());
        final Optional<UserDetails> byUserName = userDetailsRepo.findByUserName(userDetails.getUserName())
            .filter(o -> Optional.ofNullable(userDetails.getPassword())
                .filter(o.getPassword()::equals).isPresent());
        byUserName.map(o -> new UserLoginActivityLog(
            o.getUserId(),
            o.getUserName(),
            Constants.USER_LOGGED_IN)).ifPresent(userLoginActivityLogRepo::save);
        return byUserName.map(ud -> (ud.getAdmin()) ? "indx.html" : "index.html").orElse("homepge.html");
    }

    @RequestMapping("/logout")
    public String logOut(final HttpSession session) {
        final String attribute = (String) session.getAttribute(Constants.USER_NAME);
        userDetailsRepo.findByUserName(attribute).map(o -> new UserLoginActivityLog(o.getUserId(), o.getUserName(),
            Constants.USER_LOGGED_OUT)).ifPresent(userLoginActivityLogRepo::save);
        session.invalidate();
        return "homepge.html";
    }

    @RequestMapping(value = "/updateNiftyStocks", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<StockDetails> updateNiftyStocks() {
        stockDetailsRepo.findByType(Constants.NIFTY_STRING).stream().forEach(stockDetailsUpdater::updateStockDetails);
        return stockDetailsRepo.findByType(Constants.NIFTY_STRING);
    }

    @RequestMapping(value = "/findNiftyStocks", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<StockDetails> findNiftyStocks() {
        return stockDetailsRepo.findByType(Constants.NIFTY_STRING);
    }


}
