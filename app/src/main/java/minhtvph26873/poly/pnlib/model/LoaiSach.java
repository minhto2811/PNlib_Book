package minhtvph26873.poly.pnlib.model;

public class LoaiSach {
    private int MaLoai;
    private String TenLoai;

    public LoaiSach() {
    }

    public LoaiSach(int maLoai, String tenLoai) {
        MaLoai = maLoai;
        TenLoai = tenLoai;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }
}
