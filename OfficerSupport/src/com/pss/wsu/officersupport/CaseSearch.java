package com.pss.wsu.officersupport;

import java.util.ArrayList;
import java.util.List;

import cases.Case;
import cases.CaseDataSource;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CaseSearch extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case_search);
		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			search(query);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.case_search, menu);
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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Case item = (Case) getListAdapter().getItem(position);
		//TODO Go to activity for editing the case
		Intent intent = new Intent(this, EditCase.class);
		startActivity(intent);
	}
	
	private void search(String query)
	{
		CaseDataSource data = CaseDataSource.getDataSource(this);
		data.open();
		List<Case> cases = new ArrayList<Case>();
		ArrayList<Case> found = new ArrayList<Case>();
		cases  = data.getAllCases();
		for (int i = 0; i < cases.size(); i++)
		{
			if (cases.get(i).getName().equalsIgnoreCase(query))
			{
				found.add(cases.get(i));
			}
		}
		ArrayAdapter<Case> adapter = new ArrayAdapter<Case>(this, android.R.layout.simple_list_item_1, found);
		setListAdapter(adapter);
	}
}
