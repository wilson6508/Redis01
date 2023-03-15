package com.utils.api;

import com.google.gson.Gson;
import com.pojo.dto.StockInfoRepDTO;
import com.pojo.prop.ApiUrlProp;
import com.utils.constants.TimeConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableRetry
public class StockApiUtils {

    @Resource
    private ApiUrlProp apiUrlProp;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Retryable(include = {IllegalStateException.class}, maxAttempts = 2, backoff = @Backoff(value = 9000))
    public StockInfoRepDTO getStockInfoRepDTO(String stockId) {
        String apiUrl = String.format(apiUrlProp.getMultiStock(), stockId);
        int timeOut = TimeConstants.MIN_TO_MILES;
        String apiRep = ApiUtils.getForEntity(String.class, apiUrl, timeOut, timeOut);
        try {
            return new Gson().fromJson(apiRep, StockInfoRepDTO.class);
        } catch (IllegalStateException e) {
            stringRedisTemplate.opsForValue().increment("retry");
            throw e;
        }
    }

}
