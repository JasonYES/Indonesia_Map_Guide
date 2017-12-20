package com.fang.model;

import java.util.List;

/**
 * Created by Fang Yi on 17-2-12.
 */
public class Graph {

    private final int MAX = 50;
    private List<City> cities;
    private double[][] distances;
    private double[][] prim;
    private int number;
    private int core;

    public Graph(int number) {
        this.number = number;
        distances = new double[number][number];
    }

    public int getMAX() {
        return MAX;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public double[][] getDistances() {
        return distances;
    }

    public void setDistances(double[][] distances) {
        this.distances = distances;
    }

    public double[][] getPrim() {
        return prim;
    }

    public void setPrim(double[][] prim) {
        this.prim = prim;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
    }
}
