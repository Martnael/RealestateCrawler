package com.adaur.Crawler.rowMappers;

import com.adaur.Crawler.services.Developer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeveloperRowMapper implements RowMapper<Developer> {

    @Override
    public Developer mapRow(ResultSet resultSet, int i) throws SQLException {
        Developer developer = new Developer();
        developer.setDeveloperId(resultSet.getInt("id"));
        developer.setDeveloperName(resultSet.getString("developer_name"));
        return developer;
    }
}
