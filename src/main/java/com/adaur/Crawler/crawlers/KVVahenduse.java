package com.adaur.Crawler.crawlers;

import com.adaur.Crawler.exceptions.CrawlerException;
import com.adaur.Crawler.repository.CrawlerRepository;
import com.adaur.Crawler.services.Services;
import com.adaur.Crawler.services.Unit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KVVahenduse {

    @Autowired
    private CrawlerRepository crawlerRepository;

    Services services = new Services();

    public List<Unit> kehraAiaCrawler(String url, int projectId) {
        int unitConstructionYear = 2020;
        int unitCategoryId = 1;
        int unitTypeId = 1;
        Date date = new Date();
        List<Unit> unitList = new ArrayList<>();
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";
        try {
            Document kehraAia = Jsoup.connect(url).userAgent(userAgent).get();
            Elements tbody = kehraAia.select("tbody");
            for (Element trElement : tbody.select("tr")) {
                Unit unit = new Unit();
                unit.setUnitScanTime(date);
                unit.setUnitProjectId(projectId);
                unit.setUnitTypeId(unitTypeId);
                unit.setUnitCategoryId(unitCategoryId);
                unit.setUnitConstructionYear(unitConstructionYear);
                unit.setUnitUrl(trElement.attr("data-href"));
                unit.setUnitNumber(trElement.select("td").get(0).text());
                unit.setUnitFloor(Integer.parseInt(trElement.select("td").get(1).text()));
                unit.setUnitRooms(Integer.parseInt(trElement.select("td").get(2).text()));
                unit.setUnitSize(new BigDecimal(trElement.select("td").get(3).text().replace(",", ".")));
                unit.setUnitBalconySize(new BigDecimal(trElement.select("td").get(4).text().replace(",", ".")));
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
                unitList.add(unit);
                unit.setUnitSqrMPrice(services.calculateUnitSqrMPrice(unit.getUnitSize(), unit.getUnitPrice()));
            }
            return unitList;
        } catch (Exception e) {
            crawlerRepository.insertError(projectId, date);
            throw new CrawlerException("Something went wrong" + projectId);
        }
    }
}
