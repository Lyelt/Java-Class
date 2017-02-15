//  Starting point for Program 2
//  Birthday.java
//  Nicholas Ghobrial
//
import java.util.*;

//------------------------------------------------------------------------------
public class Birthday{
    public static final String[] months = {"Jan", "Feb", "Mar", "Apr", "May",
    "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", };
    public static final String[] days = { "Sunday", "Monday", "Tuesday",
    "Wednesday", "Thursday", "Friday", "Saturday" };
    public static final int[] startsOn = {4, 0, 0, 3, 5, 1, 3, 6, 2, 4, 0, 2, };
    public static final int[] numberOfDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // The collection of data members stores the STATE of an object.
    // Data members of a class are normally private.   Document each one.
    private String month;   // 3-letter abbreviation for the month.
    private int date;       // Will be 1..31
    private String day;     // The day of the week.
    private int dayNumber;  // Will be 1..365

    //--------------------------------------------------------------------------
    // Compute a new random value for the die.
    // Postcondition: the return value is between 1 and faces.
    Birthday( String m, int d){
        month = m;
        date = d;
        calculateDay();
        calculateNumber();
    }

    //--------------------------------------------------------------------------
    private void calculateDay() {
        int found, k, answer;
        for(k=0; k<12; ++k) {
            if (month.equals(months[k]))  break;
        }
        found = k;
        if (found == 12)
            System.out.println("Your month name was not a valid 3-letter abbreviation.");
        else {
            answer = (startsOn[k] + date -1)%7;
            day = days[answer];
        }
    }

    //---------- A get function gives read-only access to a private data member.
    public String getday(){  return day;  }

    //--------------------------------------------------------------------------
    // Define toString for every class.
    // Return a string that reports the state of the class. Used for debugging.
    public String toString(){
        return month +" " + date ;
    }
    
    //---------------------------------------------------------------------------
    private void calculateNumber(){
    	  int found, k, answer;
    	  int sum = 0;
          for(k=0; k<12; ++k) {
              if (month.equals(months[k]))  break;
              else {
            	  sum += numberOfDays[k];
              }
          }
          found = k;
          if (found == 12)
        	  System.out.println("Your month name was not a valid 3-letter abbreviation.");
          else {
        	  answer = sum + date;
        	  dayNumber = answer;
          }
    }
    
    //-------------------------------------------------------------------------------
    public int getDayNumber(){ return dayNumber; }

    //--------------------------------------------------------------------------
    public static  void  main( String[] args ) {
        int date;
        String monthname;
        Scanner sc = new Scanner( System.in );
        
        System.out.println("Nicholas Ghobrial - Program 2\n");
        System.out.println("\nBirthday Day Calculator, Welcome!");
        System.out.print ("Months are: ");
        for( String s : months) System.out.print( s+"  " );

        System.out.println("\n\nPlease enter your birth month and date:");
        monthname = sc.next();
        date = sc.nextInt();
        Birthday b = new Birthday (monthname, date);

        System.out.printf ( "Your %s birthday is on %s this year. That's day %d of the year.\n\n",
                           b.toString(), b.getday(), b.getDayNumber ());
    }
}

/* Output:
 * Nicholas Ghobrial - Program 2


Birthday Day Calculator, Welcome!
Months are: Jan  Feb  Mar  Apr  May  Jun  Jul  Aug  Sep  Oct  Nov  Dec  

Please enter your birth month and date:
Aug 30
Your Aug 30 birthday is on Sunday this year. That's day 243 of the year.
 */