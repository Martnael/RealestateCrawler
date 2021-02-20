package com.adaur.Crawler.services;

import com.adaur.Crawler.exceptions.CrawlerException;
import com.adaur.Crawler.repository.CrawlerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Services {

    @Autowired
    private CrawlerRepository crawlerRepository;

    /**
     * Add Developer to database. Developer name have to be unique in database.
     * @param developer
     * @return Message or Exception
     */
    public ActionResponse addDeveloper(Developer developer) {
        try {
            crawlerRepository.addDeveloper(developer);
            ActionResponse response = new ActionResponse();
            response.setResponseMessage("Developer added to database");
            return response;
        } catch (Exception e) {
            throw new CrawlerException("Developer already in database");
        }
    }

}
