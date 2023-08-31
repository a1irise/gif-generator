package com.a1irise.gifgenerator.controller;

import com.a1irise.gifgenerator.service.GifGeneratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GifGeneratorController {

    private GifGeneratorService gifGeneratorService;

    @Autowired
    public GifGeneratorController(GifGeneratorService gifGeneratorService) {
        this.gifGeneratorService = gifGeneratorService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/get-gif/{currency}")
    public String getGif(@PathVariable String currency) throws JsonProcessingException {
        return gifGeneratorService.getGif(currency);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/available-currencies")
    public List<String> getAvailableCurrencies() throws JsonProcessingException {
        return gifGeneratorService.getAvailableCurrencies();
    }
}
