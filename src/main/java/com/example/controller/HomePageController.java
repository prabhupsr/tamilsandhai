package com.example.controller;

import com.example.model.UserDetails;
import com.example.model.UserLoginActivityLog;
import com.example.pojo.MessageUpdater;
import com.example.repo.UserDetailsRepo;
import com.example.repo.UserLoginActivityLogRepo;
import com.example.utils.Constants;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
        return "newHomePage.html";
    }

    @RequestMapping("/register")
    public String getRegistrationPage() {
        return "registration.html";
    }

    @RequestMapping("/getMemoryUsed")
    @ResponseBody
    public ImmutableMap<String, Long> getMemoryDetails() {
        final Runtime runtime = Runtime.getRuntime();
        return ImmutableMap.of(
            "freeMemory",
            runtime.freeMemory() / (1024 * 1024),
            "Max memory",
            runtime.maxMemory() / (1024 * 1024),
            "totalMemory",
            runtime.totalMemory() / (1024 * 1024));
    }

    @RequestMapping(value = "/landingPage", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public MessageUpdater getlogin(
        @RequestBody final UserDetails userDetails,
        final HttpSession session) {
        final Optional<UserDetails> byUserName = userDetailsRepo.findByDetails(
            userDetails.getUserName(),
            getPhoneNumberForValidation(userDetails.getUserName()))
            .filter(o -> Optional.ofNullable(userDetails.getPassword())
                .filter(o.getPassword()::equals).isPresent());

        if (byUserName.isPresent() && byUserName.get().getAdmin()) {
            session.setAttribute(Constants.USER_NAME, byUserName.get().getUserName());
            return new MessageUpdater("lgn.html", "");
        }
        final Optional<MessageUpdater> loginValidator = byUserName.filter(ud -> new Date().getTime()
            <= ud.getSubscriptionEndDate()
            .getTime())
            .map(o -> new MessageUpdater("newLandingPage.html", ""));

        loginValidator.ifPresent(obj -> {
            session.setAttribute(Constants.USER_NAME, byUserName.get().getUserName());
            byUserName.map(o -> new UserLoginActivityLog(
                o.getUserId(),
                o.getUserName(),
                Constants.USER_LOGGED_IN)).ifPresent(userLoginActivityLogRepo::save);
        });
        return loginValidator.orElseGet(() -> new MessageUpdater(
            "newHomePage.html",
            "Username and Password are not valid"));
    }

    @RequestMapping(value = "/logout", produces = {"application/json"})
    @ResponseBody
    public UserDetails logOut(final HttpSession session) {
        final String attribute = (String) session.getAttribute(Constants.USER_NAME);
        userDetailsRepo.findByUserName(attribute).map(o -> new UserLoginActivityLog(o.getUserId(), o.getUserName(),
            Constants.USER_LOGGED_OUT)).ifPresent(userLoginActivityLogRepo::save);
        session.invalidate();
        return new UserDetails();
    }

    private Long getPhoneNumberForValidation(final String userName) {
        try {
            return Long.valueOf(userName);
        } catch (final NumberFormatException ex) {
            return 0L;
        }

    }

}
