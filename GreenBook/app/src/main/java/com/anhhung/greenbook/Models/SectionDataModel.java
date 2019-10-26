package com.anhhung.greenbook.Models;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class SectionDataModel {
    private String headerTitle;
    private ArrayList<BooksModel> allBooksInSection;
    private ArrayList<String> imgList;

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String > imgList) {
        this.imgList = imgList;
    }

    public SectionDataModel() {

    }

    public SectionDataModel(String headerTitle, ArrayList<BooksModel> allBooksInSection, ArrayList<String> imgList) {
        this.headerTitle = headerTitle;
        this.allBooksInSection = allBooksInSection;
        this.imgList = imgList;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<BooksModel> getAllBooksInSection() {
        return allBooksInSection;
    }

    public void setAllItemsInSection(ArrayList<BooksModel> allBooksInSection) {
        this.allBooksInSection = allBooksInSection;
    }

}
