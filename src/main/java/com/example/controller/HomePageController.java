package com.example.controller;

import com.example.model.UserDetails;
import com.example.model.UserLoginActivityLog;
import com.example.repo.UserDetailsRepo;
import com.example.repo.UserLoginActivityLogRepo;
import com.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * @author mchidambaranathan 4/18/2017
 */
@Controller
public class HomePageController {

    @Autowired private UserDetailsRepo userDetailsRepo;
    @Autowired private UserLoginActivityLogRepo userLoginActivityLogRepo;

    @RequestMapping("/")
    public String getHomes() {
        return "login.html";
    }

    @RequestMapping(value = "/landingPage")
    public String getlogin(
        @ModelAttribute final UserDetails userDetails,
        final HttpSession session,
        final Model model) {
        session.setAttribute(Constants.USER_NAME, userDetails.getUserName());
        final Optional<UserDetails> byUserName = userDetailsRepo.findByUserName(userDetails.getUserName())
            .filter(o -> Optional.ofNullable(userDetails.getPassword())
                .filter(o.getPassword()::equals).isPresent());
        if(byUserName.isPresent() && byUserName.get().getAdmin()){
            return  "lgn.html";
        }
        final Optional<String> viewName = byUserName.filter(ud -> new Date().getTime() <= ud.getSubscriptionEndDate()
            .getTime())
            .map(o -> "landingpage.html");

        viewName.ifPresent(obj->byUserName.map(o -> new UserLoginActivityLog(
            o.getUserId(),
            o.getUserName(),
            Constants.USER_LOGGED_IN)).ifPresent(userLoginActivityLogRepo::save));
        return viewName.orElse("login.html");
    }

    @RequestMapping("/logout")
    public String logOut(final HttpSession session) {
        final String attribute = (String) session.getAttribute(Constants.USER_NAME);
        userDetailsRepo.findByUserName(attribute).map(o -> new UserLoginActivityLog(o.getUserId(), o.getUserName(),
            Constants.USER_LOGGED_OUT)).ifPresent(userLoginActivityLogRepo::save);
        session.invalidate();
        return "homepge.html";
    }

}
