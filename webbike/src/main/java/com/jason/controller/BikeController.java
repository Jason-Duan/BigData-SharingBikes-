package com.jason.controller;

import com.jason.service.BikeService;
import com.jason.entity.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @PostMapping("/bike")
    @ResponseBody
    public String add(Bike bike) {
        bikeService.save(bike);
        //System.out.println(bike.getLatitude());
        return "success";
    }

    @GetMapping("/bikes")
    @ResponseBody
    public List<GeoResult<Bike>> findNear(double longitude, double latitude) {
        List<GeoResult<Bike>> bikes = bikeService.findNear(longitude,latitude);
        return bikes;
    }
}
