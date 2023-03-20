package minhtvph26873.poly.pnlib.model;

public class PhieuMuon {
    private int MaPhieuMuon;
    private String MaTT;
    private int MaTV;
    private int MaSach;
    private String Ngay;
    private int TraSach;
    private int TienThue;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPhieuMuon, String maTT, int maTV, int maSach, String ngay, int traSach, int tienThue) {
        MaPhieuMuon = maPhieuMuon;
        MaTT = maTT;
        MaTV = maTV;
        MaSach = maSach;
        Ngay = ngay;
        TraSach = traSach;
        TienThue = tienThue;
    }

    public int getMaPhieuMuon() {
        return MaPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        MaPhieuMuon = maPhieuMuon;
    }

    public String getMaTT() {
        return MaTT;
    }

    public void setMaTT(String maTT) {
        MaTT = maTT;
    }

    public int getMaTV() {
        return MaTV;
    }

    public void setMaTV(int maTV) {
        MaTV = maTV;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public int getTraSach() {
        return TraSach;
    }

    public void setTraSach(int traSach) {
        TraSach = traSach;
    }

    public int getTienThue() {
        return TienThue;
    }

    public void setTienThue(int tienThue) {
        TienThue = tienThue;
    }
}
