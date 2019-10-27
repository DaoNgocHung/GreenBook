package com.anhhung.greenbook.Models;


public class CategoriesModel {
    private String tenDanhMuc;
    private String anhBia;
    private String id;

    public CategoriesModel(String tenDanhMuc, String anhBia, String id) {
        this.tenDanhMuc = tenDanhMuc;
        this.anhBia = anhBia;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
