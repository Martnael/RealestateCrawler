package com.adaur.Crawler.services;

import org.springframework.stereotype.Service;

@Service
public class ActionResponse {

    String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
