package com.example.vaibhav.flamex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CategoriesDatabase {

    public static final String KEY_ID = "rowid";
    public static final String KEY_NAME = "name";
    private static final String DATABASE_NAME1 = "categoriesdb";
    private static final String DATABASE_TABLE1 = "categoriestable";
    private static final int DATABASE_VERSION = 1;
    private DbHelper1 DbHelper1;
    private Context ourcontext1;
    private SQLiteDatabase ourdatabase1;
    ArrayList<CategoryList> namelist = new ArrayList<CategoryList>();

    public CategoriesDatabase(Context c) {
        // TODO Auto-generated constructor stub
        ourcontext1 = c;
    }

    private class DbHelper1 extends SQLiteOpenHelper {

        public DbHelper1(Context context) {
            super(context, DATABASE_NAME1, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL("CREATE TABLE " + DATABASE_TABLE1 + " (" + KEY_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
                    + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
            arg0.execSQL("DROP TABLE IF EXITS " + DATABASE_TABLE1);
            onCreate(arg0);
        }
    }

    public CategoriesDatabase Open() throws SQLException {
        // TODO Auto-generated method stub
        DbHelper1 = new DbHelper1(ourcontext1);
        ourdatabase1 = DbHelper1.getWritableDatabase();
        return this;

    }

    public long CreateEntry(String sname) {
        // TODO Auto-generated method stub
        sname=sname.toLowerCase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, sname);
        return ourdatabase1.insert(DATABASE_TABLE1, null, cv);
    }

    public void close() {
        // TODO Auto-generated method stub
        DbHelper1.close();
    }

    public boolean checknameexists(String sname) {
        sname=sname.toLowerCase();
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME };
        Cursor cursor = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'", null, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<CategoryList> addingalldata() {
        String[] columns = { KEY_NAME };
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, null, null,
                null, null, null);
        while (c.moveToNext()) {
            String u = c.getString(c.getColumnIndex(KEY_NAME));
            namelist.add(new CategoryList(u));
        }
        return namelist;

    }

    public ArrayList<String> addingalldatainspinner() {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME };
        ArrayList<String> namelist = new ArrayList<String>();
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, null, null,
                null, null, null);
        while (c.moveToNext()) {
            String u = c.getString(c.getColumnIndex(KEY_NAME));
            namelist.add(u);
        }
        return namelist;

    }
}
