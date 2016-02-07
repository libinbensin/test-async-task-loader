package com.libinbensin.asynctaskloader;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(30000); // 30 seconds
        factory.setConnectTimeout(30000); // 30 seconds
        // Create a new RestTemplate instance
        mRestTemplate = new RestTemplate(factory);
        // Add the String message converter
        mRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        mRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }


    public Book getBooks(){

        try {
            String url = "http://api.libinbensin.com/books";
            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("name" , "libin");
            // Make the HTTP GET request, marshaling the response to a String
            return mRestTemplate.getForObject(url, Book.class, urlVariables);
        }catch (Exception e) {
            return null;
        }
    }

}
