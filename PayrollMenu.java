/*	Nicholas Ghobrial
 *  Program 6 - Payroll GUI
 */

package payroll;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;       // For centering
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;   // VBox and HBox
import javafx.scene.media.AudioClip;
import javafx.scene.control.*;  // TextArea

import java.util.*;
import java.io.*;
import java.net.URL;

import javafx.scene.text.*;

public class PayrollMenu extends Application {
// Create all class variables
	Stage st;
	
	Button loginBut 	 = new Button("Login");
	Button quitBut 		 = new Button("Exit");
	Button createBut 	 = new Button("Create Employee");
	Button editBut 		 = new Button("Edit Employee");
	Button payrollBut 	 = new Button("Do Payroll");
	Button editNameBut 	 = new Button("Edit Name");
	Button editSalaryBut = new Button("Edit Salary");
	Button fireBut 		 = new Button("Fire Employee");
	Button OKBut 		 = new Button("OK");
	
	final ToggleGroup payType   = new ToggleGroup();
		RadioButton hourlyBut   = new RadioButton("Hourly  ");
		RadioButton salariedBut = new RadioButton("Salaried");
	
	ArrayList<Employee> Employees    = new ArrayList<Employee>();
	ArrayList<Employee> oldEmployees = new ArrayList<Employee>();
	
	File EF;
	StringBuilder sbEmployees  = new StringBuilder();
	StringBuilder payEmployees = new StringBuilder();
	String paidList;
	String employeeList;
	String currentUser;
	
	int currentID;
	int currentPos;
	int hours;
	int count;
	double amountPaid;
	boolean hourly, salaried;
	
	Font fn = Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 11);
	
	TextField loginField;
	TextField passwordField;
	TextField nameField;
	TextField usernameField;
	TextField editField;
	TextField salaryField;
	TextField editEmployee;
	TextField hourField;

	TextArea employeeArea;
	TextArea payrollArea;
	
	Text prompt = new Text();
	Text header = new Text("ID:\t\t Login:\t\t Salary:\t\t Name:\n");
	
	//----------------------------------------------------------------------------------------------------------------------------------
	
	public void start( Stage st ) {
		this.st = st;
		readFile();			// Read the input file and build the employee string
		buildString();

		if (Employees.isEmpty()){
			buildGui3();	// Go to new employee screen if there are no employees
		}
		else{
			buildGui();		// Go to login screen if employees exist
		}
		
	/*----------------------Set handler for login button--------------------------------*/
		loginBut.setOnAction(e ->{
			System.out.println("Logged in");
			
			if (loginField.getText().equals(Employees.get(0).getLoginName())){         
				buildGui2();
			}
			else { 
				buildGui6();
				for (int k = 0; k < Employees.size(); ++k){
					if (loginField.getText().equals(Employees.get(k).getLoginName())){
						currentID = Employees.get(k).getID();
						System.out.println("Current ID: " + currentID);
					}
				}
			}
		});
		
	/*------------------------Set handler for exit button-------------------------------*/
		quitBut.setOnAction(e ->{
			writeFile();
			System.out.println("Exiting");
			Platform.exit();
		});
		
	/*-------------------------Set handler for create button---------------------------*/
		createBut.setOnAction(e ->{
			System.out.println("Create Employee Pressed");
			buildGui3();
		});
		
	/*-------------------------Set handler for edit button---------------------------*/
		editBut.setOnAction(e ->{
			System.out.println("Edit Employee Pressed");
			buildGui4();
		});
		
	/*-------------------------Set handler for payroll button---------------------------*/
		payrollBut.setOnAction(e ->{
			System.out.println("Payroll Button Pressed");
			for ( Employee em : Employees ){
				doPayroll(em);	
			}
		});
	/*--------------------------Set handler for hourly radio button--------------------*/
		hourlyBut.setOnAction( e ->{
			System.out.println("Hourly Employee Created");
			hourly = true;
			newEmployee();
			employeeList = sbEmployees.toString();
			hourlyBut.setSelected(false);         
			buildGui2();
		});
	
	/*--------------------------Set handler for salaried radio button--------------------*/
		salariedBut.setOnAction( e ->{
			System.out.println("Salaried Employee Created");
			salaried = true;
			newEmployee();
			employeeList = sbEmployees.toString();
			salariedBut.setSelected(false);       
			buildGui2();
		});
		
	/*--------------------------Set handler for edit name button-------------------------*/
		editNameBut.setOnAction(e ->{
			System.out.println("Edit Name Pressed");
			editEmployee.setVisible(true);
			editEmployee.setText("Enter the employee's new name and press enter");
			
			editEmployee.setOnAction( ee ->{
				Employees.get(currentID).setName(editEmployee.getText());
				editEmployee.setVisible(false);
				employeeList = sbEmployees.toString();
				System.out.println("Employee name changed");
			});
		});
		
	/*-------------------------Set handler for edit salary button-------------------------*/
		editSalaryBut.setOnAction(e ->{
			System.out.println("Edit Salary Pressed");
			editEmployee.setVisible(true);
			editEmployee.setText("Enter the employee's new salary and press enter");
			
			editEmployee.setOnAction( ee ->{
				Employees.get(currentID).setSalary(Double.parseDouble(editEmployee.getText()));
				editEmployee.setVisible(false);
				employeeList = sbEmployees.toString();
				System.out.println("Employee salary changed");
			});
		});
		
	/*------------------------Set handler for fire employee button------------------------*/
		fireBut.setOnAction(e ->{
			System.out.println("Fire Pressed");
			
			oldEmployees.add(Employees.get(currentPos));
			Employees.remove(currentPos);
			
			System.out.println("Employee fired");
		});
		
	/*------------------------Set handler for OK button-----------------------------------*/
		OKBut.setOnAction(e ->{
			System.out.println("OK Button Pressed");
			buildGui2();
		});
		
		st.setTitle("Payroll Program");
		st.show();
		
	}
	
	
