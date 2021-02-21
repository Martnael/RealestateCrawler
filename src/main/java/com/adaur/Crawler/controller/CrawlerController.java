package com.adaur.Crawler.controller;

import com.adaur.Crawler.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("crawler")
@RestController
public class CrawlerController {

    @Autowired
    private Services crawlerServices;
    @Autowired
    private TtpServices ttpCrawler;

    // Not ready still for crawling
    @GetMapping("starttheengine")
    public void startCrawling() {

    }

    @PostMapping("adddeveloper")
    public ActionResponse addDeveloper(@RequestBody Developer developer) {
        return crawlerServices.addDeveloper(developer);
    }

    @GetMapping("alldevelopers")
    public List<Developer> allDevelopers() {
        return crawlerServices.getAllDeveloper();
    }

    @PostMapping("addarea")
    public ActionResponse addArea(@RequestBody Area area) {
        return crawlerServices.addArea(area);
    }

    @GetMapping("getcounties")
    public List<County> allCounties() {
        return crawlerServices.getAllCounties();
    }
}
