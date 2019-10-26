package com.anhhung.greenbook.Models;

import java.util.Map;

public class CategoriesModel {
    private String tenDanhMuc;
    private int anhBia;
    private Map<String,BooksModel> SachCollection;

    public CategoriesModel(String tenDanhMuc, int anhBia, Map<String, BooksModel> SachCollection) {
        this.tenDanhMuc = tenDanhMuc;
        this.anhBia = anhBia;
        this.SachCollection = SachCollection;
    }

    public CategoriesModel(String tenDanhMuc, int anhBia) {
        this.tenDanhMuc = tenDanhMuc;
        this.anhBia = anhBia;
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

    public int getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(int anhBia) {
        this.anhBia = anhBia;
    }

    public Map<String, BooksModel> getBooksModelMap() {
        return SachCollection;
    }

    public void setBooksModelMap(Map<String, BooksModel> booksModelMap) {
        this.SachCollection = booksModelMap;
    }
}
