package minhtvph26873.poly.pnlib.model;

public class ThuThu {
    private String MaTT;
    private String HoTen;
    private String MatKhau;

    public ThuThu() {
    }

    public ThuThu(String maTT, String hoTen, String matKhau) {
        MaTT = maTT;
        HoTen = hoTen;
        MatKhau = matKhau;
    }

    public String getMaTT() {
        return MaTT;
    }

    public void setMaTT(String maTT) {
        MaTT = maTT;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}
