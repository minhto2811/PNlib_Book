package minhtvph26873.poly.pnlib.model;

public class ThanhVien {
    private int MaTV;
    private String HoTen;
    private String NamSinh;

    private String GioiTinh;

    public ThanhVien() {
    }

    public ThanhVien(int maTV, String hoTen, String namSinh, String GioiTinh) {
        MaTV = maTV;
        HoTen = hoTen;
        NamSinh = namSinh;
        GioiTinh = GioiTinh;
    }

    public int getMaTV() {
        return MaTV;
    }

    public void setMaTV(int maTV) {
        MaTV = maTV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String namSinh) {
        NamSinh = namSinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }
}
