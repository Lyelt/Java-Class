package payroll;

import java.util.Date;

public class Hourly extends Employee {

	double pay;
	
	public Hourly( String l, double b, String n, Date now, int i ){
		super( l, b, n, now, i);
	}
	
	public double getPay(int h){
		pay = baseSalary / h;
		return pay;
	}
	
	public boolean isHourly(){
		return true;
	}
	
	// toString() used for payroll data
	public String payString(){
		return String.format("%05d\t %10.2f\t %s", ID, pay, name);
	}
	
	// Is the employee hourly or salaried?
	public String typeTest(){
		return String.format("Hourly");
	}
}