/*---------------------Login Screen------------------------------------*/
	public void buildGui(){
		loginField = new TextField("Username");
		loginField.setFont(fn);
		passwordField = new TextField("Password");
		passwordField.setFont(fn);
		
		HBox pane1 = new HBox();
        VBox pane2 = new VBox();
        pane2.setMinWidth(100);
        pane2.setMaxHeight(50);
        pane2.setAlignment(Pos.CENTER);
        pane2.getChildren().addAll( loginField );
        pane1.getChildren().addAll( pane2, loginBut, quitBut );
        pane1.setMinHeight(75);
        pane1.setMinWidth(250);
        pane1.setAlignment(Pos.CENTER);
        
		Scene sn = new Scene( pane1 );
		st.setScene(sn);
	}
	
/*-------------------Boss Screen------------------------------------------*/
	public void buildGui2(){
		// Rebuild the list of employees in case any were edited
		sbEmployees.setLength(0);
		buildString();

		employeeList = sbEmployees.toString();
		employeeArea = new TextArea();
		employeeArea.setText(employeeList);
		employeeArea.setEditable(false);
		
		createBut.setMinWidth(120);
		editBut.setMinWidth(120);
		payrollBut.setMinWidth(120);
		quitBut.setMinWidth(120);
		
		HBox pane1 = new HBox();
        VBox pane2 = new VBox(8);
        pane2.setMinWidth(150);
        pane2.setAlignment(Pos.CENTER);
        pane2.getChildren().addAll( createBut, editBut, payrollBut, quitBut );
        pane1.getChildren().addAll( employeeArea, pane2 );
        
        Scene sn = new Scene( pane1, 500, 400 );
        st.setScene(sn);
	}

/*-------------------Create New Employees---------------------------------*/
	public void buildGui3(){
		nameField = new TextField("Employee Name");
		nameField.setFont(fn);
		usernameField = new TextField("Login Name");
		usernameField.setFont(fn);
		salaryField = new TextField("Salary");
		salaryField.setFont(fn);
		
		// Add hourly and salaried radio buttons to a ToggleGroup
		hourlyBut.setToggleGroup(payType);
		salariedBut.setToggleGroup(payType);
		
		HBox pane1 = new HBox(15);
		VBox pane3 = new VBox(5);
		VBox pane2 = new VBox(10);
		pane2.setAlignment(Pos.CENTER);
		pane3.setAlignment(Pos.CENTER);
		pane2.getChildren().addAll( nameField, usernameField, salaryField );
		pane3.getChildren().addAll( hourlyBut, salariedBut );
		pane1.getChildren().addAll( pane2, pane3 );
		pane1.setPadding(new Insets(20));
		
		Scene sn = new Scene( pane1, 270, 140 );
		st.setScene(sn);
	}
	
