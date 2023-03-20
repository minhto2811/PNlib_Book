package minhtvph26873.poly.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import minhtvph26873.poly.pnlib.Interface.InterfaceDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements InterfaceDAO<Admin> {

    private final SQLiteDatabase db;

    public AdminDAO(Context context) {
        DataBase dataBase = new DataBase(context);
        db = dataBase.getReadableDatabase();
    }

    @Override
    public long insert(Admin admin) {
        return 0;
    }

    @Override
    public long delete(String id) {
        return 0;
    }

    @Override
    public long update(Admin admin) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", admin.getPass());
        return db.update("admin", contentValues, "username=?", new String[]{admin.getUser()});
    }

    @Override
    public List<Admin> getAll() {
        List<Admin> admins = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM admin", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String user = cursor.getString(0);
                String pass = cursor.getString(1);
                admins.add(new Admin(user, pass));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return admins;
    }

    @Override
    public Admin getId(String id) {
        return null;
    }

    @Override
    public List<Admin> getData(String sql, String... selectionArgs) {
        return null;
    }
}
