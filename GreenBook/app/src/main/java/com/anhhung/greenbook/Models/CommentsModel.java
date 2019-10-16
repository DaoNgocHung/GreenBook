package com.anhhung.greenbook.Models;

public class CommentsModel {
    private String idBinhLuan;
    private String idSach;
    private String tenNguoiDung;
    private String tgBinhLuan;
    private String noidungBL;

    public CommentsModel() {
    }

    public CommentsModel(String idBinhLuan, String idSach, String tenNguoiDung, String tgBinhLuan, String noidungBL) {
        this.idBinhLuan = idBinhLuan;
        this.idSach = idSach;
        this.tenNguoiDung = tenNguoiDung;
        this.tgBinhLuan = tgBinhLuan;
        this.noidungBL = noidungBL;
    }

    public String getIdBinhLuan() {
        return idBinhLuan;
    }

    public void setIdBinhLuan(String idBinhLuan) {
        this.idBinhLuan = idBinhLuan;
    }

    public String getIdSach() {
        return idSach;
    }

    public void setIdSach(String idSach) {
        this.idSach = idSach;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getTgBinhLuan() {
        return tgBinhLuan;
    }

    public void setTgBinhLuan(String tgBinhLuan) {
        this.tgBinhLuan = tgBinhLuan;
    }

    public String getNoidungBL() {
        return noidungBL;
    }

    public void setNoidungBL(String noidungBL) {
        this.noidungBL = noidungBL;
    }
}
