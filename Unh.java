package GPA;
import java.util.*;
import java.io.*;

	//------------------------------------------------------------------------------

public class Unh {

	private Scanner sc;
	ArrayList<Course> Courses = new ArrayList<Course>(); 
	
	//------------------------------------------------------------------------------
	
	public Unh(Date now) throws FileNotFoundException{				// Declares that FileNotFoundException may be thrown
		sc = new Scanner( new File("transcripts.txt"));
		String grade;
		String course;
		String name;
		
		System.out.println("University of New Haven" + now); 	// University name and date
		
		name = sc.nextLine();
		System.out.println("Student:   " + name +"\n"); 		// Obtains the name of student
		
		
		while (sc.hasNext() ){									// Reads to end of file
			try{
				grade = sc.next();
				course = sc.nextLine();
				Courses.add(new Course(grade, course));			// Add course to ArrayList
			}
			catch (InputMismatchException e){					// Catch InputMismatchException
				System.out.println("Error, InputMismatchException");
				e.printStackTrace();
			}
		}
		sc.close();
		System.out.println("File Read.\n");		// Confirms that the file was read
		CalculateGPA();							
		
	}
	
	//------------------------------------------------------------------------------
	
	public void CalculateGPA(){
		int honorPoints = 0;			// Initialize honor points to zero
		int numberOfCourses;			// Number of courses being taken
		int credits;
		double GPA;						
		
		System.out.println("Grade:  Course:");
		
		for (int k=0; k<Courses.size(); ++k){							// Prints courses and grades in a neat table
				System.out.println(Courses.get(k).toString());
				honorPoints = Courses.get(k).getHP() + honorPoints;		// SHOULD sum up the honor points for each course object
		}
		
		
		
		numberOfCourses = Courses.size();								// Gets number of courses and exits if student is taking zero
			if (numberOfCourses == 0){
				System.out.println("Student is taking zero courses. Exiting GPA Calculator.");
				System.exit(0);
			}
			
		credits = numberOfCourses * 3;	
		GPA = honorPoints / credits;									// Calculates GPA. (Note: Since I couldn't figure out how to get the honor points working properly, GPA is calculated as zero)
		
		System.out.println("\nHonor Points:       " + honorPoints);		// Used for test output, but left in since the program was not entirely successful
		System.out.println("Courses Taken:     " + numberOfCourses);	
		System.out.println("Credits Attempted: " + credits);	
		System.out.printf("\nGPA is %.3f\n", GPA);						// Prints formatted GPA
		
		
		
	}
	
	//------------------------------------------------------------------------------
	
	public static void main(String[] args){
		System.out.println("Nicholas Ghobrial - Midterm Exam\n");
		
		try{
			Date now = new Date();
			Unh U = new Unh(now);					// Creates an instance of the Unh class
		}
		catch (IOException e){						// Catches IOExceptions and aborts
			System.out.println("Exception occurred");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
