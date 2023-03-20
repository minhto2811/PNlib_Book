package minhtvph26873.poly.pnlib;

import android.content.Context;
import android.content.SharedPreferences;

import minhtvph26873.poly.pnlib.model.Admin;
import minhtvph26873.poly.pnlib.model.ThuThu;

import com.google.gson.Gson;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES_admin";
    private static final String MY_SHARED_PREFERENCES1 = "MY_SHARED_PREFERENCES";
    private final Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public void putValueAdmin(String key, Admin admin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String acc = new Gson().toJson(admin);
        editor.putString(key, acc);
        editor.apply();
    }

    public Admin getValueAdmin(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        return gson.fromJson(json, Admin.class);
    }

    public void putValueThuThu(String key, ThuThu thuThu) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String acc = new Gson().toJson(thuThu);
        editor.putString(key, acc);
        editor.apply();
    }

    public ThuThu getValueThuThu(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES1, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        return gson.fromJson(json, ThuThu.class);
    }
}
