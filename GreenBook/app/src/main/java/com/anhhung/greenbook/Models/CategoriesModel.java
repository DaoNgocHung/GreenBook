package com.anhhung.greenbook.Models;

import java.util.Map;

public class CategoriesModel {
    private String tenDanhMuc;
    private String anhBia;

    public CategoriesModel(String tenDanhMuc, String anhBia) {
        this.tenDanhMuc = tenDanhMuc;
        this.anhBia = anhBia;
    }

    public CategoriesModel() {
    }

    public CategoriesModel (String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }

}
