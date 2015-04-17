package cases;

import com.pss.wsu.officersupport.MySQLiteDbHelper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Person {

	private String firstName;
	private String lastName;
	private String description;
	private double height;
	private int weight;
	private String address;
	private String phone;
	private String statement;
	private String type;
	private int num;
	
	public Person(String firstName, String lastName, String description,
			double height, int weight, String address, String phone,
			String statement, String type, int num) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.height = height;
		this.weight = weight;
		this.address = address;
		this.phone = phone;
		this.statement = statement;
		this.type = type;
		this.num = num;
	}

	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = "unknown";
		this.height = 0;
		this.weight = 0;
		this.address = "unknown";
		this.phone = "unknown";
		this.statement = "none";
	}
	
	public String toString()
	{
		return firstName + " " + lastName;
	}
	
	public long save(SQLiteDatabase database)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteDbHelper.PERSONS_ADDRESS, address);
		values.put(MySQLiteDbHelper.PERSONS_FNAME, firstName);
		values.put(MySQLiteDbHelper.PERSONS_LNAME, lastName);
		values.put(MySQLiteDbHelper.PERSONS_DESCR, description);
		values.put(MySQLiteDbHelper.PERSONS_HEIGHT, height);
		values.put(MySQLiteDbHelper.PERSONS_WEIGHT, weight);
		values.put(MySQLiteDbHelper.PERSONS_PHONE, phone);
		values.put(MySQLiteDbHelper.PERSONS_STATEMENT, statement);
		values.put(MySQLiteDbHelper.PERSONS_TYPE, type);
		return database.insert(MySQLiteDbHelper.TABLE_PERSONS, null, values);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
