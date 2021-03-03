package com.adaur.Crawler.controller;

import com.adaur.Crawler.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public void startCrawling() throws IOException {
        crawlerServices.startTheCrawling();
    }

    @CrossOrigin
    @GetMapping("getcounties")
    public List<County> allCounties() {
        return crawlerServices.getAllCounties();
    }

    @CrossOrigin
    @PostMapping("addcounty")
    public ActionResponse addCounty (@RequestBody County county) {
        return crawlerServices.addCounty(county);
    }

    @CrossOrigin
    @PutMapping("deletecounty")
    public ActionResponse deleteCounty (@RequestParam("id") int countyID) {
        return crawlerServices.deleteCounty(countyID);
    }












    @PostMapping("adddeveloper")
    public ActionResponse addDeveloper(@RequestBody Developer developer) {
        return crawlerServices.addDeveloper(developer);
    }

    @CrossOrigin
    @GetMapping("alldevelopers")
    public List<Developer> allDevelopers() {
        return crawlerServices.getAllDeveloper();
    }

    @PostMapping("addarea")
    public ActionResponse addArea(@RequestBody Area area) {
        return crawlerServices.addArea(area);
    }



//    @PutMapping("editdeveloper")
//    public ActionResponse editDeveloper (@RequestBody Developer developer) {
//        return crawlerServices.editDeveloper(developer);
//    }


}
