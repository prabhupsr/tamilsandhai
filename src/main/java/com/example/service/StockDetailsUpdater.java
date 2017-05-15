package com.example.service;

import com.example.model.StockDetails;
import com.example.repo.FavRepo;
import com.example.repo.StockDetailsRepo;
import com.example.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author mchidambaranathan 4/21/2017
 */
@Component
@EnableScheduling
public class StockDetailsUpdater {

    private final Pattern pattern = Pattern.compile(".*lastPrice\":\"([^']*)\",\"pChange.*");
    private final Pattern mcxPattern = Pattern.compile(".*vTicker=([^']*);//]].*");
    @Autowired private StockDetailsRepo stockDetailsRepo;
    @Autowired private FavRepo favRepo;

    @Scheduled(cron = "0 15 16 ? * MON-FRI")
    private void scheduledNiftyUpdate() {
        stockDetailsRepo.findByType(Constants.NIFTY_STRING).stream().forEach(this::updateStockDetails);
    }

    @Scheduled(cron = "0 15 20 ? * MON-FRI")
    public void scheduledMCXUpdate() {
        final List<Object> objects =
            getMCXCloseValues().map(JSONArray::toList).orElseGet(() -> Collections.emptyList());
        final Map<String, Double> mcxUpdateMap =
            objects.stream().map(o -> (JSONObject) o).collect(Collectors.toMap(
                jo -> jo.getString("Symbol"),
                jo1 -> jo1.getDouble("LTP")));
        stockDetailsRepo.findByType(Constants.MCX_STRING).stream().forEach(o -> updateStockDetails(o, mcxUpdateMap));
    }

    private void updateStockDetails(final StockDetails stockDetails, final Map<String, Double> mcxUpdateMap) {
        final Double dailyClose = mcxUpdateMap.get(stockDetails.getSymbol());
        stockDetails.setDailyClose(dailyClose);
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
            || Objects.isNull(stockDetails.getWeeklyClose())) {
            stockDetails.setWeeklyClose(dailyClose);
        }
        stockDetails.setLastUpdated(Calendar.getInstance().getTime());
        stockDetailsRepo.save(stockDetails);
        favRepo.updateBySymbol(stockDetails.getSymbol(), stockDetails.getDailyClose(), stockDetails.getWeeklyClose());
    }

    public void updateStockDetails(final StockDetails stockDetails) {
        getCloseValues(stockDetails.getSymbol()).ifPresent(v -> {
            stockDetails.setDailyClose(v);
            if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY || Objects.isNull(stockDetails.getWeeklyClose())){
                stockDetails.setWeeklyClose(v);
            }
            stockDetails.setLastUpdated(Calendar.getInstance().getTime());
            stockDetailsRepo.save(stockDetails);
            favRepo.updateBySymbol(
                stockDetails.getSymbol(),
                stockDetails.getDailyClose(),
                stockDetails.getWeeklyClose());
        });
    }

    @SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
    private Optional<Double> getCloseValues(final String stk) {
        try {
            final String forObject = new RestTemplate().getForObject(
                Constants.NIFTY_BASE_URL.concat(stk),
                String.class);
            final Matcher matcher = pattern.matcher(forObject);
            System.out.println(stk);
            return Optional.ofNullable(matcher)
                .filter(Matcher::find)
                .map(mt -> mt.group(1).replace(",", ""))
                .map(Double::valueOf);

        } catch (final RuntimeException e) {
            System.out.println(e);//TODO
        }
        return Optional.empty();
    }

    public Optional<JSONArray> getMCXCloseValues() throws JSONException {
        try {
            final String forObject = new RestTemplate().getForObject(
                Constants.MCX_BASE_URL,
                String.class);
            final Matcher matcher = mcxPattern.matcher(forObject);
            System.out.println(forObject);
            final String s = Optional.ofNullable(matcher)
                .filter(Matcher::find)
                .map(mt -> mt.group(1)).get();
            final String s1 = "{\"mcxArray\":".concat(s).concat("}");
            final JSONObject jsnobject = new JSONObject(s1);
            final JSONArray jsonArray = jsnobject.getJSONArray("mcxArray");
            return Optional.ofNullable(jsonArray);

        } catch (final RuntimeException e) {
            System.out.println(e);//TODO
        }
        return Optional.empty();
    }



}
