package com.example.administrator.myapplication.model;

import java.io.Serializable;

public class sanpham implements Serializable{
    public int Id;
    public String tensanpham;
    public int giasanpham;
    public String motasanpham;
    public int Idsanpham;
    public String hinhanhsanpham;

    public sanpham(int id, String tensanpham, int giasanpham, String motasanpham, int idsanpham, String hinhanhsanpham) {
        Id = id;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.motasanpham = motasanpham;
        Idsanpham = idsanpham;
        this.hinhanhsanpham = hinhanhsanpham;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public int getIdsanpham() {
        return Idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        Idsanpham = idsanpham;
    }

    public String getHinhanhsanpham() {
        return hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        this.hinhanhsanpham = hinhanhsanpham;
    }
}
