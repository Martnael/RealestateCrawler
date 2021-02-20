package com.adaur.Crawler.repository;

import com.adaur.Crawler.services.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CrawlerRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public void addDeveloper (Developer developer) {
        String sql = "INSERT INTO developer (developer_name) " + "VALUES (:developer_name)";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("developer_name", developer.getDeveloperName());
        template.update(sql, paraMap);
    }

}
