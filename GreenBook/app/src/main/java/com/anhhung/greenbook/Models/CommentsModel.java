package com.anhhung.greenbook.Models;


import com.google.firebase.Timestamp;

public class CommentsModel {
    private String tenSach;
    private String hoTen;
    private Timestamp tgBinhLuan;
    private String noidungBL;
    private String hinhDaiDien;

    public CommentsModel() {
    }

    public CommentsModel(String tenSach, String hoTen, Timestamp tgBinhLuan, String noidungBL, String hinhDaiDien) {
        this.tenSach = tenSach;
        this.hoTen = hoTen;
        this.tgBinhLuan = tgBinhLuan;
        this.noidungBL = noidungBL;
        this.hinhDaiDien = hinhDaiDien;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Timestamp getTgBinhLuan() {
        return tgBinhLuan;
    }

    public void setTgBinhLuan(Timestamp tgBinhLuan) {
        this.tgBinhLuan = tgBinhLuan;
    }

    public String getNoidungBL() {
        return noidungBL;
    }

    public void setNoidungBL(String noidungBL) {
        this.noidungBL = noidungBL;
    }

    public String getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(String hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }
}
