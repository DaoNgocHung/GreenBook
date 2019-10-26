package com.anhhung.greenbook.Models;

import java.util.Map;

public class CategoriesModel {
    private String tenDanhMuc;
    private int anhBia;
    Map<String,BooksModel> booksModelMap;

    public CategoriesModel(String tenDanhMuc, int anhBia, Map<String, BooksModel> booksModelMap) {
        this.tenDanhMuc = tenDanhMuc;
        this.anhBia = anhBia;
        this.booksModelMap = booksModelMap;
    }

    public CategoriesModel(String tenDanhMuc, int anhBia) {
        this.tenDanhMuc = tenDanhMuc;
        this.anhBia = anhBia;
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
        return booksModelMap;
    }

    public void setBooksModelMap(Map<String, BooksModel> booksModelMap) {
        this.booksModelMap = booksModelMap;
    }
}
