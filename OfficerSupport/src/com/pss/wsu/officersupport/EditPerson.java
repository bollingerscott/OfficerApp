package com.pss.wsu.officersupport;

import cases.Case;
import cases.CaseDataSource;
import cases.Person;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;

public class EditPerson extends Activity {

	private Case myCase;
	private Person myPerson;
	private CaseDataSource datasource;
	private boolean update;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_person);
		Intent intent = getIntent();
		myCase = (Case)intent.getSerializableExtra("myCase");
		myPerson = (Person)intent.getSerializableExtra("myPerson");
		if (myPerson != null)
		{
			EditText fname = (EditText) findViewById(R.id.editFname);
			EditText lname = (EditText) findViewById(R.id.editLname);
			EditText descr = (EditText) findViewById(R.id.editDescr);
			EditText height = (EditText) findViewById(R.id.editHeight);
			EditText weight = (EditText) findViewById(R.id.editWeight);
			EditText address = (EditText) findViewById(R.id.editAddr);
			EditText phone = (EditText) findViewById(R.id.editPhone);
			EditText statement = (EditText) findViewById(R.id.editStatement);
			RadioGroup typeGroup = (RadioGroup) findViewById(R.id.radioGroupWitnessSuspect);
			RadioButton witness = (RadioButton)typeGroup.getChildAt(1);
			RadioButton suspect = (RadioButton)typeGroup.getChildAt(0);
			if (myPerson.getType().equalsIgnoreCase("witness"))
			{
				witness.setChecked(true);
				suspect.setChecked(false);
				myCase.getWitnessMap().remove(myPerson.getFirstName() + " " + myPerson.getLastName());
			}
			else
			{
				witness.setChecked(false);
				suspect.setChecked(true);
				myCase.getSuspectMap().remove((int)myPerson.getNum());
			}
			fname.setText(myPerson.getFirstName());
			lname.setText(myPerson.getLastName());
			descr.setText(myPerson.getDescription());
			height.setText(((Double)myPerson.getHeight()).toString());
			weight.setText(((Integer)myPerson.getWeight()).toString());
			address.setText(myPerson.getAddress());
			phone.setText(myPerson.getPhone());
			statement.setText(myPerson.getStatement());
			update = true;
		}
		else
		{
			update = false;
			RadioGroup typeGroup = (RadioGroup) findViewById(R.id.radioGroupWitnessSuspect);
			RadioButton witness = (RadioButton)typeGroup.getChildAt(1);
			RadioButton suspect = (RadioButton)typeGroup.getChildAt(0);
			if (intent.getStringExtra("type").equalsIgnoreCase("witness"))
			{
				witness.setChecked(true);
				suspect.setChecked(false);
			}
			else
			{
				witness.setChecked(false);
				suspect.setChecked(true);
			}
		}
		datasource = CaseDataSource.getDataSource(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_editperson, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save) {
			save(update);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void save(boolean update)
	{
		String firstName = ((EditText)findViewById(R.id.editFname)).getText().toString();
		String lastName = ((EditText)findViewById(R.id.editLname)).getText().toString();
		String description = ((EditText)findViewById(R.id.editDescr)).getText().toString();
		Double height;
		try
		{
			height = Double.parseDouble(((EditText)findViewById(R.id.editHeight)).getText().toString());
		}
		catch (NumberFormatException e)
		{
			height = 0.0;
		}
		Integer weight;
		try
		{
			weight = Integer.parseInt(((EditText)findViewById(R.id.editWeight)).getText().toString());
		}
		catch (NumberFormatException e)
		{
			weight = 0;
		}
		String address = ((EditText)findViewById(R.id.editAddr)).getText().toString();
		String phone = ((EditText)findViewById(R.id.editPhone)).getText().toString();
		String statement = ((EditText)findViewById(R.id.editStatement)).getText().toString();
		RadioGroup typeGroup = ((RadioGroup)findViewById(R.id.radioGroupWitnessSuspect));
		int selectedID = typeGroup.getCheckedRadioButtonId();
		String type = ((RadioButton)findViewById(selectedID)).getText().toString();
		System.out.println(type);
		Integer num = 0;
		try
		{
			//num = Integer.parseInt(((EditText)findViewById(R.id.editid)).getText().toString());
		}
		catch (NumberFormatException e)
		{
			num = 0;
		}
		Person p = new Person(firstName, lastName, description, height, weight, address, phone, statement, type, num);
		if (type.equalsIgnoreCase("Suspect"))
		{
			myCase.getSuspectMap().put((int)p.getNum(), p);
		}
		else if (type.equalsIgnoreCase("witness"))
		{
			myCase.getWitnessMap().put(p.getFirstName() + " " + p.getLastName(), p);
		}
		if (!update)
		{
			p.setNum(datasource.insertPerson(p));
		}
		else 
		{
			datasource.updatePerson(p);
		}
		Intent intent = new Intent(this, EditCase.class);
		intent.putExtra("case", myCase);
		startActivity(intent);
	}
}
