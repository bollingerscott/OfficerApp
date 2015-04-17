package com.pss.wsu.officersupport;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteDbHelper extends SQLiteOpenHelper {

	//cases table => id, title, description
	public static final String TABLE_CASES = "cases";
	public static final String CASE_ID = "caseid";
	public static final String CASE_TITLE = "title";
	public static final String CASE_DESCR = "description";
	
	//forms table => id, name
	public static final String TABLE_FORMS = "forms";
	public static final String FORMS_ID = "formid";
	public static final String FORMS_NAME = "name";
	
	//Incident report table => incident report form fields, caseid
	public static final String TABLE_INCIDENTS = "incidents";
	public static final String INCIDENTS_ID = "incidentID";
	public static final String INCIDENTS_DISTRICT = "district";
	public static final String INCIDENTS_NUMBER = "number";
	public static final String INCIDENTS_TYPE = "type";
	public static final String INCIDENTS_TOD = "tod";
	public static final String INCIDENTS_TOA = "toa";
	public static final String INCIDENTS_TOC = "toc";
	public static final String INCIDENTS_REPORT_DATE = "reportdate";
	public static final String INCIDENTS_REPORT_TIME = "reporttime";
	public static final String INCIDENTS_CASEID = "caseid";
	
	//forms cases linking table => id, caseid, formid
	public static final String TABLE_FORMS_CASES = "forms_cases";
	public static final String FORMS_CASES_ID = "formcaseid";
	public static final String FORMS_CASES_CASEID = "caseid";
	public static final String FORMS_CASES_FORMID = "formid";
	
	/* 
	 * To find forms belonging to case:
	 * 1. Query forms_cases, to find forms
	 * 2. Lookup what form it is
	 * 3. Use result to look up the form from certain table
	 */
	
	//Persons table
	public static final String TABLE_PERSONS = "persons";
	public static final String PERSONS_ID = "personid";
	public static final String PERSONS_FNAME = "firstname";
	public static final String PERSONS_LNAME = "lastname";
	public static final String PERSONS_DESCR = "description";
	public static final String PERSONS_HEIGHT = "height";
	public static final String PERSONS_WEIGHT = "weight";
	public static final String PERSONS_ADDRESS = "address";
	public static final String PERSONS_PHONE = "phone";
	public static final String PERSONS_TYPE = "type";
	public static final String PERSONS_STATEMENT = "statement";
	
	//Persons cases linking table
	public static final String TABLE_PERSONS_CASES = "persons_cases";
	public static final String PERSONS_CASES_PERSONID = "personid";
	public static final String PERSONS_CASES_CASEID = "caseid";

	private static final String DATABASE_NAME = "cases.db";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_CASES = "create table "
			+ TABLE_CASES + "(" + CASE_ID
			+ " integer primary key, " + CASE_TITLE
			+ " text not null, " + CASE_DESCR
			+ " text not null); ";
	
	private static final String CREATE_FORMS = "create table "
			+ TABLE_FORMS + "(" + FORMS_ID
			+ " integer primary key, " + FORMS_NAME + " text); ";
	
	private static final String CREATE_INCIDENTS = "create table "
			+ TABLE_INCIDENTS + "(" + 
			INCIDENTS_ID + " integer primary key, " +
			INCIDENTS_DISTRICT + " text, " +
			INCIDENTS_NUMBER + " text, " +
			INCIDENTS_TYPE + " text, " +
			INCIDENTS_TOD + " text, " +
			INCIDENTS_TOA + " text, " +
			INCIDENTS_TOC + " text, " +
			INCIDENTS_REPORT_DATE + " text, " +
			INCIDENTS_REPORT_TIME + " text, " +
			INCIDENTS_CASEID + " integer not null, foreign key(" + 
			INCIDENTS_CASEID + ") references " + TABLE_CASES + "(" + CASE_ID + ")); ";
	
	private static final String CREATE_FORMS_CASES = "create table "
			+ TABLE_FORMS_CASES + "(" + 
			FORMS_CASES_ID + " integer primary key autoincrement, " +
			FORMS_CASES_CASEID + " integer, " +
			FORMS_CASES_FORMID + " integer, " +
			"foreign key(" + FORMS_CASES_CASEID + ") references " + TABLE_CASES + "(" + CASE_ID + "), " +
			"foreign key(" + FORMS_CASES_FORMID + ") references " + TABLE_FORMS + "(" + FORMS_ID + ")); ";
	
	private static final String CREATE_PERSONS = "create table " 
			+ TABLE_PERSONS + "(" +
			PERSONS_ID + " integer primary key, " +
			PERSONS_FNAME + " text, " +
			PERSONS_LNAME + " text, " +
			PERSONS_DESCR + " text, " + 
			PERSONS_HEIGHT + " text, " +
			PERSONS_WEIGHT + " text, " +
			PERSONS_ADDRESS + " text, " +
			PERSONS_PHONE + " text, " +
			PERSONS_TYPE + " text, " +
			PERSONS_STATEMENT + " text); ";
	
	private static final String CREATE_PERSONS_CASES = "create table " +
			TABLE_PERSONS_CASES + "(" + 
			PERSONS_CASES_PERSONID + " integer not null, " +
			PERSONS_CASES_CASEID + " integer not null, "+ 
			"foreign key(" + PERSONS_CASES_PERSONID + ") references " + TABLE_PERSONS + "(" + PERSONS_ID + "), " +
			"foreign key(" + PERSONS_CASES_CASEID + ") references " + TABLE_CASES + "(" + CASE_ID + ")" +
			"primary key(" + PERSONS_CASES_PERSONID + ", " + PERSONS_CASES_CASEID + ")); ";
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = CREATE_CASES + CREATE_FORMS + CREATE_FORMS_CASES + CREATE_INCIDENTS + 
			CREATE_PERSONS + CREATE_PERSONS_CASES;
	
	public MySQLiteDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_CASES);
		database.execSQL(CREATE_FORMS);
		database.execSQL(CREATE_FORMS_CASES);
		database.execSQL(CREATE_INCIDENTS);
		database.execSQL(CREATE_PERSONS);
		database.execSQL(CREATE_PERSONS_CASES);
		ContentValues forms = new ContentValues();
		forms.put(FORMS_ID, 1);
		forms.put(FORMS_NAME, "Incident Report");
		database.insert(MySQLiteDbHelper.TABLE_FORMS, null, forms);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASES);
		onCreate(db);
	}

} 
