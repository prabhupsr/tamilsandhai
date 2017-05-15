package com.example.controller;

import com.example.model.StockDetails;
import com.example.pojo.SunRaiseDetails;
import com.example.pojo.SunsetSunRaiseDetails;
import com.example.repo.StockDetailsRepo;
import com.example.service.RestService;
import com.example.service.StockDetailsUpdater;
import com.example.utils.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author mchidambaranathan 4/23/2017
 */
@RestController
public class LandingPageDetailsController {
    @Autowired private StockDetailsUpdater stockDetailsUpdater;
    @Autowired private StockDetailsRepo stockDetailsRepo;
    @Autowired private RestService restService;

    @RequestMapping(value = "/updateNiftyStocks", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<StockDetails> updateNiftyStocks() {
        stockDetailsRepo.findByType(Constants.NIFTY_STRING).stream().forEach(stockDetailsUpdater::updateStockDetails);
        return stockDetailsRepo.findByType(Constants.NIFTY_STRING);
    }

    @RequestMapping(value = "/updateMCXStocks", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<StockDetails> updateMCXStocks() {
        stockDetailsUpdater.scheduledMCXUpdate();
        return stockDetailsRepo.findByType(Constants.MCX_STRING);
    }

    @RequestMapping(value = "/findNiftyStocks", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<StockDetails> findNiftyStocks() {
        return stockDetailsRepo.findByType(Constants.NIFTY_STRING);
    }

    @RequestMapping(value = "/findStocksAndCommodities", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<StockDetails> findStocksAndCommodities() {
        return stockDetailsRepo.findAll();
    }

    @SuppressWarnings("SimpleDateFormatWithoutLocale")
    @RequestMapping(value = "/calculateTimeDetails", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public SunRaiseDetails calculateTimeDetails() throws
        IOException,
        ParseException {
        return Optional.ofNullable(Constants.SUN_RAISE_DETAILS_MAP.get(Calendar.getInstance().get(Calendar.DATE)))
            .orElseGet(() -> calculateAndPopulateSunraiseData());
    }

    @SuppressWarnings("SimpleDateFormatWithoutLocale")
    private SunRaiseDetails calculateAndPopulateSunraiseData() {
        try {
            final JsonNode todayData = restService.getSunRaiseDetails(false);
            final JsonNode tomorrowData = restService.getSunRaiseDetails(true);
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+00:00'");
            final Calendar sunriseToday = Calendar.getInstance();
            sunriseToday.setTime(df.parse(todayData.path("results").path("sunrise").asText()));
            sunriseToday.add(Calendar.MINUTE, 330);
            final Calendar sunsetToday = Calendar.getInstance();
            sunsetToday.setTime(df.parse(todayData.path("results").path("sunset").asText()));
            sunsetToday.add(Calendar.MINUTE, 330);
            final Calendar sunriseTomorrow = Calendar.getInstance();
            sunriseTomorrow.setTime(df.parse(tomorrowData.path("results").path("sunrise").asText()));
            sunriseTomorrow.add(Calendar.MINUTE, 330);
            final long todaySunraiseTime = sunriseToday.getTime().getTime();
            final Long rsDiff = (sunsetToday.getTime().getTime() - todaySunraiseTime) / (12 * 60000);
            final long tSrTSsDiff = (sunriseTomorrow.getTime().getTime() - sunsetToday.getTime().getTime()) / (12 * 60000);
            return populateSunRaiseDetailsMap(sunriseToday,sunsetToday, rsDiff,tSrTSsDiff);
        } catch (final ParseException | RuntimeException e) {
            return new SunRaiseDetails();
        }
    }

    private SunRaiseDetails populateSunRaiseDetailsMap(
        final Calendar sunriseToday,
        final Calendar sunsetToday,
        final Long rsDiff,
        final Long tSrTSsDiff) {
        final int today = sunriseToday.get(Calendar.DAY_OF_WEEK);
        final int todaysDate = sunriseToday.get(Calendar.DATE);
        final DateFormat df = new SimpleDateFormat("hh:mm:ss a");
        final DateFormat timeFormat = new SimpleDateFormat("dd:MM:YYYY");
        final List<String> dayOfWeek = Constants.DAYS_OF_WEEK.stream().collect(Collectors.toList());
        Collections.rotate(dayOfWeek, -1 * Constants.DAYS_OF_WEEK.indexOf(Constants.DAY_TO_SYMBOL_MAPPING.get(today)));
        final SunRaiseDetails sunRaiseDetails = new SunRaiseDetails();
        sunRaiseDetails.setSunRaise(df.format(sunriseToday.getTime()));
        sunRaiseDetails.setSunSet(df.format(sunsetToday.getTime()));
        sunRaiseDetails.setTodaysDate(timeFormat.format(sunsetToday.getTime()));
        sunRaiseDetails.setTodaysDay(Constants.DAY_TO_SYMBOL_MAPPING.get(today));
        IntStream.range(0, 17).forEach(v -> {
            final SunsetSunRaiseDetails o = new SunsetSunRaiseDetails();
            o.setDay(dayOfWeek.get(v % 7));
            o.setStartTime(df.format(sunriseToday.getTime()));
            sunriseToday.add(Calendar.MINUTE, (v>11)?tSrTSsDiff.intValue():rsDiff.intValue());
            o.setEndTime(df.format(sunriseToday.getTime()));
            sunRaiseDetails.getSunsetSunRaiseDetailses().add(o);
        });
        Constants.SUN_RAISE_DETAILS_MAP.clear();
        Constants.SUN_RAISE_DETAILS_MAP.put(todaysDate, sunRaiseDetails);
        return sunRaiseDetails;
    }

}
