package com.example.thuchi.model;

public class CongViec {
    private Integer id;
    private String dau,tenCongViec;

    public CongViec(){}
    public CongViec(String dau, String tenCongViec){
        this.dau = dau;
        this.tenCongViec = tenCongViec;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDau() {
        return dau;
    }

    public void setDau(String dau) {
        this.dau = dau;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }
}
