package com.pss.wsu.officersupport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteDbHelper extends SQLiteOpenHelper {

	public static final String TABLE_CASES = "cases";
	public static final String CASE_ID = "caseid";
	public static final String CASE_TITLE = "title";
	public static final String CASE_DESCR = "description";
	public static final String CASE_WITNESSES = "witnesses";
	public static final String CASE_SUSPECTS = "suspects";
	public static final String CASE_FORMS = "forms";

	private static final String DATABASE_NAME = "cases.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_CASES + "(" + CASE_ID
			+ " integer primary key, " + CASE_TITLE
			+ " text not null, " + CASE_DESCR
			+ " text not null, " + CASE_WITNESSES + " text, " + CASE_SUSPECTS + " text, " + CASE_FORMS + " text);";

	public MySQLiteDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		//database.execSQL("ALTER TABLE " + TABLE_CASES + "(ADD " + CASE_WITNESSES + " text, " + CASE_SUSPECTS + " text, " + CASE_FORMS + " text);");
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASES);
		onCreate(db);
	}

} 
