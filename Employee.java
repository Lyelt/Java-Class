package payroll;
import java.util.*;

public abstract class Employee {
	protected String loginName;
	protected double baseSalary;
	protected String name;
	protected Date added;
	protected int ID;
	
	public Employee(String l, double b, String n, Date now, int i) {
		loginName = l;
		baseSalary = b;
		name = n;
		added = now;
		ID = i;
	}
	
	public void setSalary(double s){
		baseSalary = s;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public String getLoginName(){
		return loginName;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return String.format("%05d\t %-15s\t %10.2f\t %s\n", ID, loginName, baseSalary/*, added*/, name); 	
	}
	
	public int nextID(int i){
		return (++i);
	}
	
	public int getID(){ return ID; }
	
	public abstract boolean isHourly();
	public abstract double getPay(int h);
	public abstract String payString();
	public abstract String typeTest();
}
	
	

