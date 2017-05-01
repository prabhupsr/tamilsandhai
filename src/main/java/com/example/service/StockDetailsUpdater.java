package com.example.service;

import com.example.model.StockDetails;
import com.example.repo.FavRepo;
import com.example.repo.StockDetailsRepo;
import com.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author mchidambaranathan 4/21/2017
 */
@Component
@EnableScheduling
public class StockDetailsUpdater {

    @Autowired private StockDetailsRepo stockDetailsRepo;
    @Autowired private FavRepo favRepo;

    private final Pattern pattern = Pattern.compile(".*lastPrice\":\"([^']*)\",\"pChange.*");

    @Scheduled(cron = "0 15 16 ? * MON-FRI")
    private void scheduledNiftyUpdate() {
        stockDetailsRepo.findByType(Constants.NIFTY_STRING).stream().forEach(this::updateStockDetails);
    }

    public void updateStockDetails(final StockDetails stockDetails) {
        getCloseValues(stockDetails.getSymbol()).ifPresent(v -> {
            stockDetails.setDailyClose(v);
            if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY || Objects.isNull(stockDetails.getWeeklyClose())){
                stockDetails.setWeeklyClose(v);
            }
            stockDetails.setLastUpdated(Calendar.getInstance().getTime());
            stockDetailsRepo.save(stockDetails);
            favRepo.updateBySymbol(stockDetails.getSymbol(), v);
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
}
