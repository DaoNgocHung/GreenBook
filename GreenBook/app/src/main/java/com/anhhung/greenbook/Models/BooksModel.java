package com.anhhung.greenbook.Models;

import android.media.Image;

public class BooksModel {
    private String idSach;
    private String tenSach;
    private float danhGia;
    private Image biaSach;
    private String danhMuc;
    private int giamGia;
    private String gioiThieuSach;
    private boolean ktMua;
    private String noiDung;

    public BooksModel(String tenSach) {
        this.tenSach = tenSach;
    }

    private String NXB;
    private String ngayUpload;
    private String ngonNgu;
    private long soNguoiMua;
    private long soTRang;
    private String tacGia;
    private boolean yeuThich;

    public BooksModel(String idSach, String tenSach, float danhGia, Image biaSach,
                      String danhMuc, int giamGia, String gioiThieuSach, boolean ktMua,
                      String noiDung, String NXB, String ngayUpload, String ngonNgu,
                      long soNguoiMua, long soTRang, String tacGia, boolean yeuThich) {
        this.idSach = idSach;
        this.tenSach = tenSach;
        this.danhGia = danhGia;
        this.biaSach = biaSach;
        this.danhMuc = danhMuc;
        this.giamGia = giamGia;
        this.gioiThieuSach = gioiThieuSach;
        this.ktMua = ktMua;
        this.noiDung = noiDung;
        this.NXB = NXB;
        this.ngayUpload = ngayUpload;
        this.ngonNgu = ngonNgu;
        this.soNguoiMua = soNguoiMua;
        this.soTRang = soTRang;
        this.tacGia = tacGia;
        this.yeuThich = yeuThich;
    }

    public BooksModel() {
    }

    public String getIdSach() {
        return idSach;
    }

    public void setIdSach(String idSach) {
        this.idSach = idSach;
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

    public Image getBiaSach() {
        return biaSach;
    }

    public void setBiaSach(Image biaSach) {
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

    public boolean isKtMua() {
        return ktMua;
    }

    public void setKtMua(boolean ktMua) {
        this.ktMua = ktMua;
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

    public String getNgayUpload() {
        return ngayUpload;
    }

    public void setNgayUpload(String ngayUpload) {
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

    public long getSoTRang() {
        return soTRang;
    }

    public void setSoTRang(long soTRang) {
        this.soTRang = soTRang;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public boolean isYeuThich() {
        return yeuThich;
    }

    public void setYeuThich(boolean yeuThich) {
        this.yeuThich = yeuThich;
    }
}
