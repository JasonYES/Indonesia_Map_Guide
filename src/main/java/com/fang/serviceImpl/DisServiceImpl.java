package com.fang.serviceImpl;

import com.fang.model.City;
import com.fang.service.DisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Fang Yi on 17-2-19.
 */
@Service
public class DisServiceImpl implements DisService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getCities() {
        String sql = "select name from cities";
        List list = jdbcTemplate.query(sql, new RowMapper() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                String name = resultSet.getString(1);
                return name;
            }
        });
        return list;
    }

    @Override
    public City getCity(String string) {
        String sql = "SELECT * FROM cities WHERE NAME='" + string + "'";
        List<City> list = jdbcTemplate.query(sql, new RowMapper() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                City city = new City();
                city.setId(resultSet.getInt(1));
                city.setName(resultSet.getString(2));
                city.setLongi(resultSet.getDouble(3));
                city.setLati(resultSet.getDouble(4));
                return city;
            }
        });
        return list.get(0);
    }

    @Override
    public void chooseCity(int id) {
        String sql = "insert into chosen values (" + id + ")";
        jdbcTemplate.update(sql);
    }

    @Override
    public List<Integer> getChosenId() {
        String sql = "Select * from chosen";
        List list = jdbcTemplate.query(sql, new RowMapper() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                int id = resultSet.getInt(1);
                return id;
            }
        });
        return list;
    }

    @Override
    public List<City> getChosenCities(List chosenId) {
        List outList = new ArrayList();
        Iterator<Integer> iterator = chosenId.iterator();
        while (iterator.hasNext()){
            String sql = "SELECT * FROM cities WHERE id='" + iterator.next() + "'";
            List<City> list = jdbcTemplate.query(sql, new RowMapper() {
                @Override
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    City city = new City();
                    city.setId(resultSet.getInt(1));
                    city.setName(resultSet.getString(2));
                    city.setLongi(resultSet.getDouble(3));
                    city.setLati(resultSet.getDouble(4));
                    return city;
                }
            });
            outList.addAll(list);
        }
        return outList;
    }

    @Override
    public void reset() {
        String sql = "delete from chosen";
        jdbcTemplate.update(sql);
    }
}
