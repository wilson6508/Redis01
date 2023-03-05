package com.utils;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

@Component
@EnableCaching
public class CacheUtils {

    @CachePut(value = "stockInfo", key = "#stockId")
    public String saveCache(String stockId, String apiRep) {
        return apiRep;
    }

    @Cacheable(value = "stockInfo", key = "#stockId")
    public String getCache(String stockId) {
        return null;
    }

}
