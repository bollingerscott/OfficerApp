package com.pss.wsu.officersupport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteDbHelper extends SQLiteOpenHelper {

	public static final String TABLE_CASES = "cases";
	public static final String COLUMN_ID = "caseid";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCR = "description";

	private static final String DATABASE_NAME = "cases.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_CASES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_TITLE
			+ " text not null, " + COLUMN_DESCR
			+ " text not null);";

	public MySQLiteDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASES);
		onCreate(db);
	}

} 
