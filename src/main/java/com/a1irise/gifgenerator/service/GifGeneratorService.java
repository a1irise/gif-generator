package com.a1irise.gifgenerator.service;

import com.a1irise.gifgenerator.config.GiphyConfig;
import com.a1irise.gifgenerator.config.OpenExchangeRatesConfig;
import com.a1irise.gifgenerator.utils.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class GifGeneratorService {

    private OpenExchangeRatesConfig openExchangeRatesConfig;
    private GiphyConfig giphyConfig;

    private OpenExchangeRatesFeign openExchangeRatesFeign;
    private GiphyFeign giphyFeign;

    @Autowired
    public GifGeneratorService(
            OpenExchangeRatesConfig openExchangeRatesConfig,
            GiphyConfig giphyConfig,
            OpenExchangeRatesFeign openExchangeRatesFeign,
            GiphyFeign giphyFeign) {
        this.openExchangeRatesConfig = openExchangeRatesConfig;
        this.giphyConfig = giphyConfig;
        this.openExchangeRatesFeign = openExchangeRatesFeign;
        this.giphyFeign = giphyFeign;
    }

    public String getGif(String currency) throws JsonProcessingException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Instant now = Instant.now();
        String today = dateFormat.format(Date.from(now));
        String yesterday = dateFormat.format(Date.from(now.minus(1, ChronoUnit.DAYS)));

        double exchangeRateToday = Json.parse(
                openExchangeRatesFeign.getHistoricalRates(today, openExchangeRatesConfig.getAppId())
        ).get("rates").get(currency).asDouble();
        double exchangeRateYesterday = Json.parse(
                openExchangeRatesFeign.getHistoricalRates(yesterday, openExchangeRatesConfig.getAppId())
        ).get("rates").get(currency).asDouble();

        if (exchangeRateToday < exchangeRateYesterday) {
            return Json.parse(
                    giphyFeign.getRandomGif(giphyConfig.getApiKey(),"rich")
            ).get("data").get("embed_url").asText();
        } else {
            return Json.parse(
                    giphyFeign.getRandomGif(giphyConfig.getApiKey(), "broke")
            ).get("data").get("embed_url").asText();
        }
    }

    public List<String> getAvailableCurrencies() throws JsonProcessingException {
        Iterator<String> iterator = Json.parse(
                openExchangeRatesFeign.getAvailableCurrencies(openExchangeRatesConfig.getAppId())
        ).fieldNames();
        List<String> currencies = new ArrayList<>();
        iterator.forEachRemaining(currencies::add);
        return currencies;
    }
}
