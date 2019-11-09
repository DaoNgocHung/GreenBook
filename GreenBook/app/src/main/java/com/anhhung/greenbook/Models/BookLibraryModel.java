package com.anhhung.greenbook.Models;

import com.google.firebase.Timestamp;

public class BookLibraryModel {
    private String tenSach, biaSach, noiDung, tacGia, NXB, danhMuc, ngonNgu;
    private long giaTien;
    private Timestamp ngayMua;

    public BookLibraryModel() {
    }

    public BookLibraryModel(String tenSach, String biaSach, String noiDung, String tacGia, String NXB, String danhMuc, long giaTien, String ngonNgu, Timestamp ngayMua) {
        this.tenSach = tenSach;
        this.biaSach = biaSach;
        this.noiDung = noiDung;
        this.tacGia = tacGia;
        this.NXB = NXB;
        this.danhMuc = danhMuc;
        this.giaTien = giaTien;
        this.ngayMua = ngayMua;
        this.ngonNgu = ngonNgu;

    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getBiaSach() {
        return biaSach;
    }

    public void setBiaSach(String biaSach) {
        this.biaSach = biaSach;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public long getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(long giaTien) {
        this.giaTien = giaTien;
    }

    public Timestamp getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Timestamp ngayMua) {
        this.ngayMua = ngayMua;
    }
}
