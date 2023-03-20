package minhtvph26873.poly.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import minhtvph26873.poly.pnlib.Interface.InterfaceDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.PhieuMuon;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO implements InterfaceDAO<PhieuMuon> {
    private final SQLiteDatabase db;

    public PhieuMuonDAO(Context context) {
        DataBase dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();
    }

    @Override
    public long insert(PhieuMuon phieuMuon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", phieuMuon.getMaTT());
        contentValues.put("matv", phieuMuon.getMaTV());
        contentValues.put("masach", phieuMuon.getMaSach());
        contentValues.put("ngay", phieuMuon.getNgay());
        contentValues.put("trasach", phieuMuon.getTraSach());
        contentValues.put("tienthue", phieuMuon.getTienThue());
        return db.insert("phieumuon", null, contentValues);
    }

    @Override
    public long delete(String id) {
        return db.delete("phieumuon", "mapm=?", new String[]{id});
    }

    @Override
    public long update(PhieuMuon phieuMuon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", phieuMuon.getMaTT());
        contentValues.put("matv", phieuMuon.getMaTV());
        contentValues.put("masach", phieuMuon.getMaSach());
        contentValues.put("ngay", phieuMuon.getNgay());
        contentValues.put("trasach", phieuMuon.getTraSach());
        contentValues.put("tienthue", phieuMuon.getTienThue());
        return db.update("phieumuon", contentValues, "mapm=?", new String[]{String.valueOf(phieuMuon.getMaPhieuMuon())});
    }

    @Override
    public List<PhieuMuon> getAll() {
        String sql = "SELECT * FROM phieumuon";
        return getData(sql);
    }

    @Override
    public PhieuMuon getId(String id) {
        String sql = "SELECT * FROM phieumuon WHERE mapm = ?";
        List<PhieuMuon> list = getData(sql, id);
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }

    }

    @Override
    public List<PhieuMuon> getData(String sql, String... selectionArgs) {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPhieuMuon(cursor.getInt(0));
                phieuMuon.setMaTT(cursor.getString(1));
                phieuMuon.setMaTV(cursor.getInt(2));
                phieuMuon.setMaSach(cursor.getInt(3));
                phieuMuon.setNgay(cursor.getString(4));
                phieuMuon.setTraSach(cursor.getInt(5));
                phieuMuon.setTienThue(cursor.getInt(6));
                list.add(phieuMuon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

}
