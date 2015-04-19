package com.pss.wsu.officersupport;

import java.util.ArrayList;

import cases.Case;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import forms.Incident_Report;

public class ChooseForm extends ListActivity {

	private ArrayList<String> forms;
	private Case myCase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_form);
		forms = new ArrayList<String>();
		initForms();
		Intent intent = getIntent();
		myCase = (Case)intent.getSerializableExtra("myCase");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, forms);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Intent intent = getFormActivity(item);
		intent.putExtra("myCase", myCase);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_form, menu);
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
	
	private Intent getFormActivity(String item)
	{
		if (item.equalsIgnoreCase("Incident Report"))
		{
			return new Intent(this, Incident_Report.class);
		}
		else
		{
			return null;
		}
	}
	
	private void initForms()
	{
		forms.add("Incident Report");
	}
}
