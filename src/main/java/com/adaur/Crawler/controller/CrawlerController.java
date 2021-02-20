package com.adaur.Crawler.controller;

import com.adaur.Crawler.services.ActionResponse;
import com.adaur.Crawler.services.Developer;
import com.adaur.Crawler.services.Services;
import com.adaur.Crawler.services.TtpServices;
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
}
