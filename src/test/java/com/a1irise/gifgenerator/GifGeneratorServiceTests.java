package com.a1irise.gifgenerator;

import com.a1irise.gifgenerator.config.GiphyConfig;
import com.a1irise.gifgenerator.config.OpenExchangeRatesConfig;
import com.a1irise.gifgenerator.service.GifGeneratorService;
import com.a1irise.gifgenerator.service.GiphyFeign;
import com.a1irise.gifgenerator.service.OpenExchangeRatesFeign;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GifGeneratorServiceTests {

	@Autowired
	GifGeneratorService gifGeneratorService;

	@MockBean
	GiphyFeign giphyFeign;
	@MockBean
	OpenExchangeRatesFeign openExchangeRatesFeign;
	@MockBean
	GiphyConfig giphyConfig;
	@MockBean
	OpenExchangeRatesConfig openExchangeRatesConfig;

	@Test
	void shouldGetRichGifWhenRateDecreased() throws JsonProcessingException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Instant now = Instant.now();
		String today = dateFormat.format(Date.from(now));
		String yesterday = dateFormat.format(Date.from(now.minus(1, ChronoUnit.DAYS)));

		String jsonToday = "{\"rates\": {\"RUB\": \"80\"} }";
		String jsonYesterday = "{\"rates\": {\"RUB\": \"90\"} }";
		Mockito.when(openExchangeRatesFeign.getHistoricalRates(today, openExchangeRatesConfig.getAppId()))
				.thenReturn(jsonToday);
		Mockito.when(openExchangeRatesFeign.getHistoricalRates(yesterday, openExchangeRatesConfig.getAppId()))
				.thenReturn(jsonYesterday);

		String expected = "https://giphy.com/embed/5yLgocwEzOGUTsT9mKs";

		String jsonGif = "{\"data\": {\"embed_url\": \"" + expected + "\"} }";
		Mockito.when(giphyFeign.getRandomGif(giphyConfig.getApiKey(),"rich")).thenReturn(jsonGif);

		String actual = gifGeneratorService.getGif("RUB");
		assertEquals(expected, actual);
	}

	@Test
	void shouldGetBrokeGifWhenRateIncreased() throws JsonProcessingException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Instant now = Instant.now();
		String today = dateFormat.format(Date.from(now));
		String yesterday = dateFormat.format(Date.from(now.minus(1, ChronoUnit.DAYS)));

		String jsonToday = "{\"rates\": {\"RUB\": \"90\"} }";
		String jsonYesterday = "{\"rates\": {\"RUB\": \"80\"} }";
		Mockito.when(openExchangeRatesFeign.getHistoricalRates(today, openExchangeRatesConfig.getAppId()))
				.thenReturn(jsonToday);
		Mockito.when(openExchangeRatesFeign.getHistoricalRates(yesterday, openExchangeRatesConfig.getAppId()))
				.thenReturn(jsonYesterday);

		String expected = "https://giphy.com/embed/gGF8ztwUbYIu9RVIFL";

		String jsonGif = "{\"data\": {\"embed_url\": \"" + expected + "\"} }";
		Mockito.when(giphyFeign.getRandomGif(giphyConfig.getApiKey(),"broke")).thenReturn(jsonGif);

		String actual = gifGeneratorService.getGif("RUB");
		assertEquals(expected, actual);
	}

	@Test
	void shouldGetAvailableCurrencies() throws JsonProcessingException {
		String json = "{\"RUB\": \"Russian Ruble\", \"EUR\": \"Euro\"}";
		Mockito.when(openExchangeRatesFeign.getAvailableCurrencies(openExchangeRatesConfig.getAppId())).thenReturn(json);

		List<String> expected = List.of("RUB", "EUR");
		List<String> actual = gifGeneratorService.getAvailableCurrencies();
		assertEquals(expected, actual);
	}
}
