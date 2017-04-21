package com.example.service;

import com.example.model.StockDetails;
import com.example.repo.FavRepo;
import com.example.repo.StockDetailsRepo;
import com.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author mchidambaranathan 4/21/2017
 */
@Component
public class StockDetailsUpdater {

    @Autowired private StockDetailsRepo stockDetailsRepo;
    @Autowired private FavRepo favRepo;

    private final Pattern pattern = Pattern.compile(".*lastPrice\":\"([^']*)\",\"pChange.*");

    // @Scheduled(cron = "*/5 * * * * *")
    private void testScedule() {
        Constants.NIFTY_COMPANY_SYMBOLS.stream().map(s -> getCloseValues(s)).collect(
            Collectors.toList());

    }

    public void updateStockDetails(final StockDetails stockDetails) {
        getCloseValues(stockDetails.getSymbol()).ifPresent(v -> {
            stockDetails.setDailyClose(v);
            if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
                stockDetails.setWeeklyClose(v);
            }
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
