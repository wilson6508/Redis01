package com.pojo.dto.stockinfo.response;

import lombok.Data;

import java.util.List;

@Data
public class StockInfoRepDTO {
    private String rtmessage;
    private List<Msg> msgArray;
}
