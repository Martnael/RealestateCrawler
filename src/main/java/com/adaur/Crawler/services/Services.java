package com.adaur.Crawler.services;

import com.adaur.Crawler.entity.CountyEntity;
import com.adaur.Crawler.entity.CountyEntityRepository;
import com.adaur.Crawler.exceptions.CrawlerException;
import com.adaur.Crawler.repository.CrawlerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class Services {

    @Autowired
    private CrawlerRepository crawlerRepository;
    @Autowired
    private CountyEntityRepository countyEntityRepository;
    @Autowired
    private TtpServices ttpServices;

    // All programs under here

    public void startTheCrawling() throws IOException {
        List<Unit> unit = ttpServices.poorise5Crawler();
        updateProjectDatabase(unit);




    }








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

    /**
     *
     * @param unitList
     */

    public void updateProjectDatabase (List<Unit> unitList) {
        for (Unit unit : unitList) {
            if (crawlerRepository.unitCountInDatabase(unit) != 1) {
                crawlerRepository.addUnitToDB(unit);
            } else {
                crawlerRepository.checkUnitPriceChange(unit);
            }
        }
    }

    /**
     * Calculating unit square meter price. If unit price is null then returns null
     * @param unitSize BigDecimal
     * @param unitPrice BigDecimal
     * @return BigDecimal unit square meter price
     */

    public BigDecimal calculateUnitSqrMPrice (BigDecimal unitSize, BigDecimal unitPrice) {
        if (unitSize.equals(0) || unitPrice.equals(0) || unitPrice.equals(null) || unitSize.equals(null)) {
            return null;
        } else {
            BigDecimal unitSqrMPrice = unitPrice.divide(unitSize, 2, RoundingMode.HALF_UP);
            return unitSqrMPrice;
        }
    }

    /**
     * Calculate project average square meter price.
     * @param unitList
     * @return
     */

    public BigDecimal calculateProjectSqrMPrice (List<Unit> unitList) {
        List<BigDecimal> unitSqrMPriceValues = new ArrayList<>();
        for (Unit unit : unitList) {
            if (unit.getUnitSqrMPrice().compareTo(BigDecimal.ZERO) > 0) {
                unitSqrMPriceValues.add(unit.getUnitSqrMPrice());
            }
        }
        BigDecimal sumOfPrices = unitSqrMPriceValues.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal numberOfUnits = BigDecimal.valueOf(unitSqrMPriceValues.size());
        return sumOfPrices.divide(numberOfUnits, 2, RoundingMode.HALF_UP );
    }

    /**
     * check is unit in unit_info table. If is then only compare price and if needed to change price.
     * @param unit
     * @return
     */
    public boolean isUnitInDatabase (Unit unit) {


        return true;
    }
}
