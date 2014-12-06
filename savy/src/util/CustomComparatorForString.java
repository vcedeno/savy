package util;

import java.util.Comparator;

public class CustomComparatorForString implements Comparator<String>
{

	@Override
	public int compare(String str1, String str2)
	{
		int spaceIndex1 = str1.indexOf(" ");
		int spaceIndex2 = str2.indexOf(" ");
		
		String term1 = str1.substring(0, spaceIndex1);
		String term2 = str2.substring(0, spaceIndex2);
		
		int year1 = Integer.parseInt(str1.substring(spaceIndex1 + 1, str1.length()));
		int year2 = Integer.parseInt(str2.substring(spaceIndex2 + 1, str2.length()));
		
		if(year1 == year2)
		{
			return term2.compareTo(term1);
		}
		return (year1> year2)? 1 : -1;
	}
	


}
