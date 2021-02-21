package com.adaur.Crawler.repository;

import com.adaur.Crawler.rowMappers.DeveloperRowMapper;
import com.adaur.Crawler.services.Area;
import com.adaur.Crawler.services.Developer;
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
     * Add area to the Database. No double entry allowed.
     * @param area
     */

    public void addArea (Area area) {
        String sql = "INSERT INTO area (area_name, county_id) VALUES (:area_name, :county_id)";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("area_name", area.getAreaName());
        paraMap.put("county_id", area.getCountyId());
        template.update(sql, paraMap);
    }


}


