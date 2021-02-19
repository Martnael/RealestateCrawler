package com.adaur.Crawler.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class TtpServices {

    private String developer = "TTP AS";
    private String[] ttpActiveProjectLinks = {"https://poorise.uusmaa.ee/houses/poorise-5/",
                                                "https://poorise.uusmaa.ee/houses/poorise-7/",
                                                "https://poorise.uusmaa.ee/houses/poorise-3/",
                                                "https://www.jarvetornid.ee/torn-2-korterid/",
                                                "https://www.jarvetornid.ee/aripinnad/"
                                                };
    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";

    /**
     * Main crawler engine for TTP project Poorise
     * @throws IOException
     * Date Last Modified: 19-02-2021
     * Author: Mart Nael
     */

    public static void pooriseCrawler() throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        String[] ttpActiveProjectLinks = {"https://poorise.uusmaa.ee/houses/poorise-5/",
                "https://poorise.uusmaa.ee/houses/poorise-7/",
                "https://poorise.uusmaa.ee/houses/poorise-3/",
                "https://www.jarvetornid.ee/torn-2-korterid/",
                "https://www.jarvetornid.ee/aripinnad/"
        };

        String unitUrl = "";
        String unitNumber = "";
        int unitFloorNumber = 0;
        int unitRoomsNumber = 0;
        double unitSize = 0;
        double unitBalconySize = 0;
        BigDecimal unitPrice = BigDecimal.ZERO;
        String unitStatus = "";


        Document pagePoorise = Jsoup.connect(ttpActiveProjectLinks[1]).userAgent(userAgent).get();
        Elements poorisePlan = pagePoorise.select("div.content.active.tab_content").select("table");
        for (Element trElement : poorisePlan.select("tr")) {
            if (trElement.hasClass(" flat_row  has_link")) {
                unitUrl = trElement.attr("data-link");
                unitNumber = trElement.select("td.flat_nr").text();
                unitFloorNumber = Integer.parseInt(trElement.select("td.floor").text());
                unitRoomsNumber = Integer.parseInt(trElement.select("td.rooms").text());
                unitSize = Double.parseDouble(trElement.select("td.size").text());
                unitBalconySize = Double.parseDouble(trElement.select("td.balcony").text());
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
                unitSize = Double.parseDouble(trElement.select("td.size").text());
                unitBalconySize = Double.parseDouble(trElement.select("td.balcony").text());
                if (trElement.hasClass("price reserved ")) {
                    unitPrice= BigDecimal.ZERO;
                    unitStatus = "Broneeritud";
                } else {
                    unitPrice= BigDecimal.ZERO;
                    unitStatus = "Muudud";
                }

            }
                System.out.println(unitUrl);
                System.out.println(unitNumber);
                System.out.println(unitFloorNumber);
                System.out.println(unitRoomsNumber);
                System.out.println(unitSize);
                System.out.println(unitBalconySize);
                System.out.println(unitPrice);
                System.out.println(unitStatus);
                System.out.println("<---------->");

        }
    }

    public static void main(String[] args) throws IOException {
        pooriseCrawler();
    }

}

