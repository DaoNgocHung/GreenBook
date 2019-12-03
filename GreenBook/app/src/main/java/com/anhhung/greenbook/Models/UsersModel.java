package com.anhhung.greenbook.Models;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.Date;

public class UsersModel {
    private String hoTen;
    private boolean gioiTinh;
    private Timestamp ngayThangNS;
    private String hinhDaiDien;
    private String email;
    private String soDT;
    private Double tien;
    private String quyen;
    private long soSachDaMua;

    public UsersModel() {
    }

    public UsersModel(String hoTen, boolean gioiTinh,
                       Timestamp ngayThangNS, String hinhDaiDien, String email, String quyen,
                       String soDT, Double tien, long soSachDaMua) {
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngayThangNS = ngayThangNS;
        this.hinhDaiDien = hinhDaiDien;
        this.email = email;
        this.quyen = quyen;
        this.soDT = soDT;
        this.tien = tien;
        this.soSachDaMua = soSachDaMua;
    }

    public boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(String hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Timestamp getNgayThangNS() {
        return ngayThangNS;
    }

    public void setNgayThangNS(Timestamp ngayThangNS) {
        this.ngayThangNS = ngayThangNS;
    }

    public Double getTien() {
        return tien;
    }

    public void setTien(Double tien) {
        this.tien = tien;
    }

    public long getSoSachDaMua() {
        return soSachDaMua;
    }

    public void setSoSachDaMua(long soSachDaMua) {
        this.soSachDaMua = soSachDaMua;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }
}