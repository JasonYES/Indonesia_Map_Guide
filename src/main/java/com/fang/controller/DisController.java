package com.fang.controller;

import com.fang.model.City;
import com.fang.model.Graph;
import com.fang.service.DisService;
import com.fang.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fang Yi on 17-2-24.
 */

@Controller
public class DisController {

    private Graph graph;

    @Autowired
    private DisService disService;

    @Autowired
    private GraphService graphService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public  String index(){

        return "index";
    }

    @RequestMapping(value = "/cities", method = RequestMethod.POST)
    public @ResponseBody List<String> getCities(){
        List list = disService.getCities();

        return list;
    }

    @RequestMapping(value = "/coor", method = RequestMethod.POST)
    public @ResponseBody City getCities(@RequestParam String city){
        City city1= disService.getCity(city);
        disService.chooseCity(city1.getId());

        return city1;
    }

    @RequestMapping(value = "/navi", method = RequestMethod.GET)
    public @ResponseBody Graph getChosen(){

        List<Integer> list1 = disService.getChosenId();
        List<City> list2 = disService.getChosenCities(list1);
        Graph graph = graphService.createGraph(list2);
        graph = graphService.findCore(graph);
        graph = graphService.prim(graph);

        return graph;
    }

    @RequestMapping(value = "/dist", method = RequestMethod.GET)
    public @ResponseBody Graph getPrim(){

        List<Integer> list1 = disService.getChosenId();
        List<City> list2 = disService.getChosenCities(list1);
        Graph graph = graphService.createGraph(list2);
        graph = graphService.findCore(graph);
        graph = graphService.prim(graph);

        return graph;
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public @ResponseBody Graph getName(){

        List<Integer> list1 = disService.getChosenId();
        List<City> list2 = disService.getChosenCities(list1);
        Graph graph = graphService.createGraph(list2);

        return graph;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String reset(){
        disService.reset();

        return "redirect:/";
    }
}
