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
     * @throws IOException Date Last Modified: 19-02-2021
     *                     Author: Mart Nael
     */

    public List<Unit> poorise5Crawler() throws IOException {
        // Hardcoded values what are always same
        String developer = "TTP AS";
        int developerId = 1;
        int projectId = 1;
        int unitTypeId = 1;
        int unitCategoryId = 1;
        int unitConstructionYear = 2021;
        Date date = new Date();
        List<Unit> unitList = new ArrayList<>();   // For collect all new units
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        String url = "https://poorise.uusmaa.ee/houses/poorise-5/";

        // Values what will be overwritten during the crawling
        String unitUrl = "";
        String unitNumber = "";
        int unitFloorNumber = 0;
        int unitRoomsNumber = 0;
        BigDecimal unitSize = BigDecimal.ZERO;
        BigDecimal unitBalconySize = BigDecimal.ZERO;
        BigDecimal unitPrice = BigDecimal.ZERO;
        BigDecimal unitSqrMPrice = BigDecimal.ZERO;
        String unitStatus = "";


        // Scrapping process
        Document pagePoorise = Jsoup.connect(url).userAgent(userAgent).get();
        Elements poorisePlan = pagePoorise.select("div.content.active.tab_content").select("table");
        for (Element trElement : poorisePlan.select("tr")) {
            Unit unit = new Unit();
            if (trElement.hasClass(" flat_row  has_link")) {
                unitUrl = trElement.attr("data-link");
                unitNumber = trElement.select("td.flat_nr").text();
                unitFloorNumber = Integer.parseInt(trElement.select("td.floor").text());
                unitRoomsNumber = Integer.parseInt(trElement.select("td.rooms").text());
                unitSize = new BigDecimal(trElement.select("td.size").text());
                unitBalconySize = new BigDecimal(trElement.select("td.balcony").text());
                if ((trElement.select("td.price  ").text()).startsWith("N")) {
                    unitPrice = BigDecimal.ZERO;
                    unitStatus = "Naidiskorter";
                } else {
                    unitPrice = new BigDecimal(trElement.select("td.price  ").text());
                    unitStatus = "Vaba";
                }
            } else if (trElement.hasClass(" flat_row  ")) {
                unitUrl = trElement.attr("data-link");
                unitNumber = trElement.select("td.flat_nr").text();
                unitFloorNumber = Integer.parseInt(trElement.select("td.floor").text());
                unitRoomsNumber = Integer.parseInt(trElement.select("td.rooms").text());
                unitSize = new BigDecimal(trElement.select("td.size").text());
                unitBalconySize = new BigDecimal(trElement.select("td.balcony").text());
                if (trElement.hasClass("price reserved ")) {
                    unitPrice = BigDecimal.ZERO;
                    unitStatus = "Broneeritud";
                } else {
                    unitPrice = BigDecimal.ZERO;
                    unitStatus = "Muudud";
                }

            }
            // first unit is empty unit from webpage to avoid this unit to getting to the list
            if (!unitNumber.isBlank()) {
                unit.setUnitSqrMPrice(services.calculateUnitSqrMPrice(unitSize, unitPrice));
                unit.setUnitNumber(unitNumber);
                unit.setDeveloperId(developerId);
                unit.setUnitSize(unitSize);
                unit.setUnitBalconySize(unitBalconySize);
                unit.setUnitPrice(unitPrice);
                unit.setUnitConstructionYear(unitConstructionYear);
                unit.setUnitScanTime(date);
                unit.setUnitStatus(unitStatus);
                unit.setUnitStatusId(getUnitStatusId(unitStatus));
                unit.setUnitProjectId(projectId);
                unit.setUnitTypeId(unitTypeId);
                unit.setUnitCategoryId(unitCategoryId);
                unit.setUnitUrl(unitUrl);
                unit.setUnitFloor(unitFloorNumber);
                unit.setUnitRooms(unitRoomsNumber);
                unitList.add(unit);
            }
        }
        return unitList;
    }

    /**
     * change according to page status to numerical value.
     * @param unitStatus
     * @return
     */

    public int getUnitStatusId (String unitStatus) {
        if (unitStatus.equals("Vaba") || unitStatus.equals("Naidiskorter")) {
            return 1;
        } else if (unitStatus.equals("Muudud") || unitStatus.equals("Müüdud")) {
            return 3;
        } else {
            return 2;
        }
    }


}

