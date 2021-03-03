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
     * kadaka-tee-191a   // project id 2  // year 2020
     * kadaka-tee-191b    // project id 3  // year 2020
     * kadaka-tee-191c    // project id 4   // year 2020
     * iltre-11            // project id 5   // year 2021
     * iltre-9             // project id 6   // year 2021
     * redise-18           // project id 7   // year 2021
     * volmre-21           // project id 8   // year 2021
     * volmre-23           // project id 9   // year 2021
     * volmre-25           // project id 10   // year 2021
     * volmre-27           // project id 11   // year 2021
     *
     * @param url
     * @param projectId
     * @param unitTypeId
     * @param unitCategoryId
     * @param unitConstructionYear
     * @return List of units
     * @throws IOException
     */

    public List<Unit> kaamosCrawler(String url, int projectId, int unitTypeId, int unitCategoryId, int unitConstructionYear) throws IOException {
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

    /**
     * Crawler what is for ZityZen project 12
     * @param url
     * @return lIST OF UNITS
     * @throws IOException
     */
    public List<Unit> cityzenCrawler(String url) throws IOException {
        Date date = new Date();     // Scanning date
        int developerId = 2;
        int projectId = 12;
        int projectConstructionYear = 2021;
        int unitType = 1;
        int unitCategoryId = 1;
        List<Unit> unitList = new ArrayList<>();   // For collect all new units

        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        Document pageZityzen = Jsoup.connect(url).userAgent(userAgent).get();
        Elements unitsBody = pageZityzen.select("tbody");

        int counter = 0;        // Counter to loop out only 40 apartments not double ones
        for (Element elementTr : unitsBody.select("tr")) {
            Unit unit = new Unit();
            unit.setUnitScanTime(date);
            unit.setUnitConstructionYear(projectConstructionYear);
            unit.setDeveloperId(developerId);
            unit.setUnitProjectId(projectId);
            unit.setUnitTypeId(unitType);
            unit.setUnitCategoryId(unitCategoryId);
            unit.setUnitNumber(elementTr.attr("data-ap"));
            unit.setUnitRooms(Integer.parseInt(elementTr.attr("data-rooms")));
            // unit can be also on 2 floors
            if (elementTr.attr("data-floor").contains(",")) {
                String[] floors = elementTr.attr("data-floor").split(",");
                unit.setUnitFloor(Integer.parseInt(floors[0]));
            } else {
                unit.setUnitFloor(Integer.parseInt(elementTr.attr("data-floor")));
            }
            if (elementTr.hasAttr("data-url")) {
                unit.setUnitUrl(elementTr.attr("data-url"));
            }
            // Status examples: Müüdud, Broneeritud, Vaata == Vaba
            if (elementTr.select("span").text().equals("Müüdud")) {
                unit.setUnitStatusId(3);
            } else if (elementTr.select("span").text().equals("Broneeritud")) {
                unit.setUnitStatusId(2);
            } else {
                unit.setUnitStatusId(2);
            }
            // Unit size is either - or string number
            if (elementTr.select("div").get(3).text().equals("-")) {
                unit.setUnitSize(BigDecimal.ZERO);
            } else {
                unit.setUnitSize(new BigDecimal(elementTr.select("div").get(3).text()));
            }

            // units may have two Balconies(contains +) or either -

            if (elementTr.select("div").get(4).text().contains("+")) {
                String[] balconies = elementTr.select("div").get(4).text().split("\\+");
                BigDecimal balcony1 = new BigDecimal(balconies[0].replace(",", "."));
                BigDecimal balcony2 = new BigDecimal(balconies[1].replace(",", "."));
                unit.setUnitBalconySize(balcony1.add(balcony2));
            } else if (elementTr.select("div").get(4).text().equals("-")) {
                unit.setUnitBalconySize(BigDecimal.ZERO);
            } else {
                unit.setUnitBalconySize(new BigDecimal(elementTr.select("div").get(4).text().replace(",", ".")));
            }

            // Unit price converting
            if (elementTr.select("div").get(5).text().equals("-")) {
                unit.setUnitPrice(BigDecimal.ZERO);
            } else {
                String unitPrice = elementTr.select("div").get(5).text().replace(" ", "");
                unit.setUnitPrice(new BigDecimal(unitPrice));
            }

            // units sqr meter price calculation
            if (unit.getUnitPrice().compareTo(BigDecimal.ZERO) > 0) {
                unit.setUnitSqrMPrice(unit.getUnitPrice().divide(unit.getUnitSize(), 2, RoundingMode.HALF_UP));
            } else {
                unit.setUnitSqrMPrice(BigDecimal.ZERO);
            }

            unitList.add(unit);

            if (counter == 39) {
                break;
            }
            counter++;
        }
        return unitList;
    }
}
