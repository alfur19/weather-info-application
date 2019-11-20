package com.example.web.bean;

import com.example.web.openweathermap.OpenWeatherMapCityModel;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo implements Serializable {

    private Set<OpenWeatherMapCityModel> favoriteCities = new HashSet<>();

    public UserInfo() {
    }

    public Set<OpenWeatherMapCityModel> getFavoriteCities() {
        return favoriteCities;
    }

    public void setFavoriteCities(Set<OpenWeatherMapCityModel> favoriteCities) {
        this.favoriteCities = favoriteCities;
    }

}
