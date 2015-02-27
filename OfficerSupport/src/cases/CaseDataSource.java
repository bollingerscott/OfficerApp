package cases;

import java.util.ArrayList;
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
	private String[] allColumns = { MySQLiteDbHelper.COLUMN_ID,
			MySQLiteDbHelper.COLUMN_TITLE,  MySQLiteDbHelper.COLUMN_DESCR};

	public CaseDataSource(Context context) {
		dbHelper = new MySQLiteDbHelper (context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Case createCase(int num, String title, String descr){
		ContentValues values = new ContentValues();
		values.put(MySQLiteDbHelper.COLUMN_ID, num);
		values.put(MySQLiteDbHelper.COLUMN_TITLE, title);
		values.put(MySQLiteDbHelper.COLUMN_DESCR, descr);
		long insertId = database.insert(MySQLiteDbHelper.TABLE_CASES, null, values);
		Cursor cursor = database.query(MySQLiteDbHelper.TABLE_CASES,
				allColumns, MySQLiteDbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Case newCase = cursorToCase(cursor);
		cursor.close();
		return newCase;
	}

	public void deleteCase(Case deleteCase) {
		long id = deleteCase.getCaseNum();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteDbHelper.TABLE_CASES, MySQLiteDbHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Case> getAllCases() {
		List<Case> cases = new ArrayList<Case>();

		Cursor cursor = database.query(MySQLiteDbHelper.TABLE_CASES,
				allColumns, null, null, null, null, null);

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
		Case newCase = new Case(null, null, null, null, null, (Integer) null);
		newCase.setCaseNum(cursor.getInt(0));
		newCase.setName(cursor.getString(1));
		newCase.setDescription(cursor.getString(2));
		return newCase;
	}
}
