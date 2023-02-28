package com.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ApiUtils {

    public static <E> E getForEntity(Class<E> clazz, String apiUrl, int connectTimeout, int readTimeout) {
        RestTemplate restTemplate = getRestTemplate(connectTimeout, readTimeout);
        ResponseEntity<E> result = restTemplate.getForEntity(apiUrl, clazz);
        return result.getBody();
    }

    public static RestTemplate getRestTemplate(int connectTimeout, int readTimeout) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return new RestTemplate(requestFactory);
    }

}
