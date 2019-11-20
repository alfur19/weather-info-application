package com.example.web.openweathermap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Scope("singleton")
public class OpenWeatherMapRepository {

    private HashMap<String, OpenWeatherMapCityModel> localRepository = new HashMap<>();

    public OpenWeatherMapRepository() {
        InputStream inputStream = getClass().getResourceAsStream("/static/city.list.json");
        String result = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
        ObjectMapper objectMapper = new ObjectMapper();
        List<OpenWeatherMapCityModel> list = null;
        try {
            list = objectMapper.readValue(result, new TypeReference<List<OpenWeatherMapCityModel>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.forEach(item -> {
            localRepository.put(item.getName().toLowerCase(), item);
        });
    }

    public HashMap<String, OpenWeatherMapCityModel> getLocalRepository() {
        return localRepository;
    }
}
