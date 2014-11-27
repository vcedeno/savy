package util;

import java.util.Comparator;

import org.json.simple.JSONObject;

public class CustomComparator implements Comparator<JSONObject>
{

	@Override
	public int compare(JSONObject json1, JSONObject json2)
	{
		String courseName1 = (String) json1.get(StringLiterals.CourseName);	
		String courseName2 = (String) json2.get(StringLiterals.CourseName);
		
		String area1 = (String) json1.get(StringLiterals.Area);
		String area2 = (String) json2.get(StringLiterals.Area);
		
		Long year1 = (Long) json1.get(StringLiterals.Year);
		Long year2 = (Long) json2.get(StringLiterals.Year);
		
		String term1 = (String) json1.get(StringLiterals.Term);
		String term2 = (String) json2.get(StringLiterals.Term);
		
		if(area1.equalsIgnoreCase(area2))
		{
			if(courseName1.equalsIgnoreCase(courseName2))
			{
				if(year1.intValue() == year2.intValue())
				{
					return term2.compareTo(term1);
				}
				return year1.compareTo(year2);
			}
			return courseName1.compareTo(courseName2);
		}
		
		return area1.compareTo(area2);
	}
	
}
