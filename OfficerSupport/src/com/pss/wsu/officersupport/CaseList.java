package com.pss.wsu.officersupport;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cases.Case;
import cases.CaseDataSource;

public class CaseList extends ListActivity {

	private CaseDataSource datasource;
	private List<Case> values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datasource = new CaseDataSource(this);
		datasource.open();
		values = new ArrayList<Case>();
		datasource.deleteAll();
		//datasource.deleteCase(1);datasource.deleteCase(2);datasource.deleteCase(3);
		values = datasource.getAllCases();
		ArrayAdapter<Case> adapter = new ArrayAdapter<Case>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		createDemoCases();
	}

	private void createDemoCases(){
		@SuppressWarnings("unchecked")
		ArrayAdapter<Case> adapter = (ArrayAdapter<Case>) getListAdapter();
		String[] witnesses = {"Bob Smith"};
		String[] suspects = {"Bob Smith"};
		String[] forms = {"Incident Report"};
		Case case1 = datasource.createCase(1, "Serial Killer #1", "5 people have been murdered", witnesses, suspects, forms);
		Case case2 = datasource.createCase(2, "Traffic Accident #1", "Dummy didn't have lights on and  got hit by a car", witnesses, suspects, forms);
		Case case3 = datasource.createCase(3, "Dumb Robber #1", "Guy robbed store with gun which turned out to be a banana", witnesses, suspects, forms);
		adapter.add(case1);
		adapter.add(case2);
		adapter.add(case3);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Case item = (Case) getListAdapter().getItem(position);
		//TODO Go to activity for editing the case
		Intent intent = new Intent(this, EditCase.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_caselist, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.case_search:
			search("Dumb Robber #1");
			return true;
		case R.id.newCase:
			Intent intent = new Intent(this, EditCase.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

	private void search(String search)
	{
		Case myCase = datasource.findCase(search);
		@SuppressWarnings("unchecked")
		ArrayAdapter<Case> adapter = (ArrayAdapter<Case>) getListAdapter();
		adapter.clear();
		if (myCase != null)
		{
			adapter.add(myCase);
		} 
		else
		{
			adapter.add(new Case(null, null, null, "Not Found", "", 0));
		}
		adapter.notifyDataSetChanged();
	}
}
