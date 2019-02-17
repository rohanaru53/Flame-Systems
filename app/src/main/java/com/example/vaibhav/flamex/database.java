package com.example.vaibhav.flamex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class database {
    public static final String KEY_ID = "rowid";
    public static final String KEY_NAME = "name";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_BARCODE = "barcode";
    public static final String KEY_UNITS = "units";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_CRITICALQUANTITY = "criticalquantity";
    public static final String KEY_PRICE = "price";
    private static final String DATABASE_NAME1 = "productdb";
    private static final String DATABASE_TABLE1 = "producttable";
    private static final int DATABASE_VERSION = 1;
    private DbHelper1 DbHelper1;
    private Context ourcontext1;
    ArrayList<ProductList> namelist = new ArrayList<ProductList>();
    private SQLiteDatabase ourdatabase1;

    public database(Context c) {
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
                    + " TEXT NOT NULL, " + KEY_CATEGORY + " TEXT NOT NULL,"
                    + KEY_WEIGHT + " TEXT NOT NULL," + KEY_CRITICALQUANTITY
                    + " TEXT NOT NULL," + KEY_BARCODE + " TEXT NOT NULL,"
                    + KEY_UNITS + " TEXT NOT NULL," + KEY_QUANTITY
                    + " TEXT NOT NULL," + KEY_PRICE + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
            arg0.execSQL("DROP TABLE IF EXITS " + DATABASE_TABLE1);
            onCreate(arg0);
        }
    }

    public database Open() throws SQLException {
        // TODO Auto-generated method stub

        DbHelper1 = new DbHelper1(ourcontext1);
        ourdatabase1 = DbHelper1.getWritableDatabase();
        return this;

    }

    public long CreateEntry(String sname, String category, String weight,
                            String barcode, String units,
                            String quantity, String price) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, sname);
        cv.put(KEY_BARCODE, barcode);
        cv.put(KEY_CATEGORY, category);
        cv.put(KEY_WEIGHT, weight);
        cv.put(KEY_UNITS, units);
        cv.put(KEY_QUANTITY, quantity);
        cv.put(KEY_PRICE, price);
        cv.put(KEY_CRITICALQUANTITY, "10");
        return ourdatabase1.insert(DATABASE_TABLE1, null, cv);
    }

    public void close() {
        // TODO Auto-generated method stub
        DbHelper1.close();
    }

    public boolean checknameexists(String sname, String category,
                                   String weight, String unit) {
        // TODO Auto-generated method stub
        String[] columns = {KEY_NAME, KEY_CATEGORY, KEY_WEIGHT, KEY_UNITS};
        Cursor cursor = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'" + " AND " + KEY_CATEGORY + "='" + category
                + "'" + " AND " + KEY_WEIGHT + "='" + weight + "'" + " AND "
                + KEY_UNITS + "='" + unit + "'", null, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateinventory(String sname, String category, String quantity, String price) {
        // TODO Auto-generated method stub
        String[] columns = {KEY_NAME, KEY_CATEGORY, KEY_QUANTITY, KEY_PRICE};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'" + " AND " + KEY_CATEGORY + "='" + category + "'", null, null, null, null);
        c.moveToNext();
        Integer x = Integer.parseInt(c.getString(c.getColumnIndex(KEY_QUANTITY)))+Integer.parseInt(quantity);
        String x1=x.toString();
        ContentValues cv = new ContentValues();
        cv.put(KEY_QUANTITY, x1);
        cv.put(KEY_PRICE, price);
        ourdatabase1.update(DATABASE_TABLE1, cv, KEY_NAME + "='" + sname + "'"
                    + " AND " + KEY_CATEGORY + "='" + category + "'", null);
        return true;
    }
    public String getprice(String sname, String category) {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME, KEY_CATEGORY,KEY_PRICE};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'" + " AND " + KEY_CATEGORY + "='" + category + "'", null, null, null, null);
        c.moveToNext();
        return c.getString(c.getColumnIndex(KEY_PRICE));
    }
    public String getcategory(String sname) {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME, KEY_CATEGORY};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'", null, null, null, null);
        c.moveToNext();
        return c.getString(c.getColumnIndex(KEY_CATEGORY));
    }

    public boolean updatingquantity(String sname, String category,String billquantity) {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME, KEY_CATEGORY,KEY_QUANTITY};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'" + " AND " + KEY_CATEGORY + "='" + category + "'", null, null, null, null);
        c.moveToNext();
        Integer x = Integer.parseInt(c.getString(c.getColumnIndex(KEY_QUANTITY)))-Integer.parseInt(billquantity);
        String x1=x.toString();
        ContentValues cv = new ContentValues();
        cv.put(KEY_QUANTITY,x1);
        ourdatabase1.update(DATABASE_TABLE1, cv, KEY_NAME + "='" + sname + "'"
                + " AND " + KEY_CATEGORY + "='" + category + "'", null);
        return true;
        }

        public ArrayList<ProductList> addingcategorydata(String category) {
        String[] columns = { KEY_NAME, KEY_CATEGORY, KEY_WEIGHT, KEY_UNITS,
                KEY_QUANTITY, KEY_PRICE ,KEY_CRITICALQUANTITY};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_CATEGORY
                + "='" + category + "'", null, null, null, null);
        while (c.moveToNext()) {
            String u = c.getString(c.getColumnIndex(KEY_NAME));
            String u1 = c.getString(c.getColumnIndex(KEY_CATEGORY));
            String v = c.getString(c.getColumnIndex(KEY_WEIGHT));
            String w = c.getString(c.getColumnIndex(KEY_UNITS));
            String x = c.getString(c.getColumnIndex(KEY_QUANTITY));
            String y = c.getString(c.getColumnIndex(KEY_PRICE));
            String z = c.getString(c.getColumnIndex(KEY_CRITICALQUANTITY));
            namelist.add(new ProductList(u, u1, v, w, x, y, z));
        }
        return namelist;

    }

    public ArrayList<ProductList> addingalldata() {
        String[] columns = { KEY_NAME, KEY_CATEGORY, KEY_WEIGHT, KEY_UNITS,
                KEY_QUANTITY, KEY_PRICE,KEY_CRITICALQUANTITY};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, null, null,
                null, null, null);
        while (c.moveToNext()) {
            String u = c.getString(c.getColumnIndex(KEY_NAME));
            String u1 = c.getString(c.getColumnIndex(KEY_CATEGORY));
            String v = c.getString(c.getColumnIndex(KEY_WEIGHT));
            String w = c.getString(c.getColumnIndex(KEY_UNITS));
            String x = c.getString(c.getColumnIndex(KEY_QUANTITY));
            String y = c.getString(c.getColumnIndex(KEY_PRICE));
            String z = c.getString(c.getColumnIndex(KEY_CRITICALQUANTITY));
            namelist.add(new ProductList(u, u1, v, w, x, y, z));
        }
        return namelist;

    }

    public boolean delete(String category, String ename) {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME, KEY_CATEGORY };
        Cursor cursor = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + ename + "'" + " AND " + KEY_CATEGORY + "='" + category
                + "'", null, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            ourdatabase1.delete(DATABASE_TABLE1, KEY_NAME + "='" + ename + "'"
                    + " AND " + KEY_CATEGORY + "='" + category + "'", null);
            return true;
        }
    }

    public int getquantity(String sname, String category) {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME, KEY_CATEGORY,KEY_QUANTITY};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'" + " AND " + KEY_CATEGORY + "='" + category + "'", null, null, null, null);
        c.moveToNext();
        return Integer.parseInt(c.getString(c.getColumnIndex(KEY_QUANTITY)));
    }
    public String getpquantity(String sname, String category) {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME, KEY_CATEGORY,KEY_QUANTITY};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'" + " AND " + KEY_CATEGORY + "='" + category + "'", null, null, null, null);
        c.moveToNext();
        return (c.getString(c.getColumnIndex(KEY_QUANTITY)));
    }
    public int getCriticalquantity(String sname, String category) {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME, KEY_CATEGORY,KEY_CRITICALQUANTITY};
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, KEY_NAME
                + "='" + sname + "'" + " AND " + KEY_CATEGORY + "='" + category + "'", null, null, null, null);
        c.moveToNext();
        return Integer.parseInt(c.getString(c.getColumnIndex(KEY_CRITICALQUANTITY)));
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

    public ArrayList<notificationlist> addingalldatainnotificationlist() {
        // TODO Auto-generated method stub
        String[] columns = { KEY_NAME,KEY_CATEGORY,KEY_QUANTITY,KEY_CRITICALQUANTITY };
        ArrayList<notificationlist> notificationlist = new ArrayList<notificationlist>();
        Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns, null, null,
                null, null, null);
        while (c.moveToNext()) {
            if(Integer.parseInt(c.getString(c.getColumnIndex(KEY_QUANTITY)))<=Integer.parseInt(c.getString(c.getColumnIndex(KEY_CRITICALQUANTITY))))
            {String u = c.getString(c.getColumnIndex(KEY_NAME));
                String v=c.getString(c.getColumnIndex(KEY_CATEGORY));
                String w=c.getString(c.getColumnIndex(KEY_QUANTITY));
                notificationlist.add(new notificationlist(u, v,w));}
        }
        return notificationlist;

    }



}

