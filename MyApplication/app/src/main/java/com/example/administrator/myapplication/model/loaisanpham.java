package com.example.administrator.myapplication.model;

public class loaisanpham {
    public int Id;
    public String Tensp;
    public String Anhsp;

    public loaisanpham(int id, String tensp, String anhsp) {
        Id = id;
        Tensp = tensp;
        Anhsp = anhsp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public String getAnhsp() {
        return Anhsp;
    }

    public void setAnhsp(String anhsp) {
        Anhsp = anhsp;
    }
}
