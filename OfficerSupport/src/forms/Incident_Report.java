package forms;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import cases.Case;
import cases.Form;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import com.pss.wsu.officersupport.EditCase;
import com.pss.wsu.officersupport.MySQLiteDbHelper;
import com.pss.wsu.officersupport.R;

public class Incident_Report extends Form {

	private String district;
	private String number;
	private String type;
	private String tod;
	private String toa;
	private String toc;
	private String reportDate;
	private String reportTime;
	private Case myCase;
	
	public Incident_Report(String district, String number, String type, String tod, String toa, String toc, String reportDate, String reportTime)
	{
		this.district = district;
		this.number = number;
		this.type = type;
		this.tod = tod;
		this.toa = tod;
		this.toc = toc;
		this.reportDate = reportDate;
		this.reportTime = reportTime;
	}
	
	public Incident_Report()
	{
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incident_report);
		setType("Incident Report");
		Intent intent = getIntent();
		myCase = (Case)intent.getSerializableExtra("myCase");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_editperson, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save) {
			save();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void save()
	{
		this.district = ((EditText)findViewById(R.id.editDistrict)).getText().toString();
		this.number = ((EditText)findViewById(R.id.editIncidentNum)).getText().toString();
		RadioGroup typeGroup = ((RadioGroup)findViewById(R.id.radioGroupoffORsupp));
		this.type = ((RadioButton)findViewById(typeGroup.getCheckedRadioButtonId())).getText().toString();
		this.tod = ((EditText)findViewById(R.id.editTOD)).getText().toString();
		this.toa = ((EditText)findViewById(R.id.editTOA)).getText().toString();
		this.toc = ((EditText)findViewById(R.id.editTOC)).getText().toString();
		this.reportDate = ((EditText)findViewById(R.id.editReportDate)).getText().toString();
		this.reportTime = ((EditText)findViewById(R.id.editReportTime)).getText().toString();
		myCase.getFormMap().put("Incident report", this);
		Intent intent = new Intent(this, EditCase.class);
		intent.putExtra("case", myCase);
		startActivity(intent);
	}
	
	@Override
	public void save(SQLiteDatabase database)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteDbHelper.INCIDENTS_DISTRICT, this.district);
		values.put(MySQLiteDbHelper.INCIDENTS_NUMBER, this.number);
		values.put(MySQLiteDbHelper.INCIDENTS_REPORT_DATE, this.reportDate);
		values.put(MySQLiteDbHelper.INCIDENTS_REPORT_TIME, this.reportTime);
		values.put(MySQLiteDbHelper.INCIDENTS_TOA, this.toa);
		values.put(MySQLiteDbHelper.INCIDENTS_TOC, this.toc);
		values.put(MySQLiteDbHelper.INCIDENTS_TOD, this.tod);
		values.put(MySQLiteDbHelper.INCIDENTS_TYPE, this.type);
		database.insert(MySQLiteDbHelper.TABLE_INCIDENTS, null, values);
	}

	public static Form cursorToForm(Cursor c2) {
		String district = c2.getString(c2.getColumnIndexOrThrow(MySQLiteDbHelper.INCIDENTS_DISTRICT));
		String number = c2.getString(c2.getColumnIndexOrThrow(MySQLiteDbHelper.INCIDENTS_NUMBER));
		String type = c2.getString(c2.getColumnIndexOrThrow(MySQLiteDbHelper.INCIDENTS_TYPE));
		String tod = c2.getString(c2.getColumnIndexOrThrow(MySQLiteDbHelper.INCIDENTS_TOD));
		String toa = c2.getString(c2.getColumnIndexOrThrow(MySQLiteDbHelper.INCIDENTS_TOA));
		String toc = c2.getString(c2.getColumnIndexOrThrow(MySQLiteDbHelper.INCIDENTS_TOC));
		String reportDate = c2.getString(c2.getColumnIndexOrThrow(MySQLiteDbHelper.INCIDENTS_REPORT_DATE));
		String reportTime = c2.getString(c2.getColumnIndexOrThrow(MySQLiteDbHelper.INCIDENTS_REPORT_TIME));
		return new Incident_Report(district, number, type, tod, toa, toc, reportDate, reportTime);
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTod() {
		return tod;
	}

	public void setTod(String tod) {
		this.tod = tod;
	}

	public String getToa() {
		return toa;
	}

	public void setToa(String toa) {
		this.toa = toa;
	}

	public String getToc() {
		return toc;
	}

	public void setToc(String toc) {
		this.toc = toc;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
}
