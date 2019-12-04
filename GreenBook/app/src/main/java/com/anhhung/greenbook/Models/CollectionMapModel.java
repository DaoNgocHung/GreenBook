package com.anhhung.greenbook.Models;

public class CollectionMapModel {
    private long tongSachBan;
    private Double tongDoanhThuTien;
    private String collectionMapName;
    private int thangThongKe;

    public CollectionMapModel() {
    }

    public CollectionMapModel(long tongSachBan, Double tongDoanhThuTien, String collectionMapName, int thangThongKe) {
        this.tongSachBan = tongSachBan;
        this.tongDoanhThuTien = tongDoanhThuTien;
        this.collectionMapName = collectionMapName;
        this.thangThongKe = thangThongKe;
    }

    public long getTongSachBan() {
        return tongSachBan;
    }

    public void setTongSachBan(long tongSachBan) {
        this.tongSachBan = tongSachBan;
    }

    public Double getTongDoanhThuTien() {
        return tongDoanhThuTien;
    }

    public void setTongDoanhThuTien(Double tongDoanhThuTien) {
        this.tongDoanhThuTien = tongDoanhThuTien;
    }

    public String getCollectionMapName() {
        return collectionMapName;
    }

    public void setCollectionMapName(String collectionMapName) {
        this.collectionMapName = collectionMapName;
    }
    public int getThangThongKe() {
        return thangThongKe;
    }

    public void setThangThongKe(int thangThongKe) {
        this.thangThongKe = thangThongKe;
    }
}
