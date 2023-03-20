package minhtvph26873.poly.pnlib.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context) {
        super(context, "PNLib.sqlite", null, 1);
    }

    public void TaoBangAvatar(String tenBang){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("CREATE TABLE IF NOT EXISTS "+tenBang+"Avatar"+"(path TEXT)");
    }
    public void suaDulieuAvatar(String tenBang,String uri){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO "+tenBang+"Avatar"+" VALUES('"+uri+"')");
    }
    public void xoaDulieuAvatar(String tenBang){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM "+tenBang+"Avatar");
    }
    public String duLieuAvatar(String tenBang){
        List<String> list = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+tenBang+"Avatar",null);
        if(cursor != null){
            list.clear();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                list.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        if(list.size()>0){
            return list.get(list.size()-1);
        }else {
            return null;
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE admin(username NVARCHAR primary key,password NVARCHAR)");
        db.execSQL("CREATE TABLE thuthu(matt NVARCHAR primary key,hoten NVARCHAR,matkhau NVARCHAR)");
        db.execSQL("CREATE TABLE loaisach(maloai INTEGER primary key AUTOINCREMENT,tenloai NVARCHAR)");
        db.execSQL("CREATE TABLE sach(masach INTEGER primary key AUTOINCREMENT,tensach NVARCHAR,giathue INTEGER,maloai INTEGER, FOREIGN KEY(maloai) REFERENCES loaisach(maloai))");
        db.execSQL("CREATE TABLE thanhvien(matv INTEGER primary key AUTOINCREMENT, hoten NVARCHAR,namsinh DATE,gioitinh NVARCHAR)");
        db.execSQL("CREATE TABLE phieumuon(mapm INTEGER primary key AUTOINCREMENT,matt NVARCHAR,matv INTEGER,masach INTEGER,ngay DATE,trasach INTEGER,tienthue INTEGER,FOREIGN KEY(matt) REFERENCES thuthu(matt), " +
                "FOREIGN KEY(matv) REFERENCES thanhvien(matv), " +
                "FOREIGN KEY(masach) REFERENCES sach(masach), " +
                "FOREIGN KEY(trasach) REFERENCES trangthai(trasach))");
        db.execSQL("CREATE TABLE trangthai(trasach INTEGER primary key,mota NVARCHAR)");


        db.execSQL("INSERT INTO admin VALUES('admin','admin')");
        db.execSQL("INSERT INTO thuthu VALUES('tt001','Pham Thi Thuy Linh','tt001')");
        db.execSQL("INSERT INTO trangthai VALUES(0,'chua tra')");
        db.execSQL("INSERT INTO trangthai VALUES(1,'Da tra')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS admin");
        db.execSQL("DROP TABLE IF EXISTS thuthu");
        db.execSQL("DROP TABLE IF EXISTS loaisach");
        db.execSQL("DROP TABLE IF EXISTS sach");
        db.execSQL("DROP TABLE IF EXISTS thanhvien");
        db.execSQL("DROP TABLE IF EXISTS phieumuon");
        db.execSQL("DROP TABLE IF EXISTS trangthai");
    }


}
