package com.anhhung.greenbook.Models;

import com.google.firebase.Timestamp;

public class BillDetailModel {
    private String email;
    private Timestamp timePurchase;
    private String bookTitle;
    private double price;
    private String status;

    public BillDetailModel() {
    }

    public BillDetailModel(String email, Timestamp timePurchase, String bookTitle, double price, String status) {
        this.email = email;
        this.timePurchase = timePurchase;
        this.bookTitle = bookTitle;
        this.price = price;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getTimePurchase() {
        return timePurchase;
    }

    public void setTimePurchase(Timestamp timePurchase) {
        this.timePurchase = timePurchase;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
