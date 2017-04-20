package asdf;

import java.util.Hashtable;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TowersOfHanoi extends Application {
	static int ans;
	Rectangle selectedpiece;
	VBox selectedbox;
	Paint temp;
	Boolean isVictory = true;
	Hashtable<Rectangle, Integer> values = new Hashtable<Rectangle, Integer>();
	//Victory/defeat
	Text t = new Text("");
	Object[] pieces;
	VBox panel3;
	
	
	public static void main(String[] args){
		ans = Integer.parseInt(JOptionPane.showInputDialog("Enter number of Discs: "));
		System.out.println(ans);
		launch();
	}
	@Override
	public void start(Stage mainStage) throws Exception {
		GridPane gp = new GridPane();
		
		//Autosolve button
		Button button = new Button("Autosolve");
		button.setOnMouseClicked(new autosolveHandler());
		
		//Adding 3 panels
		VBox panel1 = new VBox();
		panel1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		panel1.setAlignment(Pos.BOTTOM_CENTER);
		panel1.setPrefHeight(980);
		panel1.setPrefWidth(500);
		panel1.setSpacing(1);
		panel1.setPadding(new Insets(0, 20, 10, 20));
		panel1.setOnMouseClicked(new panelEventHandler());
		
		VBox panel2 = new VBox();
		panel2.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		panel2.setAlignment(Pos.BOTTOM_CENTER);
		panel2.setPrefHeight(500);
		panel2.setPrefWidth(500);
		panel2.setSpacing(1);
		panel2.setPadding(new Insets(0, 20, 10, 20));
		panel2.setOnMouseClicked(new panelEventHandler());
		
		panel3 = new VBox();
		panel3.setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
		panel3.setAlignment(Pos.BOTTOM_CENTER);
		panel3.setPrefHeight(500);
		panel3.setPrefWidth(500);
		panel3.setSpacing(1);
		panel3.setPadding(new Insets(0, 20, 10, 20));
		panel3.setOnMouseClicked(new panelEventHandler());
		
		//Adding all 4 pieces
		Rectangle piece1 = new Rectangle(250, 50);
		piece1.setFill(Color.RED);
		piece1.setStroke(Color.BLACK);
		panel1.getChildren().add(piece1);
		piece1.setOnMouseClicked(new pieceEventHandler());
			
		Rectangle piece2 = new Rectangle(300, 50);
		piece2.setFill(Color.BLUE);
		piece2.setStroke(Color.BLACK);
		panel1.getChildren().add(piece2);
		piece2.setOnMouseClicked(new pieceEventHandler());
		
		Rectangle piece3 = new Rectangle(350, 50);
		piece3.setFill(Color.CORAL);
		piece3.setStroke(Color.BLACK);
		panel1.getChildren().add(piece3);
		piece3.setOnMouseClicked(new pieceEventHandler());
		
		Rectangle piece4 = new Rectangle(400, 50);
		piece4.setFill(Color.MAGENTA);
		piece4.setStroke(Color.BLACK);
		panel1.getChildren().add(piece4);
		piece4.setOnMouseClicked(new pieceEventHandler());
		
		//Adding piece values to hashtable
		values.put(piece1, 1);
		values.put(piece2, 2);
		values.put(piece3, 3);
		values.put(piece4, 4);
		
		//Adjusting elements in window
		gp.getChildren().add(panel1);
		gp.getColumnConstraints().add(new ColumnConstraints(500));
		GridPane.setColumnIndex(panel1, 0);	
		gp.getChildren().add(panel2);
		GridPane.setColumnIndex(panel2, 5);
		gp.getChildren().add(panel3);
		GridPane.setColumnIndex(panel3, 10);
		gp.getChildren().add(button);
		button.setAlignment(Pos.TOP_LEFT);
		GridPane.setColumnIndex(button, 0);
		GridPane.setRowIndex(button, 5);
		gp.getChildren().add(t);
		t.setFont(Font.font("Verdana", 25));
		GridPane.setColumnIndex(t, 5);
		
		pieces = new Object[]{piece1, piece2, piece3, piece4};
		
		
		
		Scene scene = new Scene(gp, 1500, 980);
		mainStage.setScene(scene);
		mainStage.show();
	}

	
	
	class panelEventHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {
			
			if(selectedpiece == null){
				;
			}
			else{
				selectedbox = (VBox)event.getSource();
				if(selectedbox.getChildren().contains(selectedpiece)){
					;
				}
				else{
					VBox parentbox = (VBox) selectedpiece.getParent();
					Object[] selectedboxlist = selectedbox.getChildren().toArray();
					Object[] parentlist = parentbox.getChildren().toArray();
					
					if(selectedpiece.equals(parentlist[0])){
						
						if(selectedboxlist.length > 0){
							for(Object n : parentlist){
								if(values.get(selectedpiece) < values.get(n)){
									isVictory = false;
									t.setText("Defeat!");
									break;
								}
							}	
							
							if(selectedbox.equals(panel3)&& selectedboxlist.length == 4 && isVictory == true){
								t.setText("Victory!");
								
							}
						}
						
						parentbox.getChildren().remove(selectedpiece);
						selectedbox.getChildren().add(0, selectedpiece);
						selectedpiece.setFill(temp);
						
						
						selectedpiece = null;
						selectedbox = null;
					}
					else{
						selectedpiece.setFill(temp);
						selectedpiece = null;
						selectedbox = null;
					}
					
					
				}
			}
		}
		
	}
	
	class pieceEventHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {
			if(selectedpiece == null){
			selectedpiece = (Rectangle) event.getSource();
			temp = selectedpiece.getFill();
			selectedpiece.setFill(Color.YELLOW);
			}
			else if(selectedpiece == (Rectangle) event.getSource()){
				selectedpiece.setFill(temp);
				selectedpiece = null;
			}
			else{
				selectedpiece.setFill(temp);
				selectedpiece = (Rectangle) event.getSource();
				temp = selectedpiece.getFill();
				selectedpiece.setFill(Color.YELLOW);
			}
		}
	
	}
	
	class autosolveHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {
			
			System.out.println("Memes");
			
			
			
			
			
		}
		
	}
	
	
}
