package cases;

import java.io.Serializable;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public abstract class Form extends Activity implements Serializable {
	
	private String type;
	
	public void save(SQLiteDatabase database){}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String name)
	{
		this.type = name;
	}
	
	public String toString()
	{
		return type;
	}
}
