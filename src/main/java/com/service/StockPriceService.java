package com.service;

import com.pojo.dto.Msg;

public interface StockPriceService {
    Msg getSingle(String stockId);
    boolean checkNormalUser(String ip);
}
