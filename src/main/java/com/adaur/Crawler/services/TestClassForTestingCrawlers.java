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
        String url = "https://kaamos.ee/cityzen/hinnad-ja-plaanid/";     // project id   1

        Document pageKaamos = Jsoup.connect(url).userAgent(userAgent).get();
        Elements unitsBody = pageKaamos.select("tbody");
        int counter = 0;
        for (Element elementTr : unitsBody.select("tr")) {
            System.out.println(elementTr.attr("data-ap"));   // apartment number
            System.out.println(elementTr.attr("data-rooms"));   // rooms number
            System.out.println(elementTr.attr("data-floor"));   // floor number
            if(elementTr.hasAttr("data-url")) {
                System.out.println(elementTr.attr("data-url")); // url
            } else {
                System.out.println("NO URL");
            }
            System.out.println(elementTr.select("span").text());  //Status
            System.out.println(elementTr.select("div").get(3).text());  // Size
            System.out.println(elementTr.select("div").get(4).text()); // Balcony
            System.out.println(elementTr.select("div").get(5).text()); // Price
            System.out.println(counter);
            if (counter == 39) {
                break;
            }
            counter++;
            System.out.println("-----------------------------");
        }


    }


}
