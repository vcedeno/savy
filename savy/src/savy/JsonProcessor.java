package savy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.*;

public class JsonProcessor 
{
	private static TreeSet<String> areaList = new TreeSet<String>();
	private static TreeSet<String> professorList = new TreeSet<String>();
	private static TreeSet<String> courseNameList = new TreeSet<String>();
	private static TreeSet<String> courseNumberList = new TreeSet<String>();
	private static TreeSet<String> termList = new TreeSet<String>();
	private static TreeSet<String> yearAndTermList = new TreeSet<String>(new CustomComparatorForString());
	private static TreeSet<Integer> yearList = new TreeSet<Integer>();
	private static TreeSet<Float> averageGPAList = new TreeSet<Float>();
	private static TreeSet<Integer> classStrengthList = new TreeSet<Integer>();

	private static HashMap<String, TreeSet<JSONObject>> areaMap = new HashMap<String, TreeSet<JSONObject>>();
	private static HashMap<String, TreeSet<JSONObject>> professorMap = new HashMap<String, TreeSet<JSONObject>>();
	private static HashMap<String, TreeSet<JSONObject>> courseNameMap = new HashMap<String, TreeSet<JSONObject>>();
	private static HashMap<String, TreeSet<JSONObject>> courseNumberMap = new HashMap<String, TreeSet<JSONObject>>();
	private static HashMap<String, TreeSet<JSONObject>> termMap = new HashMap<String, TreeSet<JSONObject>>();
	private static HashMap<String, TreeSet<JSONObject>> yearAndTermMap = new HashMap<String, TreeSet<JSONObject>>();
	private static HashMap<Integer, TreeSet<JSONObject>> yearMap = new HashMap<Integer, TreeSet<JSONObject>>();
	private static HashMap<Float, TreeSet<JSONObject>> averageGPAMap = new HashMap<Float, TreeSet<JSONObject>>();
	private static HashMap<Integer, TreeSet<JSONObject>> classStrengthMap = new HashMap<Integer, TreeSet<JSONObject>>();

	public JsonProcessor() 
	{
		this.preProcess();
	}

	//reads and processes data on start-up 
	public void preProcess() 
	{
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader("C:\\JSON\\data.json"));

