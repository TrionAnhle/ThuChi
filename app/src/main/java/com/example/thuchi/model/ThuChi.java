package com.example.thuchi.model;

public class ThuChi {
    private Integer id,idCongViec,soTien;
    private String ngay, dau,tenCongViec;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCongViec() {
        return idCongViec;
    }

    public void setIdCongViec(Integer idCongViec) {
        this.idCongViec = idCongViec;
    }

    public Integer getSoTien() {
        return soTien;
    }

    public void setSoTien(Integer soTien) {
        this.soTien = soTien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
