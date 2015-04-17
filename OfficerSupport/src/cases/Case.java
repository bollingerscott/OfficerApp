package cases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Case implements Serializable {
	
	private HashMap<String, Person> witnessMap; 	//map of name to person object
	private HashMap<Integer, Person> suspectMap; 	//map of suspect # to suspect; # since name may be unknown
	private HashMap<String, Form> formMap;			//map of type of form to form
	private String name;							//name of case
	private String description;						//description of case
	private Integer caseNum;						//# of case
	
	public Case(String name, String description, int caseNum, HashMap<String, Person> witnesses, HashMap<Integer, Person> suspects, HashMap<String, Form> forms) 
	{	
		this.name = name;
		this.description = description;
		this.caseNum = caseNum;
		this.witnessMap = witnesses;
		this.suspectMap = suspects;
		this.formMap = forms;
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

	public HashMap<String, Person> getWitnessMap() {
		return witnessMap;
	}

	public void setWitnessMap(HashMap<String, Person> witnessMap) {
		this.witnessMap = witnessMap;
	}

	public HashMap<Integer, Person> getSuspectMap() {
		return suspectMap;
	}

	public void setSuspectMap(HashMap<Integer, Person> suspectMap) {
		this.suspectMap = suspectMap;
	}

	public HashMap<String, Form> getFormMap() {
		return formMap;
	}

	public void setFormMap(HashMap<String, Form> formMap) {
		this.formMap = formMap;
	}
}
