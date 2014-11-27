package util;

import java.util.Comparator;

import org.json.simple.JSONObject;

public class CustomComparator2 implements Comparator<JSONObject>
{

	@Override
	public int compare(JSONObject json1, JSONObject json2)
	{
		Long year1 = (Long) json1.get(StringLiterals.Year);
		Long year2 = (Long) json2.get(StringLiterals.Year);
		
		String term1 = (String) json1.get(StringLiterals.Term);
		String term2 = (String) json2.get(StringLiterals.Term);
		
		if(year1.intValue() == year2.intValue())
		{
			return term2.compareTo(term1);
		}
		return year1.compareTo(year2);
		
	}

}
