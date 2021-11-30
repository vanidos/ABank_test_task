package com.example.demo_1.interfaces;

import feign.Param;
import feign.RequestLine;

import java.util.Map;

public interface Rates {

    @RequestLine("GET /historical/{date}.json?app_id=2ea366e1dccf4c849809ac8cec1f876b&symbols={code}")
    public Map getCurrency(@Param("date") String date, @Param("code") String code);
}
