package cases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pss.wsu.officersupport.MySQLiteDbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CaseDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteDbHelper dbHelper;
	private String[] allColumns = { MySQLiteDbHelper.CASE_ID, MySQLiteDbHelper.CASE_TITLE,  MySQLiteDbHelper.CASE_DESCR, 
			MySQLiteDbHelper.CASE_WITNESSES, MySQLiteDbHelper.CASE_SUSPECTS, MySQLiteDbHelper.CASE_FORMS};

	public CaseDataSource(Context context) {
		dbHelper = new MySQLiteDbHelper (context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Case createCase(int num, String title, String descr, String[] witnesses, String[] suspects, String[] forms){
		Case newCase = new Case(witnesses, suspects, forms, title, descr, num);
		ContentValues values = new ContentValues();
		values.put(MySQLiteDbHelper.CASE_ID, num);
		values.put(MySQLiteDbHelper.CASE_TITLE, title);
		values.put(MySQLiteDbHelper.CASE_DESCR, descr);
		values.put(MySQLiteDbHelper.CASE_WITNESSES, newCase.getString(witnesses));
		values.put(MySQLiteDbHelper.CASE_SUSPECTS, newCase.getString(suspects));
		values.put(MySQLiteDbHelper.CASE_FORMS, newCase.getString(forms));
		//database.execSQL("INSERT INTO " + MySQLiteDbHelper.TABLE_CASES + "(" + newCase.getString(allColumns) + ")" + "VALUES(" + num + ", '" + title + "', '" + descr + "', '" + newCase.getString(witnesses) + "', '" + newCase.getString(suspects) + "', '" + newCase.getString(forms) + "');");
		long insertId = database.insert(MySQLiteDbHelper.TABLE_CASES, null, values);
/*		Cursor cursor = database.query(MySQLiteDbHelper.TABLE_CASES,
				allColumns, MySQLiteDbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Case newCase = cursorToCase(cursor);
		cursor.close();*/
		return newCase;
	}

	public void deleteCase(long caseNum) {
		System.out.println("Case deleted with id: " + caseNum);
		database.delete(MySQLiteDbHelper.TABLE_CASES, MySQLiteDbHelper.CASE_ID
				+ " = " + Long.toString(caseNum), null);
	}
	
	public void deleteAll()
	{
		database.delete(MySQLiteDbHelper.TABLE_CASES, null, null);
	}

	public List<Case> getAllCases() {
		List<Case> cases = new ArrayList<Case>();

		Cursor cursor = database.query(MySQLiteDbHelper.TABLE_CASES,
				null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Case aCase = cursorToCase(cursor);
			cases.add(aCase);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return cases;
	}

	private Case cursorToCase(Cursor cursor) {
		int witnessIndex = cursor.getColumnIndexOrThrow("witnesses");
		int suspectIndex = cursor.getColumnIndexOrThrow("suspects");
		int formsIndex = cursor.getColumnIndexOrThrow("forms");
		String[] witnesses = null;
		String[] suspects = null;
		String[] forms = null;
		if (witnessIndex != -1)
		{
			witnesses = cursor.getString(witnessIndex).split(", ");
		}
		if (suspectIndex != -1)
		{
			suspects = cursor.getString(cursor.getColumnIndexOrThrow("suspects")).split(", ");
		}
		if (formsIndex != -1)
		{
			forms = cursor.getString(cursor.getColumnIndexOrThrow("forms")).split(", ");
		}
		Case newCase = new Case(witnesses, suspects, forms, cursor.getString(cursor.getColumnIndexOrThrow("title")), cursor.getString(cursor.getColumnIndexOrThrow("description")), cursor.getInt(cursor.getColumnIndexOrThrow("caseid")));
		return newCase;
	}
	
	public Case findCase(String search)
	{
		Cursor cursor = database.query(MySQLiteDbHelper.TABLE_CASES,
				null, MySQLiteDbHelper.CASE_TITLE+ " = '" + search + "'", null, null, null, null);
		Case myCase = null;
		if (cursor.getCount() != 0)
		{
			myCase = cursorToCase(cursor);
		}
		return myCase;
	}
}
