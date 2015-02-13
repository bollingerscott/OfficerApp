package cases;

import java.util.HashMap;

public abstract class Case {
	
	HashMap<String, Person> witnesses; 		//map of name to person object
	HashMap<Integer, Person> suspects; 		//map of suspect # to suspect; # since name may be unknown
	HashMap<String, Form> forms;			//map of type of form to form
	String name;							//name of case
	String description;						//description of case
	int caseNum;							//# of case
	
	public Case(HashMap<String, Person> witnesses, HashMap<Integer, Person> suspects, HashMap<String, Form> forms, String name, String description, int caseNum) 
	{	
		this.witnesses = witnesses;
		this.suspects = suspects;
		this.forms = forms;
		this.name = name;
		this.description = description;
		this.caseNum = caseNum;
	}

	void addWitness(Person p)
	{
		witnesses.put(p.getFirstName() + " " + p.getLastName(), p);
	}
	
	void deleteWitness(Person p)
	{
		witnesses.remove(p);
	}
	
	void addSuspect(Integer num, Person p)
	{
		suspects.put(num, p);
	}
	
	void deleteSuspect(Person p)
	{
		suspects.remove(p);
	}
	
	void addForm(String type, Form f)
	{
		forms.put(type, f);
	}
	
	void deleteForm(Form f)
	{
		forms.remove(f);
	}

	public String getName() {
		return name;
	}

	public void setFirstName(String name) {
		this.name = name;
	}

	public int getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(int caseNum) {
		this.caseNum = caseNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
