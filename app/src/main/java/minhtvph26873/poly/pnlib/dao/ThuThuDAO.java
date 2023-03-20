package minhtvph26873.poly.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import minhtvph26873.poly.pnlib.Interface.InterfaceDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO implements InterfaceDAO<ThuThu> {
    private final SQLiteDatabase db;

    public ThuThuDAO(Context context) {
        DataBase dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();
    }

    @Override
    public long insert(ThuThu thuThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", thuThu.getMaTT());
        contentValues.put("hoten", thuThu.getHoTen());
        contentValues.put("matkhau", thuThu.getMatKhau());
        return db.insert("thuthu", null, contentValues);
    }

    @Override
    public long delete(String id) {
        return db.delete("thuthu", "matt=?", new String[]{id});
    }

    @Override
    public long update(ThuThu thuThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", thuThu.getMaTT());
        contentValues.put("hoten", thuThu.getHoTen());
        contentValues.put("matkhau", thuThu.getMatKhau());
        return db.update("thuthu", contentValues, "matt=?", new String[]{thuThu.getMaTT()});
    }

    @Override
    public List<ThuThu> getAll() {
        String sql = "SELECT * FROM thuthu";
        return getData(sql);
    }

    @Override
    public ThuThu getId(String id) {
        String sql = "SELECT * FROM thuthu WHERE matt = ?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }

    @Override
    public List<ThuThu> getData(String sql, String... selectionArgs) {
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(cursor.getString(0));
                thuThu.setHoTen(cursor.getString(1));
                thuThu.setMatKhau(cursor.getString(2));
                list.add(thuThu);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
