package com.adaur.Crawler.repository;

import com.adaur.Crawler.rowMappers.DeveloperRowMapper;
import com.adaur.Crawler.services.Area;
import com.adaur.Crawler.services.County;
import com.adaur.Crawler.services.Developer;
import com.adaur.Crawler.services.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CrawlerRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * Add new developer to the data base.
     * @param developer
     */
    public void addDeveloper(Developer developer) {
        String sql = "INSERT INTO developer (developer_name) " + "VALUES (:developer_name)";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("developer_name", developer.getDeveloperName());
        template.update(sql, paraMap);
    }

    /**
     * Get list of Developers from database
     * @return list of Developers
     */

    public List<Developer> getAllDevelopers() {
        String sql = "SELECT * FROM developer ORDER BY id";
        return template.query(sql, new HashMap<>(), new DeveloperRowMapper());
    }

    /**
     * Check developer ID from database
     * @param developerName
     * @return integer Developer ID
     */

    public Integer getDeveloperId(String developerName) {
        String sql = "SELECT id FROM developer WHERE developer_name = :developer_name";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("developer_name", developerName);
        return template.queryForObject(sql, paraMap, Integer.class);
    }

    /**
     * Check county ID from database
     * @param countyName
     * @return integer County ID
     */

    public Integer getCountyId(String countyName) {
        String sql = "SELECT id FROM county WHERE county_name = :county_name";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("county_name", countyName);
        return template.queryForObject(sql, paraMap, Integer.class);
    }

    /**
     * Add area to the Database. Double entry not allowed.
     * @param area
     */

    public void addArea (Area area) {
        String sql = "INSERT INTO area (area_name, county_id) VALUES (:area_name, :county_id)";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("area_name", area.getAreaName());
        paraMap.put("county_id", area.getCountyId());
        template.update(sql, paraMap);
    }

    /**
     * Add county to the table. Double entry not allowed.
     * @param county
     */

    public void addCounty (County county) {
        String sql = "INSERT INTO county (county_name) VALUES (:county_name)";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("county_name", county.getCountyName());
        template.update(sql, paraMap);
    }

    public void addUnitToDB (Unit unit) {
        String sql = "INSERT INTO unit_info (unit_number, unit_size, unit_balcony_size," +
                "unit_current_price, unit_construction_year, unit_scan_date, unit_status_id," +
                "project_id, unit_category_id, unit_type_id, unit_url, unit_rooms, unit_floor) " +
                "VALUES (:unit_number, :unit_size, :unit_balcony_size," +
                ":unit_current_price, :unit_construction_year, :Unit_scan_date, :unit_status_id," +
                ":project_id, :unit_category_id, :unit_type_id, :unit_url, :unit_rooms, :unit_floor)";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("unit_number", unit.getUnitNumber());
        paraMap.put("unit_size", unit.getUnitSize());
        paraMap.put("unit_balcony_size", unit.getUnitBalconySize());
        paraMap.put("unit_current_price", unit.getUnitPrice());
        paraMap.put("unit_construction_year", unit.getUnitConstructionYear());
        paraMap.put("unit_scan_date", unit.getUnitScanTime());
        paraMap.put("unit_status_id", unit.getUnitStatusId());
        paraMap.put("project_id", unit.getUnitProjectId());
        paraMap.put("unit_category_id", unit.getUnitCategoryId());
        paraMap.put("unit_type_id", unit.getUnitTypeId());
        paraMap.put("unit_url", unit.getUnitUrl());
        paraMap.put("unit_rooms", unit.getUnitRooms());
        paraMap.put("unit_floor", unit.getUnitFloor());
        template.update(sql, paraMap);
    }

}


