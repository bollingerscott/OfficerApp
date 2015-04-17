package com.pss.wsu.officersupport;

import cases.Case;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_person);
		Intent intent = getIntent();
		myCase = (Case)intent.getSerializableExtra("myCase");
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
			//Save to DB
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void save()
	{
		String firstName = ((EditText)findViewById(R.id.editFname)).getText().toString();
		String lastName = ((EditText)findViewById(R.id.editLname)).getText().toString();
		String description = ((EditText)findViewById(R.id.editDescr)).getText().toString();
		Double height;
		try
		{
			height = Double.parseDouble(((EditText)findViewById(R.id.editid)).getText().toString());
		}
		catch (NumberFormatException e)
		{
			height = 0.0;
		}
		Integer weight;
		try
		{
			weight = Integer.parseInt(((EditText)findViewById(R.id.editid)).getText().toString());
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
		Integer num = 0;
		try
		{
			num = Integer.parseInt(((EditText)findViewById(R.id.editid)).getText().toString());
		}
		catch (NumberFormatException e)
		{
			num = 0;
		}
		Person p = new Person(firstName, lastName, description, height, weight, address, phone, statement, type, num);
		if (type.equalsIgnoreCase("Suspect"))
		{
			myCase.getSuspectMap().put(num, p);
		}
		else if (type.equalsIgnoreCase("witness"))
		{
			myCase.getWitnessMap().put(firstName + " " + lastName, p);
		}
		Intent intent = new Intent(this, EditCase.class);
		intent.putExtra("case", myCase);
		startActivity(intent);
	}
}
