package com.jason.mapper;

import com.jason.entity.Bike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BikeMapper {

    public void save(Bike bike);

    List<Bike> findAll();
}
