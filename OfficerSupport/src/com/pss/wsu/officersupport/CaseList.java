package com.pss.wsu.officersupport;

import java.util.List;

import cases.Case;
import cases.CaseDataSource;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CaseList extends ListActivity {

	private CaseDataSource datasource;


	// This is the Adapter being used to display the list's data
	//SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		datasource = new CaseDataSource(this);
		datasource.open();

		List<Case> values = datasource.getAllCases();
		ArrayAdapter<Case> adapter = new ArrayAdapter<Case>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		createDemoCases();
		//Cursor cursor = getContentResolver().query();

		// the desired columns to be bound
		//TODO String[] columns = new String[] { caseid, title, subtitle };
		// the XML defined views which the data will be bound to
		//int[] to = new int[] { R.id.case_number, R.id.case_description };

		// Create an empty adapter we will use to display the loaded data.
		// We pass null for the cursor, then update it in onLoadFinished()
		//TODO mAdapter = new SimpleCursorAdapter(this, R.layout.caselist_entry_layout, cursor, columns, to, 0);
		//this.setListAdapter(mAdapter);
	}

	private void createDemoCases(){
		@SuppressWarnings("unchecked")
		ArrayAdapter<Case> adapter = (ArrayAdapter<Case>) getListAdapter();
		Case case1 = datasource.createCase(1, "Serial Killer #1", "5 people have been murdered");
		Case case2 = datasource.createCase(2, "Traffic Accident #1", "Dummy didn't have lights on and  got hit by a car");
		Case case3 = datasource.createCase(3, "Dumb Robber #1", "Guy robbed store with gun which turned out to be a banana");
		adapter.add(case1);
		adapter.add(case2);
		adapter.add(case3);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		//TODO Go to activity for editing the case
		Intent intent = new Intent(this, EditCase.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.case_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}
