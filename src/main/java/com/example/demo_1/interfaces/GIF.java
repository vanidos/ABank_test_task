package com.example.demo_1.interfaces;

import feign.Param;
import feign.RequestLine;

import java.util.Map;

public interface GIF {

    @RequestLine("GET /search?api_key=TSvg4x5EC1deSpxqm5U2OJV6R4Sng8Xd&q={status}")
    public Map getGif(@Param("status") String status);

}
