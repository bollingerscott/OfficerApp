package com.pss.wsu.officersupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Map.Entry;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import cases.Case;
import cases.CaseDataSource;
import cases.Form;
import cases.Person;
import forms.*;

public class EditCase extends ExpandableListActivity {

	private Case myCase;
	private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
	private static CaseDataSource datasource;
	private boolean update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datasource = CaseDataSource.getDataSource(this);
		datasource.open();
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
			update = true;
		}
		if (myCase.getName().equals(""))
		{
			update = false;
		}
		// Listview on child click listener
        listView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				String item = groupData.get(groupPosition).get("ROOT_NAME");
				Intent intent = null;
				if (item.equalsIgnoreCase("Suspects") || item.equalsIgnoreCase("Witnesses"))
				{
					String name = childData.get(groupPosition).get(childPosition).get("CHILD_NAME");
					String[] names = name.split(" ");
					Person p = datasource.findPerson(names[0], names[1]);
					intent = new Intent(EditCase.this, EditPerson.class);
					intent.putExtra("myPerson", p);
					intent.putExtra("myCase", myCase);
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
			String title = ((EditText)findViewById(R.id.editTitle)).getText().toString();
			String descr = ((EditText)findViewById(R.id.editDescr)).getText().toString();
			Integer id1;
			try
			{
				id1 = Integer.parseInt(((EditText)findViewById(R.id.editid)).getText().toString());
			}
			catch (NumberFormatException e)
			{
				id1 = 0;
			}
			myCase.setCaseNum(id1);
			myCase.setDescription(descr);
			myCase.setName(title);
			intent.putExtra("myCase", myCase);
			intent.putExtra("type", "witness");
			startActivity(intent);
			return true;
		}
		else if (id == R.id.newSuspect) {
			Intent intent = new Intent(this, EditPerson.class);
			String title = ((EditText)findViewById(R.id.editTitle)).getText().toString();
			String descr = ((EditText)findViewById(R.id.editDescr)).getText().toString();
			Integer id1;
			try
			{
				id1 = Integer.parseInt(((EditText)findViewById(R.id.editid)).getText().toString());
			}
			catch (NumberFormatException e)
			{
				id1 = 0;
			}
			myCase.setCaseNum(id1);
			myCase.setDescription(descr);
			myCase.setName(title);
			intent.putExtra("myCase", myCase);
			intent.putExtra("type", "suspect");
			startActivity(intent);
			return true;
		}
		else if (id == R.id.newForm) {
			Intent intent = new Intent(this, ChooseForm.class);
			String title = ((EditText)findViewById(R.id.editTitle)).getText().toString();
			String descr = ((EditText)findViewById(R.id.editDescr)).getText().toString();
			Integer id1;//i really should put this into a method
			try
			{
				id1 = Integer.parseInt(((EditText)findViewById(R.id.editid)).getText().toString());
			}
			catch (NumberFormatException e)
			{
				id1 = 0;
			}
			myCase.setCaseNum(id1);
			myCase.setDescription(descr);
			myCase.setName(title);
			intent.putExtra("myCase", myCase);
			startActivity(intent);
			return true;
		}
		else if (id == R.id.save) {
			save(update);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void save(boolean update)
	{
		String title = ((EditText)findViewById(R.id.editTitle)).getText().toString();
		String descr = ((EditText)findViewById(R.id.editDescr)).getText().toString();
		Integer id;
		try
		{
			id = Integer.parseInt(((EditText)findViewById(R.id.editid)).getText().toString());
		}
		catch (NumberFormatException e)
		{
			id = 0;
		}
		//ExpandableListView just displays info, editPerson and form activities save those to the DB
		//and they will already be in myCase when this activity recreates....I hope
		if (!update)
		{
			datasource.createOrUpdateCase(id, title, descr, myCase.getWitnessMap(), myCase.getSuspectMap(), myCase.getFormMap());
		}
		else 
		{
			datasource.createOrUpdateCase(id, title, descr, myCase.getWitnessMap(), myCase.getSuspectMap(), myCase.getFormMap());
		}
		Intent intent = new Intent(this, CaseList.class);
		startActivity(intent);
	}

	public void getCaseData()
	{
		
		groupData.add(new HashMap<String, String>() {{put("ROOT_NAME", "Witnesses");}});
		groupData.add(new HashMap<String, String>() {{put("ROOT_NAME", "Suspects");}});
		groupData.add(new HashMap<String, String>() {{put("ROOT_NAME", "Forms");}});
		groupData.add(new HashMap<String, String>() {{put("ROOT_NAME", "Audio Recordings");}});
		if (myCase != null)
		{
			List<Map<String, String>> childGroup1 = new ArrayList<Map<String, String>>();
			Set<String> witnesses = myCase.getWitnessMap().keySet();
			Iterator<String> it = witnesses.iterator();
			while (it.hasNext())
			{
				String witness = it.next();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("CHILD_NAME", witness);
				childGroup1.add(map);
			}
			childData.add(childGroup1);
			List<Map<String, String>> childGroup2 = new ArrayList<Map<String, String>>();
			Set<Entry<Integer, Person>> suspects = myCase.getSuspectMap().entrySet();
			Iterator<Entry<Integer, Person>> it1 = suspects.iterator();
			while (it1.hasNext())
			{
				String suspect = it1.next().getValue().toString();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("CHILD_NAME", suspect);
				childGroup2.add(map);
			}
			childData.add(childGroup2);
			List<Map<String, String>> childGroup3 = new ArrayList<Map<String, String>>();
			Set<String> forms = myCase.getFormMap().keySet();
			Iterator<String> it2 = forms.iterator();
			while (it2.hasNext())
			{
				String form = it2.next();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("CHILD_NAME", form);
				childGroup3.add(map);
			}
			childData.add(childGroup3);
			List<Map<String, String>> childGroup4 = new ArrayList<Map<String, String>>();
			HashMap<String, String> thing = new HashMap<String, String>();
			thing.put("CHILD_NAME", "audio");
			childGroup4.add(thing);
			childData.add(childGroup4);
		}
	}
}
