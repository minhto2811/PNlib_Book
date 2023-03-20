package minhtvph26873.poly.pnlib.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import minhtvph26873.poly.pnlib.Interface.InterfaceDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.TrangThai;

import java.util.ArrayList;
import java.util.List;

public class TrangThaiDAO implements InterfaceDAO<TrangThai> {
    private final SQLiteDatabase db;

    public TrangThaiDAO(Context context) {
        DataBase dataBase = new DataBase(context);
        db = dataBase.getReadableDatabase();
    }

    @Override
    public long insert(TrangThai trangThai) {
        return 0;
    }

    @Override
    public long delete(String id) {
        return 0;
    }

    @Override
    public long update(TrangThai trangThai) {
        return 0;
    }

    @Override
    public List<TrangThai> getAll() {
        String sql = "SELECT * FROM trangthai";
        return getData(sql);
    }

    @Override
    public TrangThai getId(String id) {
        return null;
    }

    @Override
    public List<TrangThai> getData(String sql, String... selectionArgs) {
        List<TrangThai> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TrangThai trangThai = new TrangThai();
                trangThai.setTrangThai(cursor.getInt(0));
                trangThai.setMoTa(cursor.getString(1));
                list.add(trangThai);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
