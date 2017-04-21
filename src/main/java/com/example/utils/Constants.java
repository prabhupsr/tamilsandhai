package com.example.utils;

import com.example.model.DailyLevel;
import com.example.model.WeeklyLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mchidambaranathan 4/17/2017
 */
public interface Constants {
    Integer USER_LOGGED_IN=2;
    Integer USER_SESSION_TIME_OUT=1;
    Integer USER_LOGGED_OUT=0;
    String USER_NAME="USER_NAME";
    List<Double> DAILY_LEVELS_RANGE_LIST=new ArrayList<>();
    NavigableMap<Double,DailyLevel> DAILY_LEVELS_MAP = new TreeMap<>();
    List<Double> WEEKLY_LEVELS_RANGE_LIST=new ArrayList<>();
    NavigableMap<Double,WeeklyLevel> WEEKLY_LEVELS_MAP = new TreeMap<>();

    String NIFTY_STRING="NIFTY";

    String NIFTY_BASE_URL="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=";


    final List<String> NIFTY_COMPANY_SYMBOLS = Stream.of("ABB",
        "ASHOKLEY",
        "BAJFINANCE",
        "BAJAJFINSV",
        "BEL",
        "BHEL",
        "BRITANNIA",
        "CADILAHC",
        "COLPAL",
        "CONCOR",
        "CUMMINSIND",
        "DLF",
        "DABUR",
        "DIVISLAB",
        "EMAMILTD",
        "GSKCONS",
        "GLAXO",
        "GLENMARK",
        "GODREJCP",
        "HAVELLS",
        "HINDPETRO",
        "HINDZINC",
        "ICICIPRULI",
        "IDEA",
        "INDIGO",
        "JSWSTEEL",
        "LICHSGFIN",
        "MARICO",
        "MOTHERSUMI",
        "NHPC",
        "NMDC",
        "OIL",
        "OFSS",
        "PETRONET",
        "PIDILITIND",
        "PEL",
        "PFC",
        "PGHH",
        "PNB",
        "RECLTD",
        "SHREECEM",
        "SRTRANSFIN",
        "SIEMENS",
        "SAIL",
        "TITAN",
        "TORNTPHARM",
        "UPL",
        "UBL",
        "MCDOWELL-N",
        "VEDL").collect(
        Collectors.toList());
    final List<String> NIFTY_COMPANY_NAMES = Stream.of("ABB India Ltd.",
        "Ashok Leyland Ltd.",
        "Bajaj Finance Ltd.",
        "Bajaj Finserv Ltd.",
        "Bharat Electronics Ltd.",
        "Bharat Heavy Electricals Ltd.",
        "Britannia Industries Ltd.",
        "Cadila Healthcare Ltd.",
        "Colgate Palmolive (India) Ltd.",
        "Container Corporation of India Ltd.",
        "Cummins India Ltd.",
        "DLF Ltd.",
        "Dabur India Ltd.",
        "Divi's Laboratories Ltd.",
        "Emami Ltd.",
        "GlaxoSmithkline Consumer Healthcare Ltd.",
        "Glaxosmithkline Pharmaceuticals Ltd.",
        "Glenmark Pharmaceuticals Ltd.",
        "Godrej Consumer Products Ltd.",
        "Havells India Ltd.",
        "Hindustan Petroleum Corporation Ltd.",
        "Hindustan Zinc Ltd.",
        "ICICI Prudential Life Insurance Company Ltd.",
        "Idea Cellular Ltd.",
        "InterGlobe Aviation Ltd.",
        "JSW Steel Ltd.",
        "LIC Housing Finance Ltd.",
        "Marico Ltd.",
        "Motherson Sumi Systems Ltd.",
        "NHPC Ltd.",
        "NMDC Ltd.",
        "Oil India Ltd.",
        "Oracle Financial Services Software Ltd.",
        "Petronet LNG Ltd.",
        "Pidilite Industries Ltd.",
        "Piramal Enterprises Ltd.",
        "Power Finance Corporation Ltd.",
        "Procter & Gamble Hygiene & Health Care Ltd.",
        "Punjab National Bank ",
        "Rural Electrification Corporation Ltd.",
        "Shree Cement Ltd.",
        "Shriram Transport Finance Co Ltd.",
        "Siemens Ltd.",
        "Steel Authority of India Ltd.",
        "Titan Company Ltd.",
        "Torrent Pharmaceuticals Ltd.",
        "UPL Ltd.",
        "United Breweries Ltd.",
        "United Spirits Ltd.",
        "Vedanta Ltd.").collect(Collectors.toList());


    }
