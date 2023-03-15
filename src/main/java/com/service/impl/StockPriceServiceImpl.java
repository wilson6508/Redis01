package com.service.impl;

import com.pojo.dto.Msg;
import com.pojo.dto.StockInfoRepDTO;
import com.service.StockPriceService;
import com.utils.other.DeserializationUtils;
import com.utils.api.StockApiUtils;
import com.utils.constants.CacheConstants;
import com.utils.other.IpAddressUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class StockPriceServiceImpl implements StockPriceService {

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(20);
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DeserializationUtils deserializationUtils;
    @Resource
    private StockApiUtils stockApiUtils;

    @Override
    public Msg getSingle(String stockId) {
        // 避免非法用戶高併發查詢
        String ip = IpAddressUtils.getIp();
        System.out.println(ip);
        boolean isNormalUser = checkNormalUser(ip);
        if (!isNormalUser) {
            return null;
        }
        // 先查緩存
        String key = CacheConstants.STOCK_PRICE_KEY + stockId;
        String cache = stringRedisTemplate.opsForValue().get(key);
        if (!Strings.isEmpty(cache)) {
            if (cache.equals("null")) {
                return null;
            }
            return deserializationUtils.readObject(cache, Msg.class);
        }
        // 緩存中沒有 打API取資訊
        StockInfoRepDTO stockInfoRepDTO = stockApiUtils.getStockInfoRepDTO(stockId);
        List<Msg> msgArray = stockInfoRepDTO.getMsgArray();
        // 存入緩存 防止緩存穿透
        Msg msg = CollectionUtils.isEmpty(msgArray) ? null : msgArray.get(0);
        CACHE_REBUILD_EXECUTOR.submit(() -> {
            String json = deserializationUtils.serialize(msg);
            stringRedisTemplate.opsForValue().set(key, json, 50, TimeUnit.MINUTES);
        });
        return msg;
    }

    @Override
    public boolean checkNormalUser(String ip) {
        String key = CacheConstants.QUERY_COUNT_KEY + ip;
        stringRedisTemplate.opsForValue().setIfAbsent(key, "0", 1000, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().increment(key);
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value != null) {
            return Integer.parseInt(value) < 100;
        }
        return false;
    }

}
