package com.adaur.Crawler.services;

import java.math.BigDecimal;
import java.util.Date;

public class Unit {
    private String unitNumber;
    private double unitSize;
    private double unitBalconySize;
    private BigDecimal unitPrice;
    private String unitStatus;
    private String projectName;
    private String unitCategory;
    private int unitConstructionYear;
    private String unitType;
    private String unitUrl;
    private Date unitScanTime;

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public double getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(double unitSize) {
        this.unitSize = unitSize;
    }

    public double getUnitBalconySize() {
        return unitBalconySize;
    }

    public void setUnitBalconySize(double unitBalconySize) {
        this.unitBalconySize = unitBalconySize;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(String unitStatus) {
        this.unitStatus = unitStatus;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUnitCategory() {
        return unitCategory;
    }

    public void setUnitCategory(String unitCategory) {
        this.unitCategory = unitCategory;
    }

    public int getUnitConstructionYear() {
        return unitConstructionYear;
    }

    public void setUnitConstructionYear(int unitConstructionYear) {
        this.unitConstructionYear = unitConstructionYear;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitUrl() {
        return unitUrl;
    }

    public void setUnitUrl(String unitUrl) {
        this.unitUrl = unitUrl;
    }

    public Date getUnitScanTime() {
        return unitScanTime;
    }

    public void setUnitScanTime(Date unitScanTime) {
        this.unitScanTime = unitScanTime;
    }
}
