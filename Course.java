package GPA;
import java.util.*;
import java.io.*;

public class Course {
	private String grade;		// Student's grade for each course
	private String course;		// Course information, such as name and number
	private int honorPoints;	// Honor points based on grade
	
	//------------------------------------------------------------------------------
	// Constructor for Course object:-----------------------------------------------
	
	public Course( String g, String c){
		grade = g;
		course = c;
		honorPoints = calculateHonorPoints(grade);
	}
	
	//------------------------------------------------------------------------------
	
	public int calculateHonorPoints(String g){
		// Calculates the honor points based on the grade
		int points = 0;
		
		switch (g){
			case "A": points = 12; 
			case "B": points = 9;
			case "C": points = 6;
			case "D": points = 3;
			case "F": points = 0;
		}
		
		return points;
	}
	
	//------------------------------------------------------------------------------
	//Public accessor for honor points:---------------------------------------------- 
	
	public int getHP(){
		return honorPoints;
	}
	
	//------------------------------------------------------------------------------

	public String toString(){
		return String.format("%s      %s", grade, course);
	}
	
	
}
