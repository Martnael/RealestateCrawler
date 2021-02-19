package com.adaur.Crawler.services;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TtpServices {

    public static void main(String[] args) throws IOException {
        pooriseCrawler();
    }


    public static void pooriseCrawler() throws IOException {
        String[] pooriseLinks = {"https://poorise.uusmaa.ee/houses/poorise-5/", "https://poorise.uusmaa.ee/houses/poorise-7/", "https://poorise.uusmaa.ee/houses/poorise-3/"};
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        Document pagePoorise = Jsoup.connect(pooriseLinks[0]).userAgent(userAgent).get();
        Elements poorisePlan = pagePoorise.select("div.content.active.tab_content").select("table");
        for (Element trElement : poorisePlan.select("tr")) {
            if (trElement.hasClass("div.flat_row.has_link")) {

            }
            
                String unitUrl = trElement.attr("data-link");
                String unitNumber = trElement.select("td.flat_nr").text();
                String unitFloor = trElement.select("td.floor").text();
                String unitRooms = trElement.select("td.rooms").text();
                String unitSize = trElement.select("td.size").text();
                String unitBalcony = trElement.select("td.balcony").text();
                String unitPrice;
                String unitStatus;
                if (trElement.hasClass("price.demoapartment")) {
                    unitPrice = null;
                    unitStatus = trElement.select("td.price.demoapartment").text();
                } else if (trElement.hasClass("price.sold")) {
                    unitPrice = null;
                    unitStatus = trElement.select("td.price.sold").text();
                } else if ((trElement.select("td.price").text()).equals("Broneeritud")) {
                    unitPrice = null;
                    unitStatus = trElement.select("td.price").text();
                } else {
                    unitPrice = trElement.select("td.price").text();
                    unitStatus = "Vaba";
                }
                System.out.println(unitUrl);
                System.out.println(unitNumber);
                System.out.println(unitFloor);
                System.out.println(unitRooms);
                System.out.println(unitSize);
                System.out.println(unitBalcony);
                System.out.println(unitPrice);
                System.out.println(unitStatus);
                System.out.println("<---------->");

        }
    }
}

