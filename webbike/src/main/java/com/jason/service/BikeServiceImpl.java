package com.jason.service;

import com.jason.entity.Bike;
import com.jason.mapper.BikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeMapper bikeMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Bike bike) {
        //bikeMapper.save(bike);
        mongoTemplate.save(bike);
    }

    @Override
    public List<GeoResult<Bike>> findNear(double longitude, double latitude) {
        //传入经度、纬度、长度单位
        NearQuery nearQuery = NearQuery.near(longitude,latitude, Metrics.KILOMETERS);
        //查找附近多少米的车辆
        nearQuery.maxDistance(0.2);
        //调用NearQuery的query方法，指定查询条件
        GeoResults<Bike> geoResults = mongoTemplate.geoNear(nearQuery.query(new Query().addCriteria(Criteria.where("status").is(0)).limit(10)), Bike.class);
        return geoResults.getContent();
    }

    @Override
    public List<Bike> findAll() {
        //return bikeMapper.findAll();
        return mongoTemplate.findAll(Bike.class);
    }
}
