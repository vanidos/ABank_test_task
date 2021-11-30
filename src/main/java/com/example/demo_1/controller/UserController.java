package com.example.demo_1.controller;

import com.example.demo_1.interfaces.GIF;
import com.example.demo_1.interfaces.Rates;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping("/getRates")
    public RedirectView checkCurrencyChanges(@RequestParam("code") String code) {

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String previousDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Rates rates = Feign.builder()
                .decoder(new GsonDecoder())
                .target(Rates.class, "https://openexchangerates.org/api");

        Double todayRate = (Double) ((Map) rates.getCurrency(currentDate, code).get("rates")).get(code);
        Double yesterdayRate = (Double) ((Map) rates.getCurrency(previousDate, code).get("rates")).get(code);

        GIF gif = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GIF.class, "https://api.giphy.com/v1/gifs");

        String gifURL;

        if (todayRate > yesterdayRate) {
            gifURL = (String) ((Map) ((List) gif.getGif("rich").get("data")).get(0)).get("embed_url");

        } else {
            gifURL = (String) ((Map) ((List) gif.getGif("broke").get("data")).get(0)).get("embed_url");
        }

        RedirectView redirectView = new RedirectView();

        redirectView.setUrl(gifURL);

        return redirectView;
    }
}
