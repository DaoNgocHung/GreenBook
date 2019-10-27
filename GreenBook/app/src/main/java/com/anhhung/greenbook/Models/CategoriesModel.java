package com.anhhung.greenbook.Models;

        import java.util.List;
        import java.util.Map;

public class CategoriesModel {
    private String tenDanhMuc;
    private String anhBia;
    private String id;
    List<BooksModel> booksModels;

    public CategoriesModel(String tenDanhMuc, String anhBia, String id, List<BooksModel> booksModels) {
        this.tenDanhMuc = tenDanhMuc;
        this.anhBia = anhBia;
        this.id = id;
        this.booksModels = booksModels;
    }

    public CategoriesModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BooksModel> getBooksModels() {
        return booksModels;
    }

    public void setBooksModels(List<BooksModel> booksModels) {
        this.booksModels = booksModels;
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
