package com.example.service;

import com.example.utils.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

/**
 * @author mchidambaranathan 4/23/2017
 */
@Component
public class RestService {

    public JsonNode getSunRaiseDetails(final Boolean isTomorrow){
        final Calendar instance = Calendar.getInstance();
        if(isTomorrow){
            instance.add(Calendar.DATE,1);
        }
        return getRestTemplate().getForObject(
            Constants.SUNSET_SUNRAISE_URL +instance.getTime(),
            JsonNode.class);
    }

    @Bean
    private RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
