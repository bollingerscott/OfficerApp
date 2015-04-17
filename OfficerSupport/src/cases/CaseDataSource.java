package cases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.pss.wsu.officersupport.MySQLiteDbHelper;

import forms.Incident_Report;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CaseDataSource {

	// Database fields
	private static SQLiteDatabase database;
	private static MySQLiteDbHelper dbHelper;
	private static CaseDataSource myData = null;
	
	public static CaseDataSource getDataSource(Context context)
	{
		if (myData == null)
		{
			myData = new CaseDataSource(context);
		}
		return myData;
	}
	
	public CaseDataSource(Context context) {
		dbHelper = new MySQLiteDbHelper (context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	
	public Case createCase(int num, String title, String descr, HashMap<String, Person> witnesses, HashMap<Integer, Person> suspects, HashMap<String, Form> forms){
		Case newCase = new Case(title, descr, num, witnesses, suspects, forms);
		//Insert into cases
		ContentValues values = new ContentValues();
		values.put(MySQLiteDbHelper.CASE_ID, num);
		values.put(MySQLiteDbHelper.CASE_TITLE, title);
		values.put(MySQLiteDbHelper.CASE_DESCR, descr);
		database.insert(MySQLiteDbHelper.TABLE_CASES, null, values);
		//Insert into FormsCases
		ContentValues fc = new ContentValues();
		Set<String> types = forms.keySet();
		Iterator<String> it = types.iterator();
		while (it.hasNext())
		{
			ArrayList<String> args = new ArrayList<String>();
			String arg = it.next();
			args.add(arg);
			String[] whereArgs = (String[]) args.toArray();
			Cursor c = database.query(true, MySQLiteDbHelper.TABLE_FORMS, null, MySQLiteDbHelper.FORMS_NAME+"=?", whereArgs, null, null, null, null, null);
			int fid = c.getInt(c.getColumnIndexOrThrow(MySQLiteDbHelper.FORMS_ID));
			fc.put(MySQLiteDbHelper.FORMS_CASES_CASEID, num);
			fc.put(MySQLiteDbHelper.FORMS_CASES_FORMID, fid);
			database.insert(MySQLiteDbHelper.TABLE_FORMS_CASES, null, fc);
		}
		//Insert into table for form
		Set<Entry<String, Form>> fs = forms.entrySet();
		Iterator<Entry<String, Form>> form = fs.iterator();
		while (form.hasNext())
		{
			Entry<String, Form> e = form.next();
			Form f = e.getValue();
			f.save(database);
		}
		//Insert witnesses into persons
		Set<Entry<String, Person>> w = witnesses.entrySet();
		Iterator<Entry<String, Person>> wit = w.iterator();
		while (wit.hasNext())
		{
			Entry<String, Person> p = wit.next();
			Person p1 = p.getValue();
			long id = p1.save(database);
			ContentValues v = new ContentValues();
			v.put(MySQLiteDbHelper.PERSONS_CASES_PERSONID, id);
			v.put(MySQLiteDbHelper.PERSONS_CASES_CASEID, num);
			database.insert(MySQLiteDbHelper.TABLE_PERSONS_CASES, null, v);
		}
		//Insert suspects into persons
		Set<Entry<Integer, Person>> s = suspects.entrySet();
		Iterator<Entry<Integer, Person>> sit = s.iterator();
		while (form.hasNext())
		{
			Entry<Integer, Person> p = sit.next();
			Person p1 = p.getValue();
			long id = p1.save(database);
			ContentValues v = new ContentValues();
			v.put(MySQLiteDbHelper.PERSONS_CASES_PERSONID, id);
			v.put(MySQLiteDbHelper.PERSONS_CASES_CASEID, num);
			database.insert(MySQLiteDbHelper.TABLE_PERSONS_CASES, null, v);
		}
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
		String title = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteDbHelper.CASE_TITLE));
		String description = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteDbHelper.CASE_DESCR));
		int id = cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteDbHelper.CASE_ID));
		HashMap<String, Person> witnesses = new HashMap<String, Person>();
		HashMap<Integer, Person> suspects = new HashMap<Integer, Person>();
		HashMap<String, Form> forms = new HashMap<String, Form>();
		//Get the person ids of people linked to this case
		Cursor c = database.query(true, MySQLiteDbHelper.TABLE_PERSONS_CASES, null, MySQLiteDbHelper.CASE_ID+"="+id, null, null, null, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast())
		{
			//Get the person linked to this case
			Cursor c1 = database.query(true, MySQLiteDbHelper.TABLE_PERSONS, null, MySQLiteDbHelper.PERSONS_ID+"="+c.getInt(c.getColumnIndex(MySQLiteDbHelper.PERSONS_CASES_PERSONID)), null, null, null, null, null, null);
			c1.moveToFirst();
			Person p = cursorToPerson(c1);
			String type = p.getType();
			if (type.equalsIgnoreCase("W"))
			{
				witnesses.put(p.getFirstName() + " " + p.getLastName(), p);
			}
			else if (type.equalsIgnoreCase("S"))
			{
				suspects.put(p.getNum(), p);
			}
			c.moveToNext();
		}
		//Get the forms
		Cursor f = database.query(true, MySQLiteDbHelper.TABLE_FORMS_CASES, null, MySQLiteDbHelper.CASE_ID+"="+id, null, null, null, null, null, null);
		f.moveToFirst();
		while (!f.isAfterLast())
		{
			//Get the type of form
			int fid = f.getInt(f.getColumnIndexOrThrow(MySQLiteDbHelper.FORMS_CASES_FORMID));
			Cursor c1 = database.query(true, MySQLiteDbHelper.TABLE_FORMS, null, MySQLiteDbHelper.FORMS_ID+"="+fid, null, null, null, null, null, null);
			c1.moveToFirst();
			while (!c1.isAfterLast())
			{
				String name = c1.getString(c1.getColumnIndexOrThrow(MySQLiteDbHelper.FORMS_NAME));
				if (name.equalsIgnoreCase("Incident Report"))
				{
					Cursor c2 = database.query(true, MySQLiteDbHelper.TABLE_INCIDENTS, null, null, null, null, null, null, null, null);
					c2.moveToFirst();
					Form form = Incident_Report.cursorToForm(c2);
				}
				c1.moveToNext();
			}
			f.moveToNext();
		}
		Case newCase = new Case(title, description, id, witnesses, suspects, forms);
		return newCase;
	}
	
	private Person cursorToPerson(Cursor c)
	{
		String firstname = c.getString(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_FNAME));
		String lastname = c.getString(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_LNAME));
		String description = c.getString(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_DESCR));
		double height = c.getDouble(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_HEIGHT));
		int weight = c.getInt(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_WEIGHT));
		String address = c.getString(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_ADDRESS));
		String phone = c.getString(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_PHONE));
		String statement = c.getString(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_STATEMENT));
		String type = c.getString(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_TYPE));
		int num = c.getInt(c.getColumnIndexOrThrow(MySQLiteDbHelper.PERSONS_ID));
		return new Person(firstname, lastname, description, height, weight, address, phone, statement, type, num);
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
