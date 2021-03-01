package com.adaur.Crawler.services;

import java.math.BigDecimal;

public class Project {
    private int Id;
    private String projectName;

    private String developerName;
    private int developerId;

    private String areaName;
    private int areaId;

    private String statusName;
    private int statusId;

    private BigDecimal sqrMPrice;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public BigDecimal getSqrMPrice() {
        return sqrMPrice;
    }

    public void setSqrMPrice(BigDecimal sqrMPrice) {
        this.sqrMPrice = sqrMPrice;
    }
}