			JSONArray jsonArray = (JSONArray) obj;
			JSONObject jsonObject = new JSONObject();
			System.out.println(jsonArray.size());
			for (int i = 0; i < jsonArray.size(); i++) 
			{
				jsonObject = (JSONObject) jsonArray.get(i);
				
				CustomComparator myComp = new CustomComparator();
				CustomComparator2 myOtherComp = new CustomComparator2();

				String courseName = (String) jsonObject.get(StringLiterals.CourseName);				
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(courseNameMap, courseNameList, courseName, jsonObject, myComp);
				
				
				String courseNumber = (String) jsonObject.get(StringLiterals.CourseNumber);
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(courseNumberMap, courseNumberList, courseNumber, jsonObject, myComp);


				String professor = (String) jsonObject.get(StringLiterals.Professor);
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(professorMap, professorList, professor, jsonObject, myComp);

				Integer year = ((Long) (jsonObject.get(StringLiterals.Year))).intValue();
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(yearMap, yearList, year, jsonObject);

				String term = (String) jsonObject.get(StringLiterals.Term);
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(termMap, termList, term, jsonObject, myComp);
				
				String yearAndTerm = (String) jsonObject.get(StringLiterals.YearAndTerm);
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(yearAndTermMap, yearAndTermList, yearAndTerm, jsonObject, myOtherComp);

				Float averageGPA = ((Double) jsonObject.get(StringLiterals.AverageGPA)).floatValue();
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(averageGPAMap, averageGPAList, averageGPA, jsonObject);


				String area = (String) jsonObject.get(StringLiterals.Area);
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(areaMap, areaList, area, jsonObject, myComp);

				Integer classStrength = ((Long) jsonObject.get(StringLiterals.ClassStrength)).intValue();
				UtilFunctions.addToMapAndListIfNotAlreadyPresent(classStrengthMap, classStrengthList, classStrength, jsonObject);


				// kept for completeness
				/*Long numberOfAs = (Long) jsonObject
						.get(StringLiterals.Number_A);
				Long numberOfBs = (Long) jsonObject
						.get(StringLiterals.Number_B);
				Long numberOfCs = (Long) jsonObject
						.get(StringLiterals.Number_C);
				Long numberOfDs = (Long) jsonObject
						.get(StringLiterals.Number_D);
				Long numberOfEs = (Long) jsonObject
						.get(StringLiterals.Number_E);
				Long numberOfFs = (Long) jsonObject
						.get(StringLiterals.Number_F);*/

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	// Filters progressively on all specified filterParameters
	public JSONObject getFilteredResults(JSONObject inputObject) 
	{
		JSONObject returnObject = new JSONObject();
		JSONArray resultList = new JSONArray();
		JSONObject jsonArrayOfFilterParameters = null;
		
		JSONParser parser = new JSONParser();
		
		JSONArray objectWithParameters = (JSONArray) inputObject.get("value");

		for (int i = 0; i < objectWithParameters.size(); i++) 
		{
			try
			{
				jsonArrayOfFilterParameters = (JSONObject) parser.parse((String)objectWithParameters.get(i));
			} catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String filterParameter = (String) jsonArrayOfFilterParameters.get(StringLiterals.Name);
			JSONObject filterValues = (JSONObject) jsonArrayOfFilterParameters.get(StringLiterals.Value);
			
			//System.out.println("Filter parameter: " + filterParameter);
			//System.out.println("FilterValues: " + filterValues.toString());
			
			List<String> filterParameterValues = new ArrayList<String>();
	
			for (int j = 0; j < filterValues.size(); j++) 
			{
				filterParameterValues.add((String) filterValues.get(String.valueOf(j)));
			}
			
			List<JSONObject> filteredResults = new ArrayList<JSONObject>();
			
			if(filterParameterValues.size()> 0)
			{				
				switch(filterParameter)
				{
					case "area" : 	filteredResults = UtilFunctions.populateJSONObjects(areaMap, filterParameterValues);
									break;
					
					case "professor" : 	filteredResults = UtilFunctions.populateJSONObjects(professorMap, filterParameterValues);
										break;
										
					case "course" : filteredResults = UtilFunctions.populateJSONObjects(courseNameMap, filterParameterValues);
										break;
										
					case "classStrength" : filteredResults = UtilFunctions.populateJSONObjectsForARangeOfInt(classStrengthMap, filterParameterValues);
										break;
					
					case "averageGPA" : filteredResults = UtilFunctions.populateJSONObjectsForARangeOfFloat(averageGPAMap, filterParameterValues);
										break;
										
					case "year" : filteredResults = UtilFunctions.populateJSONObjectsForARangeOfInt(yearMap, filterParameterValues);
										break;
					
					case "term" : filteredResults = UtilFunctions.populateJSONObjects(termMap, filterParameterValues);
										break;
				}
				
				//check if this is the first pass, of the first filtering criterion
				if(resultList.size() ==0)
				{
					for(JSONObject object: filteredResults)
					{
						resultList.add(object);
					}				
				}
				else
				{
					//filter down the resultList further by taking intersection with the filteredResults
					resultList = UtilFunctions.findIntersectionOfLists(resultList, filteredResults);
				}
				
				System.out.println("ResultList size: " + resultList.size());
				System.out.println("ResultList: " + resultList.toString());
				
			}
		}
		
		//returns a JSONArray with course to JSONObject mapping, to make it easier to process in Javascript
		returnObject.put("CoursesData", UtilFunctions.processResultList(resultList));
		returnObject.put("YearAndTermData", UtilFunctions.getYearAndTermListForFilteredResults(resultList));
		
		return returnObject;

	}
	
	//for testing logic
	public JSONArray filterOnOneParameter(JSONObject inputObject)
	{
		JSONArray resultList = new JSONArray();
		
		JSONArray jsonArray = (JSONArray) inputObject.get(StringLiterals.Value);
		String filterParameter = (String) inputObject.get(StringLiterals.Name);
				
		List<String> filterParameterValues = new ArrayList<String>();

		for(int i=0; i< jsonArray.size(); i++)
		{	
			filterParameterValues.add((String)jsonArray.get(i));
		}
		
		switch(filterParameter)
		{
			case "area" : 	return UtilFunctions.populateJSONObjects(areaMap, filterParameterValues);
			case "course" : return UtilFunctions.populateJSONObjects(courseNameMap, filterParameterValues);
			case "professor" : return UtilFunctions.populateJSONObjects(professorMap, filterParameterValues);
		}
		
		return resultList;
	}
	
	// for retrieving all Values for a given StringParameter, for initial filter population
	public JSONArray getListForStringParameter(String parameter) 
	{
		JSONArray resultArray = new JSONArray();
		TreeSet<String> listToProcess = new TreeSet<String>();

		// Create a JSONObject list and return
		switch (parameter) {
		case StringLiterals.Area:
									listToProcess = areaList;
									break;
		case StringLiterals.CourseName:
									listToProcess = courseNameList;
									break;
		case StringLiterals.CourseNumber:
									listToProcess = courseNumberList;
									break;
		case StringLiterals.Professor:
									listToProcess = professorList;
									break;
		case StringLiterals.YearAndTerm:
									listToProcess = yearAndTermList;
									break;
		case StringLiterals.Term:
									listToProcess = termList;
									break;
		default:
									System.out.println("not a valid string Parameter");
									break;
		}
		for (String listItem : listToProcess) 
		{
			// System.out.println("[");
			JSONObject jsonItem = new JSONObject();
			jsonItem.put(parameter, listItem);
			// System.out.println("{"+parameter+":"+listItem+"}");
			resultArray.add(jsonItem);
			// System.out.println("]");
		}

		return resultArray;
	}
	
	// for retrieving all Values for a given Integer Parameter, for initial filter population
	public JSONArray getListForIntegerParameter(String parameter) 
	{
		JSONArray resultArray = new JSONArray();
		TreeSet<Integer> genericList = new TreeSet<Integer>();

		// Create a JSONObject list and return
		switch (parameter) {
		case StringLiterals.ClassStrength:
									genericList = classStrengthList;
									break;
		case StringLiterals.Year:
									genericList = yearList;
									break;
		
		default:
									System.out.println("not a valid integer Parameter");
									break;
		}
		for (Integer listItem : genericList) 
		{
			// System.out.println("[");
			JSONObject jsonItem = new JSONObject();
			jsonItem.put(parameter, listItem);
			// System.out.println("{"+parameter+":"+listItem+"}");
			resultArray.add(jsonItem);
			// System.out.println("]");
		}

		return resultArray;
	}
	
	// for retrieving all Values for a given FloatParameter, for initial filter population
	public JSONArray getListForFloatParameter(String parameter) 
	{
		JSONArray resultArray = new JSONArray();
		for (Float listItem : averageGPAList) 
		{
			// System.out.println("[");
			JSONObject jsonItem = new JSONObject();
			jsonItem.put(parameter, listItem);
			// System.out.println("{"+parameter+":"+listItem+"}");
			resultArray.add(jsonItem);
			// System.out.println("]");
		}

		return resultArray;
	}
	
	public JSONArray getDataForAllCourses()
	{
		return UtilFunctions.convertMapToJSONArray(courseNameMap);
	}
	
}
