package com.adaur.Crawler.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KaamosServices {

    /**
     * it handles following projects
     *  kadaka-tee-191a   // project id 1  // year 2020
     *  kadaka-tee-191b    // project id 2  // year 2020
     *  kadaka-tee-191c    // project id 3   // year 2020
     *  iltre-11
     *  iltre-9
     *  redise-18
     *  volmre-21
     *  volmre-23
     *  volmre-25
     *  volmre-27
     * @param url
     * @param projectId
     * @param unitTypeId
     * @param unitCategoryId
     * @param unitConstructionYear
     * @return List of units
     * @throws IOException
     */

    public List<Unit> kaamosCrawler(String url, int projectId, int unitTypeId, int unitCategoryId, int unitConstructionYear ) throws IOException {
        Date date = new Date();     // Scanning date
        int developerId = 2;
        List<Unit> unitList = new ArrayList<>();   // For collect all new units
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        Document pageKaamos = Jsoup.connect(url).userAgent(userAgent).get();
        Elements unitsBody = pageKaamos.select("tbody");
        for (Element elementTr : unitsBody.select("tr")) {
            Unit unit = new Unit();
            unit.setUnitScanTime(date);
            unit.setUnitProjectId(projectId);
            unit.setDeveloperId(developerId);
            unit.setUnitTypeId(unitTypeId);
            unit.setUnitCategoryId(unitCategoryId);
            unit.setUnitConstructionYear(unitConstructionYear);
            unit.setUnitUrl(elementTr.attr("data-href"));
            unit.setUnitNumber(elementTr.select("td").get(0).text());
            unit.setUnitFloor(Integer.parseInt(elementTr.select("td").get(1).text()));
            unit.setUnitRooms(Integer.parseInt(elementTr.select("td").get(2).text()));
            String[] unitSize = elementTr.select("td").get(3).text().split(" ");
            unit.setUnitSize(new BigDecimal(unitSize[0].replace(",", ".")));
            // there can be 2 balconies on unit
            String[] unitBalconySize = elementTr.select("td").get(4).text().split(" ");
            if (!unitBalconySize[0].contains("+")) {
                unit.setUnitBalconySize(new BigDecimal(unitBalconySize[0].replace(",", ".")));
            } else {
                String[] twoBalconies = unitBalconySize[0].split("\\+");
                BigDecimal balconySize = new BigDecimal(twoBalconies[0].replace(",", ".")).add(new BigDecimal(twoBalconies[1].replace(",", ".")));
                unit.setUnitBalconySize(balconySize);
            }

            // Set unit price and unit statusId
            if (elementTr.select("td").get(5).text().equals("Müüdud")) {
                unit.setUnitPrice(BigDecimal.ZERO);
                unit.setUnitStatusId(3);
            } else if (elementTr.select("td").get(5).text().equals("Broneeritud")) {
                unit.setUnitPrice(BigDecimal.ZERO);
                unit.setUnitStatusId(2);
            } else {
                unit.setUnitPrice(new BigDecimal(elementTr.select("td").get(5).text().replace("€", "").replace(" ", "")));
                unit.setUnitStatusId(1);
            }

            // Set unist sqr meter price
            if (unit.getUnitPrice().compareTo(BigDecimal.ZERO) > 0) {
                unit.setUnitSqrMPrice(unit.getUnitPrice().divide(unit.getUnitSize(), 2, RoundingMode.HALF_UP));
            } else {
                unit.setUnitSqrMPrice(BigDecimal.ZERO);
            }
            unitList.add(unit);
        }
        return unitList;
    }


}
