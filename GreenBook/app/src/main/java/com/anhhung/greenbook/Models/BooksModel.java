package com.anhhung.greenbook.Models;

import android.media.Image;

import com.google.firebase.Timestamp;

public class BooksModel {
    private String tenSach;
    private float danhGia;
    private String biaSach;
    private String danhMuc;
    private int giamGia;
    private String gioiThieuSach;
    private String noiDung;   //change to epub
    private double giaTien;

    public BooksModel(String tenSach) {
        this.tenSach = tenSach;
    }

    private String NXB;
    private Timestamp ngayUpload;
    private String ngonNgu;
    private long soNguoiMua;
    private String tacGia;
    private long luotDanhGia;
    private String idDM;
    private boolean trangThai;

    public BooksModel(String tenSach, float danhGia, String biaSach, String danhMuc, int giamGia, String gioiThieuSach,
                      String noiDung, double giaTien, String NXB, Timestamp ngayUpload, String ngonNgu, long soNguoiMua,
                      String tacGia, long luotDanhGia, String idDM, boolean trangThai) {
        this.tenSach = tenSach;
        this.danhGia = danhGia;
        this.biaSach = biaSach;
        this.danhMuc = danhMuc;
        this.giamGia = giamGia;
        this.gioiThieuSach = gioiThieuSach;
        this.noiDung = noiDung;
        this.giaTien = giaTien;
        this.NXB = NXB;
        this.ngayUpload = ngayUpload;
        this.ngonNgu = ngonNgu;
        this.soNguoiMua = soNguoiMua;
        this.tacGia = tacGia;
        this.luotDanhGia = luotDanhGia;
        this.idDM = idDM;
        this.trangThai = trangThai;
    }

    public BooksModel() {
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public float getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(float danhGia) {
        this.danhGia = danhGia;
    }

    public String getBiaSach() {
        return biaSach;
    }

    public void setBiaSach(String biaSach) {
        this.biaSach = biaSach;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public String getGioiThieuSach() {
        return gioiThieuSach;
    }

    public void setGioiThieuSach(String gioiThieuSach) {
        this.gioiThieuSach = gioiThieuSach;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public Timestamp getNgayUpload() {
        return ngayUpload;
    }

    public void setNgayUpload(Timestamp ngayUpload) {
        this.ngayUpload = ngayUpload;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public long getSoNguoiMua() {
        return soNguoiMua;
    }

    public void setSoNguoiMua(long soNguoiMua) {
        this.soNguoiMua = soNguoiMua;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public long getLuotDanhGia() {
        return luotDanhGia;
    }

    public void setLuotDanhGia(long luotDanhGia) {
        this.luotDanhGia = luotDanhGia;
    }

    public String getIdDM() {
        return idDM;
    }

    public void setIdDM(String idDM) {
        this.idDM = idDM;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
