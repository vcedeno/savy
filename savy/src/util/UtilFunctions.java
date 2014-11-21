package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UtilFunctions 
{
	public static void addToMapAndListIfNotAlreadyPresent(HashMap<String, List<JSONObject>> inputHashMap, List<String> inputList, String key, JSONObject value)
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
			List<JSONObject> newList = new ArrayList<JSONObject>();
			newList.add(value);
			
			inputHashMap.put(key, newList);
			inputList.add(key);
		}
	}
	
	public static void addToMapAndListIfNotAlreadyPresent(HashMap<Integer, List<JSONObject>> inputHashMap, List<Integer> inputList, Integer key, JSONObject value)
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
			List<JSONObject> newList = new ArrayList<JSONObject>();
			newList.add(value);
			
			inputHashMap.put(key, newList);
			inputList.add(key);
		}
	}
	
	public static void addToMapAndListIfNotAlreadyPresent(HashMap<Float, List<JSONObject>> inputHashMap, List<Float> inputList, Float key, JSONObject value)
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
			List<JSONObject> newList = new ArrayList<JSONObject>();
			newList.add(value);
			
			inputHashMap.put(key, newList);
			inputList.add(key);
		}
	}
	
	public static JSONArray populateJSONObjects(HashMap<String, List<JSONObject>> mapToFilter, List<String> filterValues)
	{
		JSONArray resultList = new JSONArray();	
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		
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
	
	public static JSONArray populateJSONObjectsForARangeOfFloat(HashMap<Float, List<JSONObject>> mapToFilter, List<String> filterValues)
	{
		JSONArray resultList = new JSONArray();	
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		
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
	
	public static JSONArray populateJSONObjectsForARangeOfInt(HashMap<Integer, List<JSONObject>> mapToFilter, List<String> filterValues)
	{
		JSONArray resultList = new JSONArray();	
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		
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
		HashMap<String, List<JSONObject>> outputMap = new HashMap<String, List<JSONObject>>();
		
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
				List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
				jsonObjectList.add(currentObject);
				outputMap.put(courseName, jsonObjectList);
			}			
		}
		
		return convertMapToJSONArray(outputMap);
		
	}
	
	public static JSONArray convertMapToJSONArray (HashMap<String, List<JSONObject>> inputMap)
	{
		JSONArray returnObject = new JSONArray();
		List<JSONObject> jsonObjectListToTraverse = new ArrayList<JSONObject>();
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
