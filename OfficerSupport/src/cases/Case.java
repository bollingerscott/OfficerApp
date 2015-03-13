package cases;

import java.util.HashMap;

public class Case {
	
	private HashMap<String, Person> witnessMap; 		//map of name to person object
	private HashMap<Integer, Person> suspectMap; 		//map of suspect # to suspect; # since name may be unknown
	private HashMap<String, Form> formMap;			//map of type of form to form
	private String[] witnesses;
	private String[] suspects;
	private String[] forms;
	private String name;							//name of case
	private String description;						//description of case
	private Integer caseNum;						//# of case
	
	public Case(String[] witnesses, String[] suspects, String[] forms, String name, String description, int caseNum) 
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
		//witnesses[witnesses.length-1] = p.getFirstName() + " " + p.getLastName();
		witnessMap.put(p.getFirstName() + " " + p.getLastName(), p);
	}
	
	void deleteWitness(Person p)
	{
		witnessMap.remove(p);
	}
	
	void addSuspect(Integer num, Person p)
	{
		suspectMap.put(num, p);
	}
	
	void deleteSuspect(Person p)
	{
		suspectMap.remove(p);
	}
	
	void addForm(String type, Form f)
	{
		formMap.put(type, f);
	}
	
	void deleteForm(Form f)
	{
		formMap.remove(f);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(Integer caseNum) {
		this.caseNum = caseNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return "Case " + caseNum.toString() + ": " + name + "\n" + description;
	}

	public String[] getWitnesses() {
		return witnesses;
	}

	public void setWitnesses(String[] witnesses) {
		this.witnesses = witnesses;
	}

	public String[] getSuspects() {
		return suspects;
	}

	public void setSuspects(String[] suspects) {
		this.suspects = suspects;
	}

	public String[] getForms() {
		return forms;
	}

	public void setForms(String[] forms) {
		this.forms = forms;
	}
	
	public String getString(String[] s)
	{
		String result = "";
		for (int i = 0; i < s.length; i++)
		{
			result = result.concat(s[i]);
			if (i != s.length-1)
			{
				result = result.concat(", ");
			}
		}
		return result;
	}
}
