package com.adaur.Crawler.services;

import com.adaur.Crawler.entity.CountyEntity;
import com.adaur.Crawler.entity.CountyEntityRepository;
import com.adaur.Crawler.exceptions.CrawlerException;
import com.adaur.Crawler.repository.CrawlerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Services {

    @Autowired
    private CrawlerRepository crawlerRepository;
    @Autowired
    private CountyEntityRepository countyEntityRepository;

    /**
     * Add Developer to database. Developer name have to be unique in database. With SQL query method
     * @param developer
     * @return Message or Exception
     */
    public ActionResponse addDeveloper(Developer developer) {
        try {
            crawlerRepository.addDeveloper(developer);
            ActionResponse response = new ActionResponse();
            response.setResponseMessage("Developer added to database");
            return response;
        } catch (Exception e) {
            throw new CrawlerException("Developer already in database");
        }
    }

    /**
     * Get list of all developers in database. With SQL query method
     * @return
     */

    public List<Developer> getAllDeveloper() {
        return crawlerRepository.getAllDevelopers();
    }

    /**
     * Add area to Database. Check countyId from areaName. With SQL query method
     *
     * @param area
     * @return response
     */

    public ActionResponse addArea(Area area) {
        try {
            area.setCountyId(crawlerRepository.getCountyId(area.getCountyName()));
        } catch (Exception e) {
            throw new CrawlerException("No such county in database!");
        }
        try {
            crawlerRepository.addArea(area);
            ActionResponse response = new ActionResponse();
            response.setResponseMessage("Area added to database.");
            return response;
        } catch (Exception e) {
            throw new CrawlerException("Area is already in database!");
        }
    }

    /**
     * Map Entity County to regular county.
     * @param countyEntity
     * @return County county
     */

    public County mapEntityCounty(CountyEntity countyEntity) {
        County county = new County();
        county.setId(countyEntity.getId());
        county.setCountyName(countyEntity.getCountyName());
        return county;
    }

    /**
     * Get all counties with hibernate method.
     *
     * @return list of counties in database
     */

    public List<County> getAllCounties() {
        List<CountyEntity> countyEntities = countyEntityRepository.findAll();
        List<County> counties = new ArrayList<>();
        for (CountyEntity countyEntity : countyEntities) {
            County county = mapEntityCounty(countyEntity);
            counties.add(county);
        }
        return counties;
    }

    public ActionResponse editDeveloper(Developer developer) {
        ActionResponse response = new ActionResponse();
        return response;
    }

    /**
     * Add new county to the database. with SQL query. Returns response if that county is already in database.
     * County have to be unique.
     * @param county
     * @return
     */

    public ActionResponse addCounty(County county) {
        try {
            crawlerRepository.addCounty(county);
            ActionResponse response = new ActionResponse();
            response.setResponseMessage("County added to database.");
            return response;
        } catch (Exception e) {
            throw new CrawlerException("County is already in database");
        }
    }

    /**
     * Delete county base from countyID
     * @param countyID
     * @return
     */

    public ActionResponse deleteCounty(int countyID) {
        ActionResponse response = new ActionResponse();
        response.setResponseMessage("Deleted from database.");
        return response;
    }
}
