package com.a1irise.gifgenerator.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "giphy", url = "${giphy.url}")
public interface GiphyFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/random")
    String getRandomGif(@RequestParam("api_key") String apiKey, @RequestParam("tag") String tag);
}
