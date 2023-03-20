package minhtvph26873.poly.pnlib.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.Sach;
import minhtvph26873.poly.pnlib.model.Top;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private final SQLiteDatabase db;
    private final Context context;

    public ThongKeDAO(Context context) {
        this.context = context;
        DataBase dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();
    }

    public List<Top> getTop() {
        String sqlTop = "SELECT masach, count(masach) as soluong FROM phieumuon GROUP BY masach ORDER BY soluong DESC LIMIT 10";
        List<Top> listtop = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor cursor = db.rawQuery(sqlTop, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Top top = new Top();
                Sach sach = sachDAO.getId(cursor.getString(0));
                top.setTenSach(sach.getTenSach());
                top.setSoLuong(cursor.getInt(1));
                listtop.add(top);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listtop;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        List<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT SUM(tienthue) as tongtien FROM phieumuon WHERE ngay BETWEEN ? AND ?", new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()) {
            try {
                list.add(Integer.valueOf(cursor.getString(cursor.getColumnIndex("tongtien"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        cursor.close();
        return list.get(0);
    }

}
