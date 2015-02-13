package cases;

public class Person {

	private String firstName;
	private String lastName;
	private String description;
	private double height;
	private int weight;
	private String address;
	private String phone;
	private String statement;
	
	public Person(String firstName, String lastName, String description,
			double height, int weight, String address, String phone,
			String statement) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.height = height;
		this.weight = weight;
		this.address = address;
		this.phone = phone;
		this.statement = statement;
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
}
