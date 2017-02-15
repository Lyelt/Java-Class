package payroll;

import java.util.Date;

public class Salaried extends Employee {

	double pay;
	
	public Salaried( String l, double b, String n, Date now, int i){
		super( l, b, n, now, i);
	}
	
	public double getPay(int h){
		pay = baseSalary / h;
		return pay;
	}
	
	public boolean isHourly(){
		return false;
	}
	
	// toString() used for payroll data
	public String payString(){
		return String.format("%05d\t %10.2f\t %s", ID, pay, name);
	}
	
	// Is the employee Hourly or Salaried?
	public String typeTest(){
		return String.format("Salaried");
	}
}