/*--------------------Edit/Fire Employees----------------------------------*/
	public void buildGui4(){
		Button backBut = new Button("Back");
		editEmployee = new TextField();
		editEmployee.setVisible(false);
		
		// Prompt a user for an employee to edit
		editField = new TextField("Type an employee's name and press enter");
		editField.setFont(fn);
		
		prompt = new Text("What would you like to do with this employee?");
		OKBut.setText("Done");
		
		VBox pane1 = new VBox(5);
		VBox pane2 = new VBox(5);
		FlowPane pane3 = new FlowPane();
		
		pane1.setAlignment(Pos.CENTER);
		pane2.setAlignment(Pos.CENTER);
		pane3.setAlignment(Pos.CENTER);
		pane1.getChildren().addAll( editField, OKBut );
		pane3.getChildren().addAll( editNameBut, editSalaryBut, fireBut, backBut );
		pane2.getChildren().addAll( prompt, editEmployee, pane3 );
		Scene sn = new Scene( pane1, 270, 100 );
		Scene sn2 = new Scene( pane2, 400, 300);
		
		st.setScene(sn);
		
		// Determine which employee was entered
		editField.setOnAction(e ->{
			for (int k = 0; k < Employees.size(); ++k){
				if (Employees.get(k).getName().trim().equals(editField.getText().trim())){
					currentID = Employees.get(k).getID();
					System.out.println("Employee Recognized, ID = " + currentID);
					
				// Determine which position the employee is in the ArrayList
					for (int p = 0; p < Employees.size(); ++p){
						if (Employees.get(p).getID() == currentID){
							currentPos = p;
						}
					}
				}
			}
			st.setScene(sn2);
		});
		
		// Allow the user to return to the previous screen
		backBut.setOnAction( e ->{
			editField.setText("Type an employee's name and press enter");
			st.setScene(sn);
		});
		
		
		
	}
	
/*--------------------Display Payroll Data---------------------------------*/
	public void buildGui5(){
		// Display list of all employees who were paid
		paidList = payEmployees.toString();
		payrollArea = new TextArea("Payroll Data:\n\n");
		payrollArea.setEditable(false);
		payrollArea.setText(paidList);
		
		OKBut.setMinWidth(120);
		
		// Build GUI
		HBox pane1 = new HBox();
        VBox pane2 = new VBox(8);
        pane2.setMinWidth(150);
        pane2.setAlignment(Pos.CENTER);
        pane2.getChildren().addAll( OKBut );
        pane1.getChildren().addAll( payrollArea, pane2 );
        
        Scene sn2 = new Scene( pane1, 500, 400 );
        st.setScene(sn2);
	}

/*-------------------non-Boss data viewing and quit------------------------*/
	public void buildGui6(){
		// Determine who logged in
		currentUser = loginField.getText();
		fireBut.setText("Quit Company");
		
		TextArea displayArea = new TextArea("");
		
		// Create an image to display and music to play on this screen
		Image picture = new Image("picture.jpg");
		ImageView iv1 = new ImageView( picture );
		
		//-------------------------------------------------
	    //URL res2 = getClass().getResource("music.mp3");		
	    //AudioClip graze = new AudioClip(res2.toString());		// NullPointerException on this line. music.mp3 is in the folder though
		//--------------------------------------------------
		
	    // Display the current user's data
		for (int k = 0; k < Employees.size(); ++k){
			if (Employees.get(k).getLoginName().equals(currentUser)){
				displayArea.setText("\t\t\t\t\t\t" + Employees.get(k).toString());
			}
		}
		
		// Build the scene, display the picture, and play the music
		displayArea.setEditable(false);
		displayArea.setMaxHeight(50);
		VBox pane1 = new VBox(10);
		pane1.setAlignment(Pos.CENTER);
		pane1.getChildren().addAll( iv1, header, displayArea, quitBut, fireBut );
		Scene sn = new Scene ( pane1, 600, 450 );
		st.setScene(sn);
		//graze.play();
	}
	
/*-------------------Create a new employee object using data from the interface---------------*/	
	public void newEmployee(){
		String login;
		String salaryS;
		double salary;
		String name;
		int ID;
		Date now = new Date();
		
		if (Employees.isEmpty()){
		// Boss's ID is zero
			ID = 0;				
		}
		else {
		// Other IDs must be the number after the previous employee (including ones who quit or were fired)
			ID = Employees.get(0).nextID((Employees.size() - 1) + oldEmployees.size());
		}
		
			name = nameField.getText();												
			salaryS = salaryField.getText();
			login = usernameField.getText();
			salary = Double.parseDouble(salaryS);
		
		// Determine whether employee is hourly or salaried, create appropriate employee
		if (salaried){
			Employee em = new Salaried( login, salary, name, now, ID );
			Employees.add(em);
			sbEmployees.append(em);
		}
		else if (hourly){
			Employee em = new Hourly( login, salary, name, now, ID );
			Employees.add(em);
			sbEmployees.append(em);
		}
		
		hourly = false;
		salaried = false;
			
		System.out.println("Employee Added.");
		writeFile(); 	// Write the file again with the new employee added
	}

