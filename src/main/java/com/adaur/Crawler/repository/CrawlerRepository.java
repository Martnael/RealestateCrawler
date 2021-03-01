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

    /**
     * Insert unit to database
     * @param unit
     */

    public void addUnitToDB (Unit unit) {
        String sql = "INSERT INTO unit_info (unit_number, unit_size, unit_balcony_size," +
                "unit_current_price, unit_construction_year, unit_scan_date, unit_status_id," +
                "project_id, unit_category_id, unit_type_id, unit_url, unit_rooms, unit_floor, sqrm_price) " +
                "VALUES (:unit_number, :unit_size, :unit_balcony_size," +
                ":unit_current_price, :unit_construction_year, :unit_scan_date, :unit_status_id," +
                ":project_id, :unit_category_id, :unit_type_id, :unit_url, :unit_rooms, :unit_floor, :sqrm_price)";
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
        paraMap.put("sqrm_price", unit.getUnitSqrMPrice());
        template.update(sql, paraMap);
    }

    /**
     * Check do we have unit in Database.
     * On the basis of projectID, unit_number and unit_size. These information should be always same
     * @param unit
     * @return
     */
    public int unitCountInDatabase (Unit unit) {
        String sql = "SELECT COUNT(*) FROM unit_info WHERE unit_number = :unit_number and unit_size = :unit_size and project_id = :project_id";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("unit_number", unit.getUnitNumber());
        paraMap.put("unit_size", unit.getUnitSize());
        paraMap.put("project_id", unit.getUnitProjectId());
        return template.queryForObject(sql, paraMap, Integer.class);
    }

    /**
     * Check if the price of the unit is still same or it have been changed. It it is changed new insert have to made
     * to price_history table
     * @param unit
     * @return
     */
    public int checkUnitPriceChange(Unit unit) {
        String sql = "SELECT COUNT(*) FROM unit_price_history WHERE unit_id = :unit_id and unit_price = :unit_price";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("unit_id", unit.getUnitId());
        paraMap.put("unit_size", unit.getUnitPrice());
        return template.queryForObject(sql, paraMap, Integer.class);
    }

    /**
     * Insert unit_id and price to database. If price change occurred then new line is inserted.
     * @param unit
     */
    public void insertPriceToPriceHistoryTable (Unit unit) {
        String sql = "INSERT INTO unit_price_history (unit_price, price_date, unit_id) VALUES (:unit_price, :price_date, :unit_id)";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("unit_price", unit.getUnitPrice());
        paraMap.put("price_date", unit.getUnitScanTime());
        paraMap.put("unit_id", unit.getUnitId());
        template.update(sql, paraMap);
    }

    /**
     * Get unit ID with based on project_id and unit_number
     * @param unit
     * @return
     */

    public int getUnitId (Unit unit) {
        String sql = "SELECT FROM unit_info WHERE project_id = :project_id and unit_number = :unit_number";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("project_id", unit.getUnitProjectId());
        paraMap.put("unit_number", unit.getUnitNumber());
        return template.queryForObject(sql, paraMap, Integer.class);
    }

    /**
     * update unit price and status in unit_info table
     * @param unit
     */

    public void updateUnitPriceAndStatus(Unit unit) {
        String sql =    "UPDATE unit_info " +
                        "SET unit_current_price = :unit_current_price, unit_status_id = :unit_status_id, sqrm_price = :sqrm_price " +
                        "WHERE id = :id";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("unit_current_price", unit.getUnitPrice());
        paraMap.put("unit_status_id", unit.getUnitStatusId());
        paraMap.put("sqrm_price", unit.getUnitSqrMPrice());
        paraMap.put("id", unit.getUnitId());
        template.update(sql, paraMap);
    }
}


