package com.example.controller;

import com.example.model.UserDetails;
import com.example.pojo.Levels;
import com.example.repo.DailyLevelRepo;
import com.example.repo.UserDetailsRepo;
import com.example.repo.UserLoginActivityLogRepo;
import com.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.utils.Constants.*;

/**
 * @author mchidambaranathan 4/16/2017
 */
@RestController
public class PersonController {

    @Autowired
    private DailyLevelRepo dailyLevelRepo;

    @Autowired private UserDetailsRepo userDetailsRepo;
    @Autowired private UserLoginActivityLogRepo userLoginActivityLogRepo;

    @RequestMapping(value = "getRange/type/{type}/range/{range}/calculate",
        method = RequestMethod.GET,
        produces = {"application/json"})
    public List<Levels> getDailyRangeValues(
        @PathVariable("range") final Double range,
        @PathVariable("type") final String type, final HttpSession session) throws
        IOException {
        if (!Optional.ofNullable(range).filter(d -> d > 10).isPresent()) {
            return Collections.emptyList();
        }
        final String uName = (String) session.getAttribute(Constants.USER_NAME);
        final boolean isDaily = "daily".equals(type);
        final Optional<UserDetails> userDetails;
        userLoginActivityLogRepo.findLatestByUserName(uName).map(o -> {
            o.setLastActivityTime(new Date());
            return o;
        }).ifPresent(userLoginActivityLogRepo::save);
        userDetails = userDetailsRepo.findByUserName(uName).map(o -> updateLastSearched(o, range, isDaily)).map(
            userDetailsRepo::save);
        return userDetails.isPresent()
            ? decideAndGetLevels(range, "tamil".equals(userDetails.get().getLang()), isDaily)
            : Collections.emptyList();
    }

    private UserDetails updateLastSearched(final UserDetails userDetails, final Double range, final boolean isDaily) {
        if (isDaily) {
            userDetails.setLastSearchedDailyLevel(range);
        } else {
            userDetails.setLastSearchedWeeklyLevel(range);
        }
        return userDetails;
    }

    private List<Levels> decideAndGetLevels(
        final Double range,
        final boolean isLangTamil, final boolean isDaily) {
        return (isDaily) ? getLevels(range, isLangTamil, DAILY_LEVELS_MAP, DAILY_LEVELS_RANGE_LIST)
            : getLevels(range, isLangTamil, WEEKLY_LEVELS_MAP, WEEKLY_LEVELS_RANGE_LIST);

    }

    private List<Levels> getLevels(
        final Double range,
        final boolean isLangTamil,
        final NavigableMap<Double, ? extends Levels> map,
        final List<Double> doubles) {
        final Double rangeMappedKey = map.floorKey(range);
        //final int numberOfValuesBefore = (Objects.equals(rangeMappedKey, range)) ? 8 : 7;
        final int indexOfRangeKey = doubles.indexOf(rangeMappedKey);
        final List<Double> longs = doubles.subList(
            indexOfRangeKey - 7, indexOfRangeKey + 8);
        final Stream<? extends Levels> stream = longs.stream()
            .map(map::get);
        return (isLangTamil) ? stream.map(dl -> new Levels(
            dl.getLevel(),
            ("1\r".equals(dl.getMcl()) ? "Mcl".concat(dl.getTamilName().substring(0, 3)) : dl.getTamilName()),
            dl.getMcl()))
            .collect(Collectors.toList()) : stream.map(dl -> new Levels(
            dl.getLevel(),
            ("1\r".equals(dl.getMcl()) ? "Mcl".concat(dl.getName().substring(0, 3)) : dl.getName()),
            dl.getMcl()))
            .collect(Collectors.toList());
    }

}
