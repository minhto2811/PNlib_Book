package minhtvph26873.poly.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import minhtvph26873.poly.pnlib.Interface.InterfaceDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO implements InterfaceDAO<LoaiSach> {
    private final SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        DataBase dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();
    }

    @Override
    public long insert(LoaiSach loaiSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenLoai());
        return db.insert("loaisach", null, contentValues);
    }

    @Override
    public long delete(String id) {
        return db.delete("loaisach", "maloai=?", new String[]{id});
    }

    @Override
    public long update(LoaiSach loaiSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenLoai());
        return db.update("loaisach", contentValues, "maloai=?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }

    @Override
    public List<LoaiSach> getAll() {
        String sql = "SELECT * FROM loaisach";
        return getData(sql);
    }

    @Override
    public LoaiSach getId(String id) {
        String sql = "SELECT * FROM loaisach WHERE maloai = ?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }

    @Override
    public List<LoaiSach> getData(String sql, String... selectionArgs) {
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(0));
                loaiSach.setTenLoai(cursor.getString(1));
                list.add(loaiSach);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
