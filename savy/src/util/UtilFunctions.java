package util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UtilFunctions 
{
	public static void addToMapAndListIfNotAlreadyPresent(HashMap<String, TreeSet<JSONObject>> inputHashMap, TreeSet<String> inputList, String key, JSONObject value, Comparator comparator)
	{
		if(inputHashMap.containsKey(key))
		{
			if(!inputHashMap.get(key).contains(value))
			{
				inputHashMap.get(key).add(value);
			}			
		}
		else
		{
			TreeSet<JSONObject> newList = new TreeSet<JSONObject>(comparator);
			newList.add(value);
			
			inputHashMap.put(key, newList);
			inputList.add(key);
		}
	}
	
	public static void addToMapAndListIfNotAlreadyPresent(HashMap<Integer, TreeSet<JSONObject>> inputHashMap, TreeSet<Integer> inputList, Integer key, JSONObject value)
	{
		if(inputHashMap.containsKey(key))
		{
			if(!inputHashMap.get(key).contains(value))
			{
				inputHashMap.get(key).add(value);
			}
		}
		else
		{
			TreeSet<JSONObject> newList = new TreeSet<JSONObject>(new CustomComparator());
			newList.add(value);
			
			inputHashMap.put(key, newList);
			inputList.add(key);
		}
	}
	
	public static void addToMapAndListIfNotAlreadyPresent(HashMap<Float, TreeSet<JSONObject>> inputHashMap, TreeSet<Float> inputList, Float key, JSONObject value)
	{
		if(inputHashMap.containsKey(key))
		{
			if(!inputHashMap.get(key).contains(value))
			{
				inputHashMap.get(key).add(value);
			}
		}
		else
		{
			TreeSet<JSONObject> newList = new TreeSet<JSONObject>(new CustomComparator());
			newList.add(value);
			
			inputHashMap.put(key, newList);
			inputList.add(key);
		}
	}
	
	public static JSONArray populateJSONObjects(HashMap<String, TreeSet<JSONObject>> mapToFilter, List<String> filterValues)
	{
		JSONArray resultList = new JSONArray();	
		TreeSet<JSONObject> jsonObjectList = new TreeSet<JSONObject>(new CustomComparator());
		
		for(String filterValue : filterValues)
		{			
			jsonObjectList = mapToFilter.get(filterValue);
			
			if(jsonObjectList!= null)
			{
				for(JSONObject anObject : jsonObjectList)
				{
					resultList.add(anObject);
				}
			}
			//System.out.println("Size of resultList = " + resultList.size());
		}
		
		return resultList;
	}
	
	public static JSONArray populateJSONObjectsForARangeOfFloat(HashMap<Float, TreeSet<JSONObject>> mapToFilter, List<String> filterValues)
	{
		JSONArray resultList = new JSONArray();	
		TreeSet<JSONObject> jsonObjectList = new TreeSet<JSONObject>(new CustomComparator());
		
		float minValue = Float.parseFloat(filterValues.get(0));
		float maxValue = Float.parseFloat(filterValues.get(1));
		
		for(float key : mapToFilter.keySet())
		{
			if(key>=minValue && key<=maxValue)
			{
				jsonObjectList = mapToFilter.get(key);
				
				if(jsonObjectList!= null)
				{
					for(JSONObject anObject : jsonObjectList)
					{
						resultList.add(anObject);
					}
				}
			}
		}
		
		return resultList;
	}
	
	public static JSONArray populateJSONObjectsForARangeOfInt(HashMap<Integer, TreeSet<JSONObject>> mapToFilter, List<String> filterValues)
	{
		JSONArray resultList = new JSONArray();	
		TreeSet<JSONObject> jsonObjectList = new TreeSet<JSONObject>(new CustomComparator());
		
		int minValue = Integer.parseInt(filterValues.get(0));
		int maxValue = Integer.parseInt(filterValues.get(1));
		
		for(int key : mapToFilter.keySet())
		{
			if(key>=minValue && key<=maxValue)
			{
				jsonObjectList = mapToFilter.get(key);
				
				if(jsonObjectList!= null)
				{
					for(JSONObject anObject : jsonObjectList)
					{
						resultList.add(anObject);
					}
				}
			}
		}
		
		return resultList;
	}
	
	
	public static JSONArray findIntersectionOfLists(JSONArray baseList, List<JSONObject> secondList)
	{
		//System.out.println("BaseList size before filtering: " + baseList.size());
		JSONArray intersectionArray = new JSONArray();
		
		for(int i=0; i< baseList.size(); i++)
		{
			JSONObject item = (JSONObject) baseList.get(i);
			if(secondList.contains(item))
			{
				intersectionArray.add(item);
				//System.out.println("IntersectionArray size during filtering: " + intersectionArray.size());
			}
		}		
		
		return intersectionArray;
	}
	
	public static JSONArray processResultList(JSONArray inputList)
	{
		HashMap<String, TreeSet<JSONObject>> outputMap = new HashMap<String, TreeSet<JSONObject>>();
		
		for(int i=0; i< inputList.size(); i++)
		{
			JSONObject currentObject = (JSONObject) inputList.get(i);
			String courseName = (String) currentObject.get(StringLiterals.CourseName);
			
			if(outputMap.containsKey(courseName))
			{
				outputMap.get(courseName).add(currentObject);
			}
			else
			{
				TreeSet<JSONObject> jsonObjectList = new TreeSet<JSONObject>(new CustomComparator());
				jsonObjectList.add(currentObject);
				outputMap.put(courseName, jsonObjectList);
			}			
		}
		
		return convertMapToJSONArray(outputMap);
		
	}
	
	public static JSONArray getYearAndTermListForFilteredResults(JSONArray inputList)
	{
		TreeSet<JSONObject> sortedList = new TreeSet<JSONObject>(new CustomComparator2());
		
		for(int i=0; i< inputList.size(); i++)
		{
			JSONObject currentObject = (JSONObject) inputList.get(i);
			sortedList.add(currentObject);
		}
		
		JSONArray list = new JSONArray();
		
		for(JSONObject obj : sortedList)
		{
			list.add((String)obj.get(StringLiterals.YearAndTerm));
		}
		return list;
		
	}
	
	public static JSONArray convertMapToJSONArray (HashMap<String, TreeSet<JSONObject>> inputMap)
	{
		JSONArray returnObject = new JSONArray();
		TreeSet<JSONObject> jsonObjectListToTraverse = new TreeSet<JSONObject>(new CustomComparator());
		for(String key : inputMap.keySet())
		{
			jsonObjectListToTraverse = inputMap.get(key);
			JSONArray list = new JSONArray();
			
			for(JSONObject obj : jsonObjectListToTraverse)
			{
				list.add(obj);
			}
			
			JSONObject keyValueObject = new JSONObject();
			
			keyValueObject.put(StringLiterals.Name, key);
			keyValueObject.put(StringLiterals.Value, list);
			
			returnObject.add(keyValueObject);
		}
		
		return returnObject;
	}
	


}
