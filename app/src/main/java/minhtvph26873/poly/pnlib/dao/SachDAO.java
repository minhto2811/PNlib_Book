package minhtvph26873.poly.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import minhtvph26873.poly.pnlib.Interface.InterfaceDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO implements InterfaceDAO<Sach> {

    private final SQLiteDatabase db;

    public SachDAO(Context context) {
        DataBase dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();
    }

    @Override
    public long insert(Sach sach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", sach.getTenSach());
        contentValues.put("giathue", sach.getGiaThue());
        contentValues.put("maloai", sach.getMaLoai());
        return db.insert("sach", null, contentValues);
    }

    @Override
    public long delete(String id) {
        return db.delete("sach", "masach=?", new String[]{id});
    }

    @Override
    public long update(Sach sach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", sach.getTenSach());
        contentValues.put("giathue", sach.getGiaThue());
        contentValues.put("maloai", sach.getMaLoai());
        return db.update("sach", contentValues, "masach=?", new String[]{String.valueOf(sach.getMaSach())});
    }

    @Override
    public List<Sach> getAll() {
        String sql = "SELECT * FROM sach";
        return getData(sql);
    }

    @Override
    public Sach getId(String id) {
        String sql = "SELECT * FROM sach WHERE masach = ?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }

    @Override
    public List<Sach> getData(String sql, String... selectionArgs) {
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaThue(cursor.getInt(2));
                sach.setMaLoai(cursor.getInt(3));
                list.add(sach);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }


}
