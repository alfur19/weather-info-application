package com.example.service.impl;

import com.example.common.dto.WeatherForecastModel;
import com.example.common.dto.WeatherModel;
import com.example.service.WeatherService;
import com.example.service.openweathermap.jaxb.ObjectFactory;
import com.example.service.openweathermap.jaxb.WeatherdataType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class OpenWeatherMapServiceImpl implements WeatherService {

    private static final SimpleDateFormat periodDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //2019-04-19T09:00:00

    @Value("${openWeatherMapAppID}")
    private String openWeatherMapAppID;

    @Value("${serviceTargetTemplate}")
    private String serviceTargetUrlTemplate;

    @RequestMapping("/forecast")
    public WeatherModel forecast(@RequestParam(value="id", defaultValue="658226") Integer cityId) {
        //Make service URL using the template and input parameter for the city
        String serviceTargetUrl = MessageFormat.format(serviceTargetUrlTemplate, String.valueOf(cityId), openWeatherMapAppID);
        //Create a rest-template and invoke the service using the URL
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(serviceTargetUrl, String.class);
        //Parse and convert the XML response from OpenWeatherMapAPI to JAXB data model
        StringReader xmlResponseReader = new StringReader(xmlResponse);
        WeatherdataType jaxbWeatherDataModel = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller jaxbContextUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbWeatherDataModel = (WeatherdataType) ((JAXBElement)(jaxbContextUnmarshaller.unmarshal(xmlResponseReader))).getValue();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //Data conversion from JAXB object to WeatherModel DTO
        WeatherModel weatherModel = fillWeatherModel(jaxbWeatherDataModel);
        weatherModel.setCityId(cityId);
        return weatherModel;
    }

    @GetMapping(path = "/forecast/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<WeatherForecastModel> forecastStream(@RequestParam(value="id", defaultValue="658226") Integer cityId) {
        WeatherModel weatherModel = forecast(cityId);
        return Flux.fromIterable(weatherModel.getForecasts()).delayElements(Duration.ofSeconds(1));
    }

    private static WeatherModel fillWeatherModel(WeatherdataType jaxbModel) {
        WeatherModel weatherModel = new WeatherModel();
        //Fill header data
        if(jaxbModel.getLocation() != null && jaxbModel.getLocation().getContent() != null){
            jaxbModel.getLocation().getContent().forEach(item -> {
                JAXBElement jaxbElement = (JAXBElement) item;
                if("name".equalsIgnoreCase(jaxbElement.getName().toString())){
                    weatherModel.setCity(jaxbElement.getValue().toString());
                }
                else if("country".equalsIgnoreCase(jaxbElement.getName().toString())){
                    weatherModel.setCountry(jaxbElement.getValue().toString());
                }
            });
        }
        //Fill forecast data
        List<WeatherForecastModel> forecasts = new ArrayList<>();
        if(jaxbModel.getForecast() != null && jaxbModel.getForecast().getTime() !=  null){
            jaxbModel.getForecast().getTime().forEach(item -> {
                        WeatherForecastModel weatherForecastModel = new WeatherForecastModel();
                        try {
                            weatherForecastModel.setStartPeriod(periodDateFormat.parse(item.getFrom()));
                            weatherForecastModel.setEndPeriod(periodDateFormat.parse(item.getTo()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        weatherForecastModel.setCelsiusTemperature(Double.parseDouble(item.getTemperature().getValueAttribute()));
                        weatherForecastModel.setCelsiusTemperatureMax(Double.parseDouble(item.getTemperature().getMax()));
                        weatherForecastModel.setCelsiusTemperatureMin(Double.parseDouble(item.getTemperature().getMin()));
                        weatherForecastModel.setHumidityPercentage(Integer.valueOf(item.getHumidity().getValueAttribute()));
                        forecasts.add(weatherForecastModel);
                    }
            );
        }
        //Sort forecast objects by start time
        forecasts.sort(Comparator.comparing(WeatherForecastModel::getStartPeriod));
        weatherModel.setForecasts(forecasts);
        return weatherModel;
    }
}
