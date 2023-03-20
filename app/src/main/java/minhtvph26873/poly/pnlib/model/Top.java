package minhtvph26873.poly.pnlib.model;

public class Top {
    private String TenSach;
    private int SoLuong;

    public Top() {
    }

    public Top(String tenSach, int soLuong) {
        TenSach = tenSach;
        SoLuong = soLuong;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}
