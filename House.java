/* Program 7 - Nicholas Ghobrial */

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;   // VBox and HBox
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class House extends Application {
	/* Declare all class variables and initialize them */
	Stage st;
	
	Rectangle house 	= new Rectangle();
	Rectangle door 		= new Rectangle();
	Rectangle window 	= new Rectangle();
	Rectangle frontLawn = new Rectangle();
	Polygon   roof 		= new Polygon();
	
	Text prompt = new Text();
	
	Color plumPurple  = new Color(.7, 0, 1, 1);
	Color transparent = new Color (0, 0, 0, 0);
	
	Pane housePane;
	VBox fullPane;
	
	double xClick, yClick;
	double bottomRightX, bottomRightY;
	
	boolean houseCreate = true;
	boolean roofCreate = false;
	
	public void start ( Stage st ){
		this.st = st;
		
		buildGui();
		
		st.setTitle("House");
		st.show();
	}
	
	public void buildGui(){
		Background bkGreen = new Background(
	            new BackgroundFill(Color.LIGHTGREEN, null, null)); 	// Background representing yard
		
		prompt.setText(" Please choose a color for the house:\n" +  // Prompts for color selection
						" P = Plum Purple\n O = Orange");
		housePane = new Pane();
		fullPane = new VBox();
		
		housePane.setBackground(bkGreen);
		
		fullPane.getChildren().addAll( prompt );
		housePane.getChildren().addAll( house, frontLawn, roof, window, door, fullPane);
		
		Scene sn = new Scene( housePane, 700, 600 );
		sn.setOnKeyReleased( new KeyTyped() );	
		
		st.setScene(sn);
	}
	
	/* ------------------Handler for color selection------------------ */
	class KeyTyped implements EventHandler<KeyEvent> {
		public void handle( KeyEvent k ){
			System.out.println("Key Typed: " + k.getCode());
			
			// Sets house to selected color and roof to the opposite
			if (k.getCode().toString().equals("P")) {
				house.setFill(plumPurple);
				roof.setFill(Color.ORANGE);
				prompt.setText(" Click for the top left of the house\n and drag to the lower left");
				housePane.setOnMousePressed( new MousePressed() );
			}
			else if (k.getCode().toString().equals("O")){
				house.setFill(Color.ORANGE);
				roof.setFill(plumPurple);
				prompt.setText(" Click for the top left of the house\n and drag to the lower left");
				housePane.setOnMousePressed( new MousePressed() );
			}
		}
	}
	
	/* ------------------Handler for mouse drag------------------ */
	class MouseDragged implements EventHandler<MouseEvent> {
	    public void handle( MouseEvent e ){
	        System.out.println("Mouse dragged");
	        
	        // Sets house dimensions to match mouse drag
	        bottomRightX = e.getX() - xClick;
	        bottomRightY = e.getY() - yClick;
	        house.setHeight(e.getY() - yClick);
	        house.setWidth(e.getX() - xClick);
	        housePane.setOnMouseReleased( new MouseReleased() );
	    }
	}
	
	/* ------------------Handler for mouse press------------------*/
	class MousePressed implements EventHandler<MouseEvent> {
        public void handle( MouseEvent e ){
        	
        	// Creates house when prompted
        	if (houseCreate){
        		xClick = e.getX();
        		yClick = e.getY();
        		house.setX(e.getX());
        		house.setY(e.getY());
        		housePane.setOnMouseDragged( new MouseDragged() );
        		houseCreate = false;
        		
        		System.out.println("House Created");
        	}
        	
        	// Creates roof when prompted
        	else if (roofCreate){
        		roof.getPoints().addAll(new Double[]{xClick, yClick, e.getX(), e.getY(), (house.getX() + house.getWidth()), house.getY()});
        		
        		housePane.setOnMouseReleased( null ); // Prevents further clicks from creating roof
        		housePane.setOnMousePressed( null );
        		
        		house.setOnMousePressed( new WindowCreate() );
        		prompt.setText(" Click in the house for a window");
        		roofCreate = false;
        		
        		System.out.println("Roof Created");
        	}
        }
    }
	
	/* ------------------Handler for mouse release------------------*/
	class MouseReleased implements EventHandler<MouseEvent> {
		public void handle( MouseEvent e){
			System.out.println("Mouse released");
			
			// Creates front lawn matching width of house
			frontLawn.setFill(Color.LIGHTGREEN);
			frontLawn.setX(house.getX());
			frontLawn.setY(house.getY() + house.getHeight());
			frontLawn.setWidth(house.getWidth());
			frontLawn.setHeight(600 - house.getX() - house.getHeight());
			
			housePane.setOnMouseDragged( null ); // Stops listening for dragging
			prompt.setText(" Click above the house for peak of roof");
			roofCreate = true;	// Ready to create roof
		}
	}
	
	/* ------------------Handler for mouse press on the house------------------*/
	class WindowCreate implements EventHandler<MouseEvent> {
		public void handle( MouseEvent e){
			// Creates window
			window.setFill(Color.BLUE);
			window.setX(e.getX());
			window.setY(e.getY());
			window.setHeight(45);
			window.setWidth(45);
			
			// Prevents user from creating a window off the house
			if ((window.getX() + 45) > (house.getX() + house.getWidth()) || (window.getY() + 45) > (house.getY() + house.getHeight())){
				prompt.setText("Can't place window, try again!");
				window.setFill(transparent);
				return;
			}
			
			house.setOnMousePressed( null ); // Stops listening for mouse presses
			prompt.setText(" Click underneath the house for front door");
			frontLawn.setOnMousePressed( new DoorCreate() );
			
			System.out.println("Window Created");
		}
	}
	
	/* ------------------Handler for mouse press on lawn------------------*/
	class DoorCreate implements EventHandler<MouseEvent> {
		public void handle( MouseEvent e){
			door.setFill(Color.BLUE);
			door.setX(e.getX());
			door.setY(yClick + (house.getHeight()/2));
			door.setHeight(house.getHeight()/2);
			door.setWidth(50);
			
			frontLawn.setOnMousePressed( null ); // Stops listening for mouse presses
			
			System.out.println("Door Created");
			prompt.setText(" Your house was created!");
		}
	}
}
