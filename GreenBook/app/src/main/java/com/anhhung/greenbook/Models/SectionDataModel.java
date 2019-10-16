package com.anhhung.greenbook.Models;

import java.util.ArrayList;

public class SectionDataModel {
    private String headerTitle;
    private ArrayList<BooksModel> allBooksInSection;


    public SectionDataModel() {

    }

    public SectionDataModel(String headerTitle, ArrayList<BooksModel> allBooksInSection) {
        this.headerTitle = headerTitle;
        this.allBooksInSection = allBooksInSection;
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
