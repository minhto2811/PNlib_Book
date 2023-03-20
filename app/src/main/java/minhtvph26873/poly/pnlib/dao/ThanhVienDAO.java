package minhtvph26873.poly.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import minhtvph26873.poly.pnlib.Interface.InterfaceDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO implements InterfaceDAO<ThanhVien> {
    private final SQLiteDatabase db;

    public ThanhVienDAO(Context context) {
        DataBase dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();
    }

    @Override
    public long insert(ThanhVien thanhVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", thanhVien.getHoTen());
        contentValues.put("namsinh", thanhVien.getNamSinh());
        contentValues.put("gioitinh", thanhVien.getGioiTinh());

        return db.insert("thanhvien", null, contentValues);
    }

    @Override
    public long delete(String id) {
        return db.delete("thanhvien", "matv=?", new String[]{id});
    }

    @Override
    public long update(ThanhVien thanhVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", thanhVien.getHoTen());
        contentValues.put("namsinh", thanhVien.getNamSinh());
        contentValues.put("gioitinh", thanhVien.getGioiTinh());
        return db.update("thanhvien", contentValues, "matv=?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    @Override
    public List<ThanhVien> getAll() {
        String sql = "SELECT * FROM thanhvien";
        return getData(sql);
    }

    @Override
    public ThanhVien getId(String id) {
        String sql = "SELECT * FROM thanhvien WHERE matv=?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);
    }

    @Override
    public List<ThanhVien> getData(String sql, String... selectionArgs) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setMaTV(Integer.parseInt(cursor.getString(0)));
                thanhVien.setHoTen(cursor.getString(1));
                thanhVien.setNamSinh(cursor.getString(2));
                thanhVien.setGioiTinh(cursor.getString(3));
                list.add(thanhVien);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

}
