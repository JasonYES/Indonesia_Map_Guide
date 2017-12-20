package com.fang.service;

import com.fang.model.City;
import com.fang.model.Graph;

import java.util.List;

/**
 * Created by Fang Yi on 17-2-27.
 */
public interface GraphService {

    double disCount(City city1, City city2);

    Graph createGraph(List<City> cities);

    Graph findCore(Graph graph);

    Graph prim(Graph graph);
}
