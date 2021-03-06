package com.example.utils;

import com.example.model.*;
import com.example.repo.*;
import com.example.service.StockDetailsUpdater;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.example.utils.Constants.*;

/**
 * @author mchidambaranathan 4/17/2017
 */
@Component
@EnableScheduling
public class DataLoader {

    private final DailyLevelRepo dailyLevelRepo;
    private final WeeklyLevelRepo weeklyLevelRepo;
    private final UserDetailsRepo userDetailsRepo;
    private final StockDetailsRepo stockDetailsRepo;
    private final StockDetailsUpdater stockDetailsUpdater;
    private final FavRepo favRepo;

    @Autowired
    public DataLoader(
        final DailyLevelRepo dailyLevelRepo,
        final FavRepo favRepo,
        final WeeklyLevelRepo weeklyLevelRepo,
        final UserDetailsRepo userDetailsRepo,
        final StockDetailsRepo stockDetailsRepo, final StockDetailsUpdater stockDetailsUpdater) throws IOException {
        this.dailyLevelRepo=dailyLevelRepo;
        this.weeklyLevelRepo=weeklyLevelRepo;
        this.userDetailsRepo=userDetailsRepo;
        this.favRepo=favRepo;
        this.stockDetailsRepo = stockDetailsRepo;
        this.stockDetailsUpdater = stockDetailsUpdater;
        populateNifty50StockDetails();
        pupulateMCXData();
        populateDailyLevelMap();
        populateWeeklyLevelMap();
        //populateDefaultUsers();
        //populateFavorites();
        populateStockDetails();
        //populateNiftyStockUpdates();
    }

    private void loadLevels(final File file, final CrudRepository repo, final boolean isDaily) throws IOException {
        final String content = new String(Files.readAllBytes(file.toPath()));
        final Stream<String[]> stream = Arrays.stream(content.split("\n")).map(line -> line.split(","));
        if(isDaily){
            stream.map(split -> new DailyLevel(Double.parseDouble(split[0]),split[1],"", split[2])).forEach(repo::save);
        }else{
            stream.map(split -> new WeeklyLevel(Double.parseDouble(split[0]),split[1],"", split[2])).forEach(repo::save);
        }
    }

    private void populateDailyLevelMap() throws IOException {
        List<DailyLevel> dailyLevels = dailyLevelRepo.findAll();
        if (dailyLevels.isEmpty()) {
            final File file = ResourceUtils.getFile("classpath:config/dailylvl.txt");
            loadLevels(file,dailyLevelRepo,true);
            dailyLevels = dailyLevelRepo.findAll();
        }
        dailyLevels.forEach(level -> {
            Constants.DAILY_LEVELS_MAP.put(level.getLevel(), level);
            Constants.DAILY_LEVELS_RANGE_LIST.add(level.getLevel());
            });
    }
    private void populateWeeklyLevelMap() throws IOException {
        List<WeeklyLevel> weeklyLevels = weeklyLevelRepo.findAll();
        if (weeklyLevels.isEmpty()) {
            final File file = ResourceUtils.getFile("classpath:config/weeklylvl.txt");
            loadLevels(file,weeklyLevelRepo,false);
            weeklyLevels = weeklyLevelRepo.findAll();
        }
        weeklyLevels.forEach(level -> {
            Constants.WEEKLY_LEVELS_MAP.put(level.getLevel(), level);
            Constants.WEEKLY_LEVELS_RANGE_LIST.add(level.getLevel());
        });
    }

    private void populateFavorites() {
         for(int i=0;i<5;i++){
             favRepo.save(new Favorites(124.0d, 123.0d,"name "+i,"symbol"+i,"pr"));
         }
    }
    private void populateDefaultUsers(){
        userDetailsRepo.save(new UserDetails("pr","123456",false,"english", 99999L,"abc@gm.com"));
        userDetailsRepo.save(new UserDetails("pra","123456",true,"english",99989L,"abdc@gm.com"));
    }

    private void pupulateMCXData() {
        if (!stockDetailsRepo.findByType(Constants.MCX_STRING).isEmpty()) {
            return;
        }
        stockDetailsUpdater.getMCXCloseValues().ifPresent(o -> o.forEach(o1 -> {
            final JSONObject o11 = (JSONObject) o1;
            stockDetailsRepo.save(new StockDetails(
                "",
                o11.getString("Symbol"),
                Constants.MCX_STRING,
                o11.getDouble("LTP")));
        }));
    }

    private void populateStockDetails() {
        if (!stockDetailsRepo.findAll().isEmpty()) {
            return;
        }
        IntStream.range(0, NIFTY_COMPANY_SYMBOLS.size()).forEach(i -> {
            System.out.println(i);
            stockDetailsRepo.save(new StockDetails(NIFTY_COMPANY_NAMES.get(i), NIFTY_COMPANY_SYMBOLS.get(i),
                NIFTY_STRING));
        });
    }

    private void populateNifty50StockDetails() {
        if (stockDetailsRepo.findByType(NIFTY_STRING).size() >= 99) {
            return;
        }
        IntStream.range(0, NIFTY_50_SYMBOLS.size()).forEach(i -> {
            System.out.println(i);
            stockDetailsRepo.save(new StockDetails("", NIFTY_50_SYMBOLS.get(i),
                NIFTY_STRING));
        });
    }

    private void populateNiftyStockUpdates() {
        stockDetailsRepo.findByType(Constants.NIFTY_STRING).stream().forEach(stockDetailsUpdater::updateStockDetails);
    }
}



