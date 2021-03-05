package com.adaur.Crawler.services;


import com.sun.xml.bind.v2.runtime.output.SAXOutput;
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
    // project_id
    // unit_number
    // unit_size
    // unit_balcony_size
    // unit_current_price
    // unit_construction_year
    // unit_scan_time
    // unit_status_id: 1-Vaba 2-Broneeritud 3-Muudud
    // unit_category_id : 1- Korter 2-ridaelamu 4-aripind
    // unit_type_id : 1. UUS
    // unit_url
    // unit_rooms
    // Unit_floor
    // unit_sqrm_price

    public static void main(String[] args) throws IOException {
        String url = "https://kehra-aia.kinnisvaravahendus.ee/hinnad-ja-plaanid/";
        Date date = new Date();
        List<Unit> unitList = new ArrayList<>();   // For collect all new units
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        Document kehraAia = Jsoup.connect(url).userAgent(userAgent).get();
        Elements tbody = kehraAia.select("tbody");
        for (Element trElement : tbody.select("tr")) {
            Unit unit = new Unit();
            unit.setUnitUrl(trElement.attr("data-href"));
            System.out.println(trElement.attr("data-href"));

            unit.setUnitNumber(trElement.select("td").get(0).text());
            System.out.println(unit.getUnitNumber());

            unit.setUnitFloor(Integer.parseInt(trElement.select("td").get(1).text()));
            System.out.println(unit.getUnitFloor());

            unit.setUnitRooms(Integer.parseInt(trElement.select("td").get(2).text()));
            System.out.println(unit.getUnitRooms());

            unit.setUnitSize(new BigDecimal(trElement.select("td").get(3).text().replace(",", ".")));
            System.out.println(unit.getUnitSize());

            unit.setUnitBalconySize(new BigDecimal(trElement.select("td").get(4).text().replace(",", ".")));
            System.out.println(unit.getUnitBalconySize());

            if (trElement.select("td").get(5).text().equals("Broneeritud")) {
                unit.setUnitStatusId(2);
                unit.setUnitPrice(BigDecimal.ZERO);
            } else if (trElement.select("td").get(5).text().startsWith("M")) {
                unit.setUnitStatusId(3);
                unit.setUnitPrice(BigDecimal.ZERO);
            } else {
                unit.setUnitStatusId(1);
                unit.setUnitPrice(new BigDecimal(trElement.select("td").get(5).text().replace(" ", "")));
            }
            System.out.println(unit.getUnitStatusId());
            System.out.println(unit.getUnitPrice());
            unitList.add(unit);
            System.out.println("<-------------->");
        }


    }
}

