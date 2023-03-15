package com.pojo.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Msg {
    @SerializedName("c")
    private String stockId;
    @SerializedName("n")
    private String stockName;
    @SerializedName("z")
    private String nowPrice;
    @SerializedName("y")
    private String closePrice;
}
