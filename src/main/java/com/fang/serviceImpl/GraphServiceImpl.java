package com.fang.serviceImpl;

import com.fang.model.City;
import com.fang.model.Graph;
import com.fang.service.DisService;
import com.fang.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Fang Yi on 17-2-27.
 */
@Service
public class GraphServiceImpl implements GraphService {

    final int MAX = 99999;
    private final double RADIUS = 6378.137;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DisService disService;

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    @Override
    public double disCount(City city1, City city2) {
        double radLat1 = rad(city1.getLati());
        double radLat2 = rad(city2.getLati());
        double a = radLat1 - radLat2;
        double b = rad(city1.getLongi()) - rad(city2.getLongi());
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * RADIUS;
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    @Override
    public Graph createGraph(List<City> cities) {
        Graph graph = new Graph(cities.size());
        graph.setCities(cities);
        double[][] dis = new double[cities.size()][cities.size()];

        Iterator<City> iterator1 = cities.iterator();
        int iter1 = 0;

        while (iterator1.hasNext()) {
            Iterator<City> iterator2 = cities.iterator();
            City city1 = iterator1.next();
            int iter2 = 0;
            while (iterator2.hasNext()) {
                dis[iter1][iter2] = disCount(city1, iterator2.next());
                iter2++;
            }
            iter1++;
        }
        graph.setDistances(dis);
        return graph;
    }

    @Override
    public Graph findCore(Graph graph) {
        double[][] dis = graph.getDistances();
        double[] rank = new double[graph.getNumber()];
        int core = 0;

        for (int x = 0; x < graph.getNumber(); x++) {
            double sum = 0;
            for (int y = 0; y < graph.getNumber(); y++) {
                sum += dis[x][y];
            }
            rank[x] = sum;
        }

        for (int x = 1; x < graph.getNumber(); x++) {
            if (rank[x] < rank[core]) {
                core = x;
            }
        }
        graph.setCore(core);

        return graph;
    }

    @Override
    public Graph prim(Graph graph) {
        int count = 0;
        int key = 0;
        double[][] dis = graph.getDistances();
        double[][] record = new double[3][graph.getNumber()];

        for (int n = 0; n < graph.getNumber(); n++) {
            record[0][n] = dis[0][n];
            record[1][n] = MAX;
            record[1][0] = -1;
            record[2][n] = 0;
        }


        while (count < graph.getNumber()) {
            for (int n = 0; n < graph.getNumber(); n++) {
                if (record[1][n] == MAX) {
                    if (record[0][n] > dis[key][n]) {
                        record[0][n] = dis[key][n];
                        record[2][n] = key;
                    }
                }
            }

            int flag = 1;
            int minkey = 0;

            for (int n = 0; n < graph.getNumber(); n++) {
                if (record[1][n] == MAX && flag == 1) {
                    minkey = n;
                    flag = 0;
                    continue;
                }

                if (record[1][n] == MAX) {
                    if (record[0][n] < record[0][minkey]) {
                        minkey = n;
                    }
                }
            }

            record[1][minkey] = record[2][minkey];

            key = minkey;
            count++;
            record[1][0] = -1;
        }
        graph.setPrim(record);

        return graph;
    }
}
