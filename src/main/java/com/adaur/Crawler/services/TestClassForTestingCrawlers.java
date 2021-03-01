package com.adaur.Crawler.services;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestClassForTestingCrawlers {
    public static void main(String[] args) throws IOException {
        // Hardcoded values what are always same
        int developerId = 2;
        int projectId = 2;
        int unitTypeId = 1;
        int unitCategoryId = 1;
        int unitConstructionYear = 2021;
        Date date = new Date();
        List<Unit> unitList = new ArrayList<>();   // For collect all new units
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        String url = "https://kaamos.ee/hobemetsa/hinnad/kadaka-tee-191a/";     // project id   1
        String url2 = "https://kaamos.ee/hobemetsa/hinnad/kadaka-tee-191b/";    // project id   2
        String url3 = "https://kaamos.ee/hobemetsa/hinnad/kadaka-tee-191c/";    // project id   3
        String url4 = "https://kaamos.ee/vahtramae/hinnad/iltre-11/";
        String url5 = "https://kaamos.ee/vahtramae/hinnad/iltre-9/";
        String url6 = "https://kaamos.ee/vahtramae/hinnad/redise-18/";
        String url7 = "https://kaamos.ee/vikimoisa/hinnad/volmre-21/";
        String url8 = "https://kaamos.ee/vikimoisa/hinnad/volmre-23/";
        String url9 = "https://kaamos.ee/vikimoisa/hinnad/volmre-25/";
        String url10 = "https://kaamos.ee/vikimoisa/hinnad/volmre-27/";


        Document pageKaamos = Jsoup.connect(url10).userAgent(userAgent).get();
        Elements unitsBody = pageKaamos.select("tbody");
        for (Element elementTr : unitsBody.select("tr")) {
            Unit unit = new Unit();
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
            if(unit.getUnitPrice().compareTo(BigDecimal.ZERO)>0) {
                unit.setUnitSqrMPrice(unit.getUnitPrice().divide(unit.getUnitSize(), 2, RoundingMode.HALF_UP));
            } else {
                unit.setUnitSqrMPrice(BigDecimal.ZERO);
            }


            System.out.println(elementTr.attr("data-href"));
            System.out.println(elementTr.select("td").get(0).text());
            System.out.println(elementTr.select("td").get(1).text());
            System.out.println(elementTr.select("td").get(2).text());
            System.out.println(elementTr.select("td").get(3).text());
            System.out.println(elementTr.select("td").get(4).text());
            System.out.println(elementTr.select("td").get(5).text());
            System.out.println(unit.getUnitSqrMPrice());
            System.out.println("-----------------------");

        }


    }


}
