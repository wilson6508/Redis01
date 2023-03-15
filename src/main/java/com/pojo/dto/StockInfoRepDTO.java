package com.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class StockInfoRepDTO {
    private String rtmessage;
    private List<Msg> msgArray;
}
