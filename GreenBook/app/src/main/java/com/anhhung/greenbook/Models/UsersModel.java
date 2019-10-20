package com.anhhung.greenbook.Models;

import android.media.Image;
import android.widget.ImageView;

import java.util.Date;

public class UsersModel {
    private String idUser;
    private String hoTen;
    private String gioiTinh;
    private Date ngayThangNS;
    private Image hinhDaiDien;
    private String email;
    private String soDT;
    private Double tien;
    private int soSachDaMua;

    public UsersModel(String idUser, String hoTen, String gioiTinh,
                      Date ngayThangNS, Image hinhDaiDien, String email,
                      String soDT, Double tien, int soSachDaMua) {
        this.idUser = idUser;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngayThangNS = ngayThangNS;
        this.hinhDaiDien = hinhDaiDien;
        this.email = email;
        this.soDT = soDT;
        this.tien = tien;
        this.soSachDaMua = soSachDaMua;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Image getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(Image hinhDaiDien) {
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

    public Date getNgayThangNS() {
        return ngayThangNS;
    }

    public void setNgayThangNS(Date ngayThangNS) {
        this.ngayThangNS = ngayThangNS;
    }

    public Double getTien() {
        return tien;
    }

    public void setTien(Double tien) {
        this.tien = tien;
    }

    public int getSoSachDaMua() {
        return soSachDaMua;
    }

    public void setSoSachDaMua(int soSachDaMua) {
        this.soSachDaMua = soSachDaMua;
    }
}
