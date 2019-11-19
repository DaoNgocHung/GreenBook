package com.anhhung.greenbook.Models;

public class DanhGiaModel {
    private String userName;
    private Float rateStar;

    public DanhGiaModel() {
    }

    public DanhGiaModel(String userName, Float rateStar) {
        this.userName = userName;
        this.rateStar = rateStar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getRateStar() {
        return rateStar;
    }

    public void setRateStar(Float rateStar) {
        this.rateStar = rateStar;
    }
}
