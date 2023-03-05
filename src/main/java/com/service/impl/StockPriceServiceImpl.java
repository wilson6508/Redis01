package com.service.impl;

import com.pojo.dto.stockinfo.response.Msg;
import com.pojo.dto.stockinfo.response.StockInfoRepDTO;
import com.service.StockPriceService;
import com.utils.DeserializationUtils;
import com.utils.api.StockApiUtils;
import com.utils.constants.CacheConstants;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class StockPriceServiceImpl implements StockPriceService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DeserializationUtils deserializationUtils;
    @Resource
    private StockApiUtils stockApiUtils;

    @Override
    public Msg getSingle(String stockId) {
        // 攔截器先驗證token{ ip:XXX, expireTime:XXX }
        // 先查緩存
        String cache = stringRedisTemplate.opsForValue().get(CacheConstants.STOCK_PRICE_KEY + stockId);
        if (!Strings.isEmpty(cache)) {
            if (cache.equals("null")) {
                return null;
            }
            return deserializationUtils.readObject(cache, Msg.class);
        }
        // 緩存中沒有
        // 攔截器取token 去redis看這1分鐘的查詢次數 超過1000 return
        // setIfAbsent increment
        // 緩存中沒有 打API取資訊
        StockInfoRepDTO stockInfoRepDTO = stockApiUtils.getStockInfoRepDTO(stockId);
        List<Msg> msgArray = stockInfoRepDTO.getMsgArray();
        // 緩存空對象 避免高併發緩存穿透
        Msg msg = CollectionUtils.isEmpty(msgArray) ? null : msgArray.get(0);
        // 存入緩存
        Executors.newSingleThreadExecutor().submit(() -> {
            String json = deserializationUtils.serialize(msg);
            stringRedisTemplate.opsForValue().set(CacheConstants.STOCK_PRICE_KEY + stockId, json, 5, TimeUnit.MINUTES);
        });
        return msg;
    }

}
