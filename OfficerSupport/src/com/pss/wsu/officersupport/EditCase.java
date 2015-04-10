package com.pss.wsu.officersupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import cases.Case;
import forms.*;

public class EditCase extends ExpandableListActivity {

	private Case myCase;
	private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		myCase = (Case) intent.getSerializableExtra("case");
		setContentView(R.layout.activity_edit_case);
		getCaseData();
		ExpandableListView listView = getExpandableListView();
		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				this,

				groupData,
				android.R.layout.simple_expandable_list_item_1,
				new String[] { "ROOT_NAME" },
				new int[] { android.R.id.text1 },

				childData,
				android.R.layout.simple_expandable_list_item_2,
				new String[] { "CHILD_NAME", "CHILD_NAME" },
				new int[] { android.R.id.text1, android.R.id.text2 }
				);
		listView.setAdapter(adapter);
		if (myCase != null)
		{
			EditText id = (EditText) findViewById(R.id.editid);
			EditText title = (EditText) findViewById(R.id.editTitle);
			EditText descr = (EditText) findViewById(R.id.editDescr);
			id.setText(myCase.getCaseNum().toString());
			title.setText(myCase.getName());
			descr.setText(myCase.getDescription());
		}
		// Listview on child click listener
        listView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				String item = groupData.get(groupPosition).get("ROOT_NAME");
				Intent intent = null;
				if (item.equalsIgnoreCase("Suspects") || item.equalsIgnoreCase("Witnesses"))
				{
					intent = new Intent(EditCase.this, EditPerson.class);
				}
				else if (item.equalsIgnoreCase("Forms"))
				{
					String formName = childData.get(groupPosition).get(childPosition).get("CHILD_NAME");
					if (formName.equals("Incident Report"))
					{
						intent = new Intent(EditCase.this, Incident_Report.class);
					}
					else
					{
						intent = new Intent(EditCase.this, ChooseForm.class);
					}
				}
				startActivity(intent);
				return false;
			}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_editcase, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.newWitness) {
			Intent intent = new Intent(this, EditPerson.class);
			startActivity(intent);
			return true;
		}
		else if (id == R.id.newSuspect) {
			Intent intent = new Intent(this, EditPerson.class);
			startActivity(intent);
			return true;
		}
		else if (id == R.id.newForm) {
			Intent intent = new Intent(this, ChooseForm.class);
			startActivity(intent);
			return true;
		}
		else if (id == R.id.save) {
			//Save to DB
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getCaseData()
	{
		
		groupData.add(new HashMap<String, String>() {{put("ROOT_NAME", "Witnesses");}});
		groupData.add(new HashMap<String, String>() {{put("ROOT_NAME", "Suspects");}});
		groupData.add(new HashMap<String, String>() {{put("ROOT_NAME", "Forms");}});
		if (myCase != null)
		{
			List<Map<String, String>> childGroup1 = new ArrayList<Map<String, String>>();
			for (int i = 0; i < myCase.getWitnesses().length; i++)
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("CHILD_NAME", myCase.getWitnesses()[i]);
				childGroup1.add(map);
			}
			childData.add(childGroup1);
			List<Map<String, String>> childGroup2 = new ArrayList<Map<String, String>>();
			for (int i = 0; i < myCase.getSuspects().length; i++)
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("CHILD_NAME", myCase.getSuspects()[i]);
				childGroup2.add(map);
			}
			childData.add(childGroup2);
			List<Map<String, String>> childGroup3 = new ArrayList<Map<String, String>>();
			for (int i = 0; i < myCase.getForms().length; i++)
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("CHILD_NAME", myCase.getForms()[i]);
				childGroup3.add(map);
			}
			childData.add(childGroup3);
		}
	}
}
