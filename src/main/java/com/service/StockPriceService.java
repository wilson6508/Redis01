package com.service;

import com.pojo.dto.stockinfo.response.Msg;

public interface StockPriceService {
    Msg getSingle(String stockId);
}
