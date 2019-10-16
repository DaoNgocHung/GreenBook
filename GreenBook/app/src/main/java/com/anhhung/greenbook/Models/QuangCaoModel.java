package com.anhhung.greenbook.Models;

import android.media.Image;

public class QuangCaoModel {
    private String idQC;
    private Image hinhQC;
    private String tenQC;
    private String idUser;

    public QuangCaoModel() {
    }

    public QuangCaoModel(String idQC, Image hinhQC, String tenQC, String idUser) {
        this.idQC = idQC;
        this.hinhQC = hinhQC;
        this.tenQC = tenQC;
        this.idUser = idUser;
    }

    public String getIdQC() {
        return idQC;
    }

    public void setIdQC(String idQC) {
        this.idQC = idQC;
    }

    public Image getHinhQC() {
        return hinhQC;
    }

    public void setHinhQC(Image hinhQC) {
        this.hinhQC = hinhQC;
    }

    public String getTenQC() {
        return tenQC;
    }

    public void setTenQC(String tenQC) {
        this.tenQC = tenQC;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
