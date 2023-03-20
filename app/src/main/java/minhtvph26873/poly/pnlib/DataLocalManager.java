package minhtvph26873.poly.pnlib;

import android.content.Context;

import minhtvph26873.poly.pnlib.model.Admin;
import minhtvph26873.poly.pnlib.model.ThuThu;

public class DataLocalManager {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setFirstInstall(Admin isFirst) {
        DataLocalManager.getInstance().mySharedPreferences.putValueAdmin(PREF_FIRST_INSTALL, isFirst);
    }

    public static Admin getFirstInstall() {
        return DataLocalManager.getInstance().mySharedPreferences.getValueAdmin(PREF_FIRST_INSTALL);
    }

    public static void setFirstInstall1(ThuThu isFirst1) {
        DataLocalManager.getInstance().mySharedPreferences.putValueThuThu(PREF_FIRST_INSTALL, isFirst1);
    }

    public static ThuThu getFirstInstall1() {
        return DataLocalManager.getInstance().mySharedPreferences.getValueThuThu(PREF_FIRST_INSTALL);
    }

}
