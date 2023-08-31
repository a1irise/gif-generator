package com.a1irise.gifgenerator.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "openexchangerates", url = "${openexchangerates.url}")
public interface OpenExchangeRatesFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/historical/{date}.json")
    String getHistoricalRates(@PathVariable String date, @RequestParam("app_id") String appId);

    @RequestMapping(method = RequestMethod.GET, value = "/currencies.json")
    String getAvailableCurrencies(@RequestParam("app_id") String appId);
}
