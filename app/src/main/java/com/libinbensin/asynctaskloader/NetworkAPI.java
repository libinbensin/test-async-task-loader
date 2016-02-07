package com.libinbensin.asynctaskloader;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libinsalal on 2/7/16.
 */
public class NetworkAPI {

    private static final String DEBUG_TAG = NetworkAPI.class.getSimpleName();

    private RestTemplate mRestTemplate;

    NetworkAPI(){
        // Create a new RestTemplate instance
        mRestTemplate = new RestTemplate();

        // Add the String message converter
        mRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        mRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }


    public Book getBooks(){
        String url = "http://api.libinbensin/books";
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("name" , "libin");
        // Make the HTTP GET request, marshaling the response to a String
        Book result = mRestTemplate.getForObject(url, Book.class, urlVariables);
        return result;
    }

}
