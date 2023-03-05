package com.controller;

import com.pojo.dto.stockinfo.response.Msg;
import com.service.StockPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/stockPrice")
public class StockPriceController {

    @Resource
    private StockPriceService stockPriceService;

    // http://localhost:7500/stockPrice/getSingle?stockId=2330
    @GetMapping("/getSingle")
    public Msg getSingle(String stockId) {
        return stockPriceService.getSingle(stockId);
    }

}
