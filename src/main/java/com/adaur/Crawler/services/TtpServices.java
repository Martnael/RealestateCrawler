package com.adaur.Crawler.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TtpServices {

    @Autowired
    private Services services;

    /**
     * Main crawler engine for TTP project Poorise
     *
     * @throws IOException Date Last Modified: 03-03-2021
     *                     Author: Mart Nael
     */

    public List<Unit> pooriseCrawler(String url, int projectId) throws IOException {
        Date date = new Date();
        int projectConstructionYear = 2021;
        int unitType = 1;
        int unitCategoryId = 1;
        int developerId = 2;
        List<Unit> unitList = new ArrayList<>();   // For collect all new units
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";

        // Scrapping process
        Document pagePoorise = Jsoup.connect(url).userAgent(userAgent).get();
        Elements poorisePlan = pagePoorise.select("div.content.active.tab_content").select("table");
        for (Element trElement : poorisePlan.select("tr")) {
            Unit unit = new Unit();
            unit.setDeveloperId(developerId);
            unit.setUnitCategoryId(unitCategoryId);
            unit.setUnitTypeId(unitType);
            unit.setUnitConstructionYear(projectConstructionYear);
            unit.setUnitScanTime(date);
            unit.setUnitProjectId(projectId);
            if (trElement.hasClass(" flat_row  ")) {
                unit.setUnitNumber(trElement.select("td").get(0).text());
                unit.setUnitFloor(Integer.parseInt(trElement.select("td").get(1).text()));
                unit.setUnitRooms(Integer.parseInt(trElement.select("td").get(2).text()));
                unit.setUnitSize(new BigDecimal(trElement.select("td").get(3).text()));
                unit.setUnitBalconySize(new BigDecimal(trElement.select("td").get(4).text()));
                if (trElement.select("td").get(5).text().equals("Müüdud")) {
                    unit.setUnitStatusId(3);
                } else {
                    unit.setUnitStatusId(2);
                }
                unit.setUnitPrice(BigDecimal.ZERO);
                unit.setUnitSqrMPrice(BigDecimal.ZERO);
            }
            if (trElement.hasClass(" flat_row  has_link")) {
                unit.setUnitNumber(trElement.select("td").get(0).text());
                unit.setUnitFloor(Integer.parseInt(trElement.select("td").get(1).text()));
                unit.setUnitRooms(Integer.parseInt(trElement.select("td").get(2).text()));
                unit.setUnitSize(new BigDecimal(trElement.select("td").get(3).text()));
                unit.setUnitBalconySize(new BigDecimal(trElement.select("td").get(4).text()));
                if (trElement.select("td").get(5).hasClass("price demoapartment ")) {
                    if (trElement.select("td").get(5).select("p").get(0).text().isBlank()) {
                        unit.setUnitPrice(BigDecimal.ZERO);
                        unit.setUnitStatusId(1);
                    } else {
                        unit.setUnitPrice(new BigDecimal(trElement.select("td").get(5).select("p").get(0).text()));
                        unit.setUnitStatusId(1);
                    }
                } else {
                    unit.setUnitPrice(new BigDecimal(trElement.select("td").get(5).text()));
                    unit.setUnitStatusId(1);
                }
                unit.setUnitSqrMPrice(services.calculateUnitSqrMPrice(unit.getUnitSize(), unit.getUnitPrice()));
            }
            // to avoid first empty unit
            if(unit.getUnitNumber() == null) {
                continue;
            } else {
                unitList.add(unit);
            }
        }
        return unitList;
    }
}

