package com.service;

import com.pojo.prop.ApiUrlProp;
import com.utils.api.ApiUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class TestService {

    @Resource
    private ApiUrlProp apiUrlProp;

    public String testOdd() {
        String apiUrl = String.format(apiUrlProp.getSingleOdd(), "2330");
        int timeOut = (int) TimeUnit.MINUTES.toMillis(1);
        return ApiUtils.getForEntity(String.class, apiUrl, timeOut, timeOut);
    }

    public String multiStock() {
        String apiUrl = String.format(apiUrlProp.getMultiStock(), "2330_2884_2886");
        int timeOut = (int) TimeUnit.MINUTES.toMillis(1);
        return ApiUtils.getForEntity(String.class, apiUrl, timeOut, timeOut);
    }

    public String multiOdd() {
        String apiUrl = String.format(apiUrlProp.getMultiOdd(), "2330_2884_2886");
        int timeOut = (int) TimeUnit.MINUTES.toMillis(1);
        return ApiUtils.getForEntity(String.class, apiUrl, timeOut, timeOut);
    }

}
