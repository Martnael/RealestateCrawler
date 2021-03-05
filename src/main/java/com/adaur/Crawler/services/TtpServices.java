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

    public List<Unit> jarveTornidCrawler(String url, int projectId) throws IOException {
        Date date = new Date();
        List<Unit> unitList = new ArrayList<>();   // For collect all new units
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        Document pageTornid = Jsoup.connect(url).userAgent(userAgent).get();
        Element tbody = pageTornid.select("tbody").get(0);
        for (Element trElement : tbody.select("tr")) {
            Unit unit = new Unit();
            unit.setUnitProjectId(projectId);
            unit.setUnitTypeId(1);
            unit.setUnitCategoryId(1);
            unit.setUnitScanTime(date);
            unit.setUnitConstructionYear(2020);
            unit.setUnitUrl(trElement.attr("data-link"));
            unit.setUnitFloor(Integer.parseInt(trElement.select("td").get(0).text()));
            unit.setUnitNumber(trElement.select("td").get(1).text());
            unit.setUnitRooms(Integer.parseInt(trElement.select("td").get(2).text()));
            unit.setUnitSize(new BigDecimal(trElement.select("td").get(3).text()));
            if (trElement.select("td").get(4).text().equals("")) {
                unit.setUnitBalconySize(BigDecimal.ZERO);
            } else {
                unit.setUnitBalconySize(new BigDecimal(trElement.select("td").get(4).text()));
            }
            if (trElement.select("td").get(5).text().equals("Müüdud")) {
                unit.setUnitPrice(BigDecimal.ZERO);
                unit.setUnitStatusId(3);
            } else if (trElement.select("td").get(5).text().equals("Broneeritud")) {
                unit.setUnitPrice(BigDecimal.ZERO);
                unit.setUnitStatusId(2);
            } else if (trElement.select("td").get(5).hasClass("price demoapartment ")) {
                unit.setUnitPrice(new BigDecimal(trElement.select("td").get(5).select("p").get(0).text()));
                unit.setUnitStatusId(1);
            } else {
                unit.setUnitPrice(new BigDecimal(trElement.select("td").get(5).text()));
                unit.setUnitStatusId(1);
            }
            unit.setUnitSqrMPrice(services.calculateUnitSqrMPrice(unit.getUnitSize(),unit.getUnitPrice()));
            unitList.add(unit);
        }
    return unitList;
    }

    /**
     * Jarve Tornid commercial part crawling
     * @param url
     * @return
     * @throws IOException
     */
    public List<Unit> jarveTornidAriCrawler(String url, int projectId) throws IOException {
        Date date = new Date();
        List<Unit> unitList = new ArrayList<>();   // For collect all new units
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        Document pageTornid = Jsoup.connect(url).userAgent(userAgent).get();
        Elements tbody = pageTornid.select("tbody");
        for (Element trElement : tbody.select("tr")) {
            Unit unit = new Unit();
            unit.setUnitProjectId(projectId);
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
            if (trElement.select("td").get(3).select("span").text().equals("Broneeritud")) {
                unit.setUnitPrice(BigDecimal.ZERO);
                unit.setUnitSqrMPrice(BigDecimal.ZERO);
                unit.setUnitStatusId(2);
            } else if (trElement.select("td").get(3).select("span").text().equals("Üüritud") || trElement.select("td").get(3).select("span").text().equals("ÜÜRITUD")) {
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
        return unitList;
    }

}

