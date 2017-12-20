package com.fang.service;

import com.fang.model.City;

import java.util.List;

/**
 * Created by Fang Yi on 17-2-19.
 */
public interface DisService {

    List<String> getCities();

    City getCity(String string);

    void chooseCity(int id);

    List getChosenId();

    List<City> getChosenCities(List chosenId);

    void reset();
}
