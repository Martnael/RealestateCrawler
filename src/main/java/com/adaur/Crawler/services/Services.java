package com.adaur.Crawler.services;

import com.adaur.Crawler.repository.CrawlerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Services {

    @Autowired
    private CrawlerRepository crawlerRepository;


    public ActionResponse addDeveloper(Developer developer) {
        crawlerRepository.addDeveloper(developer);
        ActionResponse response = new ActionResponse();
        response.setResponseMessage("Developer added to database");
        return response;
    }


}
