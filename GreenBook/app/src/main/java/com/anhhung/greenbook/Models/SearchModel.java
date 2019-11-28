package com.anhhung.greenbook.Models;

public class SearchModel {
    private String biaSach;
    private String tenSach;
    private String NXB;
    private String tacGia;

    public SearchModel() {
    }

    public SearchModel(String biaSach, String tenSach, String NXB, String tacGia) {
        this.biaSach = biaSach;
        this.tenSach = tenSach;
        this.NXB = NXB;
        this.tacGia = tacGia;
    }

    public String getBiaSach() {
        return biaSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getNXB() {
        return NXB;
    }

    public String getTacGia() {
        return tacGia;
    }
}
