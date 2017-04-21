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
	Rectangle temporary;
	Rectangle selectedpiece;
	VBox selectedbox;
	Paint temp;
	Boolean isVictory = true;
	Hashtable<Rectangle, Integer> values = new Hashtable<Rectangle, Integer>();
	//Victory/defeat
	Text t = new Text("");
	Object[] pieces;
	
	VBox panel1;
	VBox panel2;
	VBox panel3;
	
	Object[] list1;
	Object[] list2;
	Object[] list3;
	
	public static void main(String[] args){
		ans = Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Discs: "));
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
		panel1 = new VBox();
		panel1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		panel1.setAlignment(Pos.BOTTOM_CENTER);
		panel1.setPrefHeight(980);
		panel1.setPrefWidth(500);
		panel1.setSpacing(1);
		panel1.setPadding(new Insets(5, 5, 5, 5));
		panel1.setOnMouseClicked(new panelEventHandler());
		
		panel2 = new VBox();
		panel2.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		panel2.setAlignment(Pos.BOTTOM_CENTER);
		panel2.setPrefHeight(500);
		panel2.setPrefWidth(500);
		panel2.setSpacing(1);
		panel2.setPadding(new Insets(5, 5, 5, 5));
		panel2.setOnMouseClicked(new panelEventHandler());
		
		panel3 = new VBox();
		panel3.setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
		panel3.setAlignment(Pos.BOTTOM_CENTER);
		panel3.setPrefHeight(500);
		panel3.setPrefWidth(500);
		panel3.setSpacing(1);
		panel3.setPadding(new Insets(5, 5, 5, 5));
		panel3.setOnMouseClicked(new panelEventHandler());
		
		int size = 100;
		pieces = new Object[ans];
		
		for(int i = 0; i < ans; i++){
			temporary = new Rectangle(size, 50);
			temporary.setFill(Color.color(Math.random(), Math.random(), Math.random()));
			temporary.setStroke(Color.BLACK);
			panel1.getChildren().add(temporary);
			temporary.setOnMouseClicked(new pieceEventHandler());
			size += 50;
			values.put(temporary, i);
			pieces[0] = temp;
		}

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
		
		//pieces = new Object[]{piece1, piece2, piece3, piece4};

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
					Object[] panel3list = panel3.getChildren().toArray();
					
					if(selectedpiece.equals(parentlist[0])){
						
						if(selectedboxlist.length > 0){
							for(int i = 0; i < selectedboxlist.length; i++){
								if(selectedpiece.getWidth() > ((Rectangle) selectedboxlist[i]).getWidth()){
									t.setText("Defeat!");
									isVictory = false;
									Object[] options = {"Retry", "Quit"};
							        int startingchoice = JOptionPane.showOptionDialog(null, "Defeat! Try again?", null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							        
							        if(startingchoice == JOptionPane.YES_OPTION){
							        	System.exit(0);
							        }
							        else if(startingchoice == JOptionPane.NO_OPTION){
							        	System.exit(0);
							        }
								}
							}
						}
						
						
						parentbox.getChildren().remove(selectedpiece);
						selectedbox.getChildren().add(0, selectedpiece);
						selectedpiece.setFill(temp);
						
						selectedpiece = null;
						selectedbox = null;
						
						if(panel1.getChildren().isEmpty() && panel2.getChildren().isEmpty()){
							t.setText("Victory!");
						}
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
	
			solve(panel1, panel2, panel3);
			
		}

		private void solve(VBox panel1, VBox panel2, VBox panel3) {
			
			
		}
	}
}
