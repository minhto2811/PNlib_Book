package minhtvph26873.poly.pnlib;

import android.app.Application;

import minhtvph26873.poly.pnlib.model.Admin;
import minhtvph26873.poly.pnlib.model.ThuThu;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
//        DataLocalManager.setFirstInstall(new Admin("null","null"));
//        DataLocalManager.setFirstInstall1(new ThuThu("null","null","null"));
    }
}


