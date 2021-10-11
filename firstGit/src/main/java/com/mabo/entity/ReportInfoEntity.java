package com.mabo.entity;

public class ReportInfoEntity {
    String reportDate;
    String realReportDate;
    String bodyTemperature;
    String isConfirm;
    String isArea;
    String address;
    String uameId;
    public ReportInfoEntity(String reportDate, String realReportDate, String bodyTemperature, String isConfirm, String isArea, String address, String uameId) {
        this.reportDate = reportDate;
        this.realReportDate = realReportDate;
        this.bodyTemperature = bodyTemperature;
        this.isConfirm = isConfirm;
        this.isArea = isArea;
        this.address = address;
        this.uameId = uameId;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getRealReportDate() {
        return realReportDate;
    }

    public void setRealReportDate(String realReportDate) {
        this.realReportDate = realReportDate;
    }

    public String getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(String bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getIsArea() {
        return isArea;
    }

    public void setIsArea(String isArea) {
        this.isArea = isArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUameId() {
        return uameId;
    }

    public void setUameId(String uameId) {
        this.uameId = uameId;
    }
}
