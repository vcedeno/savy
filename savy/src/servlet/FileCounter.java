package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import savy.FileDao;
import savy.JsonProcessor;
import util.*;

/**
 * Servlet implementation class FileCounter
 */

@WebServlet(name = "FileCounter", urlPatterns = { "/FileCounter" })
public class FileCounter extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	int count;
	private FileDao dao;
	private JsonProcessor processor;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// Set a cookie for the user, so that the counter does not increate
		// every time the user press refresh
		HttpSession session = request.getSession(true);
		response.setContentType("application/json");
		//dao = new FileDao();

		// JSONArray allLists=new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONArray relevantCourseData = processor.getDataForAllCourses();
		jsonObject.put(StringLiterals.ListOfAreas, processor.getListForStringParameter(StringLiterals.Area));
		jsonObject.put(StringLiterals.ListOfCourses, processor.getListForStringParameter(StringLiterals.CourseName));
		jsonObject.put(StringLiterals.ListOfProfessors, processor.getListForStringParameter(StringLiterals.Professor));
		jsonObject.put(StringLiterals.ListOfCourseNumbers, processor.getListForStringParameter(StringLiterals.CourseNumber));
		jsonObject.put(StringLiterals.ListOfYears, processor.getListForIntegerParameter(StringLiterals.Year));
		jsonObject.put(StringLiterals.ListOfTerms, processor.getListForStringParameter(StringLiterals.Term));
		jsonObject.put(StringLiterals.ListOfYearsAndTerms, UtilFunctions.getRelevantYearAndTermValues(relevantCourseData));
		jsonObject.put(StringLiterals.ListOfClassStrengths, processor.getListForIntegerParameter(StringLiterals.ClassStrength));
		jsonObject.put(StringLiterals.ListOfAverageGPAs, processor.getListForFloatParameter(StringLiterals.AverageGPA));
		
		
		//System.out.println(processor.getListForStringParameter(StringLiterals.YearAndTerm).toString());
		jsonObject.put(StringLiterals.CoursesData, relevantCourseData);

		// response.getWriter().write(preprocessor.getAllAreas().toJSONString());
		response.getWriter().write(jsonObject.toJSONString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String jsonResponse = request.getParameter("json_string");

		JSONObject jsonobject = new JSONObject();
		JSONParser parser = new JSONParser();

		try 
		{
			jsonobject = (JSONObject) parser.parse(jsonResponse);
		} catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Received: " + jsonobject.toString());
		//System.out.println((JSONArray) jsonobject.get("value"));
		//System.out.println((String) jsonobject.get("name"));
		
		JSONObject resultObject = processor.getFilteredResults(jsonobject);
				
		//JSONArray resultArray = processor.filterOnOneParameter(jsonobject);		
		System.out.println("Sending response" + resultObject.toString());
		
		response.getWriter().write(resultObject.toJSONString());
		
		

	}

	@Override
	public void init() throws ServletException {

		try {
			// count = dao.getCount();
			// preprocessor.preProcess();
			processor = new JsonProcessor();
		} catch (Exception e) {
			getServletContext().log("An exception occurred in FileCounter", e);
			throw new ServletException("An exception occurred in FileCounter"
					+ e.getMessage());
		}
	}

	public void destroy() {
		super.destroy();
		try {
			dao.save(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}