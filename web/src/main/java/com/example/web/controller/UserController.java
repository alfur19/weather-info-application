package com.example.web.controller;

import com.example.common.dto.WeatherModel;
import com.example.web.bean.UserInfo;
import com.example.web.openweathermap.OpenWeatherMapCityModel;
import com.example.web.openweathermap.OpenWeatherMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Controller
public class UserController {

    @Autowired
    private OpenWeatherMapRepository openWeatherMapRepository;

    @Autowired
    private UserInfo userInfo;


    @Value("${webservice.address}")
    private String webserviceAddress;


    @GetMapping("/panel")
    public String panel(Model model) {
        model.addAttribute("weatherModel", callForecastService(openWeatherMapRepository.getLocalRepository().get("helsinki")));
        return "panel";
    }

    @GetMapping("/forecast")
    public String search(@RequestParam(name="city", required=false, defaultValue="Helsinki") String city, Model model) {
        OpenWeatherMapCityModel cityModel = openWeatherMapRepository.getLocalRepository().get(city.toLowerCase());
        if(cityModel != null){
            model.addAttribute("weatherModel", callForecastService(cityModel));
        }
        else{
            model.addAttribute("city", city);
        }
        return "panel";
    }

    @GetMapping("/saveFavorite")
    public String saveFavorite(@RequestParam(name="city") String city, Model model) {
        OpenWeatherMapCityModel cityModel = openWeatherMapRepository.getLocalRepository().get(city.toLowerCase());
        userInfo.getFavoriteCities().add(cityModel);
        return "listFavorites";
    }

    @GetMapping("/removeFavorite")
    public String removeFavorite(@RequestParam(name="city") String city, Model model) {
        OpenWeatherMapCityModel cityModel = openWeatherMapRepository.getLocalRepository().get(city.toLowerCase());
        userInfo.getFavoriteCities().remove(cityModel);
        return "listFavorites";
    }

    @GetMapping("/listFavorites")
    public String listFavorites() {
        return "listFavorites";
    }

    private WeatherModel callForecastService(OpenWeatherMapCityModel cityModel){
        String serviceTargetTemplate = "{0}/forecast?id={1}";
        String serviceTarget = MessageFormat.format(serviceTargetTemplate, webserviceAddress, String.valueOf(cityModel.getId()));
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(serviceTarget, WeatherModel.class);
    }

}
