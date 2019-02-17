package com.example.vaibhav.flamex;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

@SuppressLint("SimpleDateFormat")
public class billdatabase {
	public static final String KEY_ID = "rowid";
	public static final String KEY_NAME = "name";
	public static final String KEY_QUANTITY = "quantity";
	public static final String KEY_PRICE = "price";
	public static final String KEY_TIMESTAMP = "timestamp";
	private static final String DATABASE_NAME1 = "billdb";
	private static final String DATABASE_TABLE1 = "billtable";
	private static final int DATABASE_VERSION = 1;
	private DbHelper1 DbHelper1;
	private Context ourcontext1;
	private SQLiteDatabase ourdatabase1;


	public billdatabase(Context c) {
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
					+ " TEXT NOT NULL, " + KEY_QUANTITY + " TEXT NOT NULL,"
					+ KEY_PRICE + " TEXT NOT NULL," + KEY_TIMESTAMP
					+ " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			arg0.execSQL("DROP TABLE IF EXITS " + DATABASE_TABLE1);
			onCreate(arg0);
		}

	}

	public billdatabase Open() throws SQLException {
		// TODO Auto-generated method stub
		DbHelper1 = new DbHelper1(ourcontext1);
		ourdatabase1 = DbHelper1.getWritableDatabase();
		return this;
	}

	public long CreateEntry(String sname, String quantity, String price,String formattedDate) {
		// TODO Auto-generated method stub
		sname=sname.toLowerCase();
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, sname);
		cv.put(KEY_QUANTITY, quantity);
		cv.put(KEY_PRICE, price);
		cv.put(KEY_TIMESTAMP, formattedDate);
		return ourdatabase1.insert(DATABASE_TABLE1, null, cv);
	}

	public void close() {
		// TODO Auto-generated method stub
		DbHelper1.close();
	}

	public boolean emptydatabase() {
		// TODO Auto-generated method stub
		ourdatabase1.delete(DATABASE_TABLE1, null, null);
		return true;
	}
	public ArrayList<billdatabaselist> addingnamedata(String name) {
		name=name.toLowerCase();
		String[] columns = {KEY_ID,KEY_NAME,KEY_QUANTITY, KEY_PRICE,KEY_TIMESTAMP};
		Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns,KEY_NAME
						+"='"+name+ "'", null,
				null, null, null);
		ArrayList<billdatabaselist> namelist=new ArrayList<billdatabaselist>();
		while (c.moveToNext()) {
			String i= c.getString(c.getColumnIndex(KEY_NAME));
			String x = c.getString(c.getColumnIndex(KEY_QUANTITY));
			String y = c.getString(c.getColumnIndex(KEY_PRICE));
			String z = c.getString(c.getColumnIndex(KEY_TIMESTAMP));
			namelist.add(new billdatabaselist(i,x, y, z));
		}
		return namelist;
	}
	public ArrayList<String>displayingtime()
	{
		ArrayList<String> timelist=new ArrayList<>();
		String[] columns = {KEY_TIMESTAMP};
		Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns,null, null,
				null, null, null);
		while (c.moveToNext()) {
			String z = c.getString(c.getColumnIndex(KEY_TIMESTAMP));
			timelist.add(z);
		}
		return timelist;
	}
	public ArrayList<billdatabaselist> addingtimedata(String time) {
		String[] columns = {KEY_NAME,KEY_QUANTITY, KEY_PRICE,KEY_TIMESTAMP};
		Cursor c = ourdatabase1.query(DATABASE_TABLE1, columns,KEY_TIMESTAMP
						+"='"+time+ "'", null,
				null, null, null);
		ArrayList<billdatabaselist> namelist=new ArrayList<billdatabaselist>();
		while (c.moveToNext()) {
			String i=c.getString(c.getColumnIndex(KEY_NAME));
			String x = c.getString(c.getColumnIndex(KEY_QUANTITY));
			String y = c.getString(c.getColumnIndex(KEY_PRICE));
			String z = c.getString(c.getColumnIndex(KEY_TIMESTAMP));
			namelist.add(new billdatabaselist(i,x, y, z));
		}
		return namelist;
	}
}
