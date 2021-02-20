package com.adaur.Crawler.controller;

import com.adaur.Crawler.services.Developer;
import com.adaur.Crawler.services.Services;
import com.adaur.Crawler.services.TtpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("crawler")
@RestController
public class CrawlerController {

    @Autowired
    private Services crawlerServices;
    @Autowired
    private TtpServices ttpCrawler;

    @GetMapping("starttheengine")
    public void startCrawling() {

    }

    @PostMapping("adddeveloper")
    public void adddeveloper(@RequestBody Developer developer) {
        crawlerServices.addDeveloper(developer);
    }


}
