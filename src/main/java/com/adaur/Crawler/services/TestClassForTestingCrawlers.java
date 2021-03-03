package com.adaur.Crawler.services;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TestClassForTestingCrawlers {

    // !!!! variable what have to be in unit !!!!
    // project_id +
    // unit_number +
    // unit_size +
    // unit_balcony_size -
    // unit_current_price -
    // unit_construction_year +
    // unit_scan_time +
    // unit_status_id: 1-Vaba 2-Broneeritud 3-Muudud
    // unit_category_id : 1- Korter 2-ridaelamu 4-aripind
    // unit_type_id : 1. UUS
    // unit_url
    // unit_rooms
    // Unit_floor
    // unit_sqrm_price

    public static void main(String[] args) throws IOException {
        String url = "https://www.jarvetornid.ee/aripinnad/";
        Date date = new Date();
        List<Unit> unitList = new ArrayList<>();   // For collect all new units
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        Document pageTornid = Jsoup.connect(url).userAgent(userAgent).get();
        Elements tbody = pageTornid.select("tbody");
        for (Element trElement : tbody.select("tr")) {
            Unit unit = new Unit();
            unit.setUnitProjectId(14);
            unit.setUnitConstructionYear(2021);
            unit.setUnitScanTime(date);
            unit.setUnitTypeId(1);
            unit.setUnitUrl(trElement.select("td").get(0).select("a").attr("href"));
            unit.setUnitNumber(trElement.select("td").get(0).select("span").text());
            if (trElement.select("td").get(1).select("span").text().equals("büroo")) {
                unit.setUnitCategoryId(4);
            } else {
                unit.setUnitCategoryId(6);
            }
            unit.setUnitSize(new BigDecimal(trElement.select("td").get(2).select("span").text()));
            if(trElement.select("td").get(3).select("span").text().equals("Broneeritud")) {
                unit.setUnitPrice(BigDecimal.ZERO);
                unit.setUnitSqrMPrice(BigDecimal.ZERO);
                unit.setUnitStatusId(2);
            } else if(trElement.select("td").get(3).select("span").text().equals("Üüritud") || trElement.select("td").get(3).select("span").text().equals("ÜÜRITUD") ) {
                unit.setUnitPrice(BigDecimal.ZERO);
                unit.setUnitSqrMPrice(BigDecimal.ZERO);
                unit.setUnitStatusId(4);
            } else {
                unit.setUnitPrice(new BigDecimal(trElement.select("td").get(3).select("span").text()));
                unit.setUnitSqrMPrice(new BigDecimal(trElement.select("td").get(3).select("span").text()));
                unit.setUnitStatusId(5);
            }
            unitList.add(unit);
        }
    }
}

