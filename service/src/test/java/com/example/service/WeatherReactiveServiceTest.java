package com.example.service;

import com.example.common.dto.WeatherForecastModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
@AutoConfigureWebTestClient
public class WeatherReactiveServiceTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testForecastStream() {
        List<WeatherForecastModel> results = webClient
                .get().uri("/forecast/stream")
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .exchange()
                .expectStatus().isOk()
                .returnResult(WeatherForecastModel.class)
                .getResponseBody()
                .take(10)
                .collectList()
                .block();

        results.forEach(x -> System.out.println(x));
        assertEquals(10, results.size());
    }
}