/*------------------Read the employee file and create employee objects from data---------------------*/
	public void readFile(){
		String login;
		double salary;
		String name;
		int ID;
		Date now;
		String type;
		
			try{										// Surround by try block to catch FileNotFound Exception
				EF = new File("EmployeeFile.txt");
				Scanner sc = new Scanner (EF);			// Try: Open employee file and a scanner to read it
				
				while (sc.hasNext()){					// Read all data one line at a time using normal loop and hasNext()
					type = sc.next();
					ID = sc.nextInt();
					login = sc.next();					// Create series of Employee Objects
					salary = sc.nextDouble();
					now = new Date();
					name = sc.nextLine().trim();
					
					// Determine whether employee is Hourly or Salaried
					
					if (type.equals("Hourly")){
						Employees.add(new Hourly(login, salary, name, now, ID));	
					}
					else if (type.equals("Salaried")){
						Employees.add(new Salaried(login, salary, name, now, ID));
					}
					else{
						System.out.println("Try something else");
					}
				}
				
				sc.close();								// Close scanner
			}
	
			catch (FileNotFoundException e) {			// Create file if it doesn't exist
				EF = new File("EmployeeFile.txt");
				System.out.println("File Not Found. New File Created.");
				}
			
			System.out.println("File Read");
	}

/*------------------Write the ArrayList of employees to a text file----------------------------------*/
	public void writeFile(){
		PrintWriter pw;

			try{
				pw = new PrintWriter("EmployeeFile.txt");					// Reopen input file for output
					for (int k = 0; k < Employees.size(); ++k){				// Write each object in the collection to the file
						pw.append(Employees.get(k).typeTest() + " " + Employees.get(k).toString());
					}
	
				pw.close(); 	// Close new database file
			}
			
			catch( FileNotFoundException ex ){
				ex.printStackTrace();
			}
			
		System.out.println("Employee File written");	
	}
	
/*------------------Build the StringBuilder of all employees currently in the ArrayList--------------*/	
	public void buildString(){
		sbEmployees.append( "Employees:\n\n" );

		for (Employee e : Employees){			
			sbEmployees.append(e.toString());
		}
	}
	
/*-----------------Pay each employee-----------------------------------------------------------------*/
	public void doPayroll(Employee e){		
		// Prompt for hours worked if the employee is hourly
		if (count < Employees.size()){
			if (e.isHourly()){
				System.out.println("Employee is hourly.");
				prompt.setText("How many hours did "+ e.getName() + " work this period?");
				
				hourField = new TextField();
				hourField.setMaxWidth(50);
				
		// Build a separate scene for the prompt
				VBox pane3 = new VBox(5);
				pane3.getChildren().addAll( prompt, hourField );
				pane3.setAlignment(Pos.CENTER);
				Scene sn = new Scene( pane3, 400, 200);
				st.setScene(sn);
				
		// Pay the employee when the user presses enter
				hourField.setOnAction( a -> {
						hours = Integer.parseInt(hourField.getText());
						amountPaid = e.getPay( hours );
						hourField.setText("");
						payEmployees.append( e.payString() + "\n" );
						System.out.println("Hourly Employee " + e.getName() + " paid " + amountPaid);
						++count;
						doPayroll(e);
				});
			}
			
		// If the employee is salaried, divide his/her salary by 24
			else{
				amountPaid = e.getPay(24);
				payEmployees.append( e.payString() + "\n" );
				System.out.println( "Salaried employee " + e.getName() + " paid " + amountPaid );
				++count;
			}
		}
		else { buildGui5(); }
	}
	
/*-----------------Prompt for employee's hours worked if they are hourly--------------------------*/
	class getHours implements EventHandler<ActionEvent> {
		public void handle( ActionEvent a ){
		
		if (count < Employees.size()){
			hours = Integer.parseInt(hourField.getText());
			++count;
			hourField.setText("");
			System.out.println("Employee Paid");
		}
		else{ buildGui5(); }	// Build the payroll GUI once all employees have been paid
		}	
	}
}