package com.jason.service;

import com.jason.entity.Bike;
import org.springframework.data.geo.GeoResult;

import java.util.List;

public interface BikeService {

    public void save(Bike bike);

    List<Bike> findAll();

    List<GeoResult<Bike>> findNear(double longitude, double latitude);
}
