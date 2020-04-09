// John Moeder
//  GUI Libraries
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos; 
import javafx.geometry.Insets;
import javafx.scene.text.Font;

//import javafx.event.*;
//import javax.swing.JFrame;  

//Graphics Libraries 
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.image.*;


public class guiWhy extends Application // Start of Class
{
  
   private logic playerobj = new logic(); // holds each player obj, including their scoreboard, and overall score
   private int setButtonArr; // used to setOnAction for button array categories

    // gui vars
    private Stage window; // main windows that gui runs ins
    private Scene scene1, scene2; // scene 1 is main menu and scene 2 will be main board screen
    private Button [] categories = new Button [13]; // buttons to that score roll in a category

    private Button diceButtons [] = new Button[5]; // dice images for roll values and reserve toggle
    private Image diceNullImg = new Image("resources/diceNull.png"); // default image for dice
    
    private Label numberPlayers = new Label("Number of players: " + playerobj.getPlayers()); // label that contain's the amount of players playing
    private Label rollVal = new Label("You rolled a: "); // value of the roll
    private Label curPlayer = new Label("Player "+(playerobj.getCurPlayer()+1)+"'s Turn"); // who the current player is
    private Label numRolls = new Label("Rolls Left: "+ playerobj.getreroll()); // number of rolls available per turn
    private Label availableChoice = new Label(""); // tells player if option is already taken
    
    private TextField input = new TextField("# of players?"); // field for the number of players playing
    private String [] options = new String[] {"Total of Aces: 1's","Total of Twos: 2's","Total of Threes: 3's", "Total of Fours: 4's",
    "Total of Fives: 5's", "Total of Sixes: 6's", "3 of A Kind | Total of All 5 Dice", "4 of A Kind | Total of All 5 Dice", "Full House | 25 points","Small Straight | 30 points", "Large Straight | 40 points", 
    "YAHTZEE! | 50 points", "Chance | Total of All 5 Dice"}; // list of player's scoring options for dice

    public static void main(String[] args) { // main, runs start function
        launch(args);
    }

    @Override // required for start, which is pulled from application class
    public void start(Stage primaryStage) {
        window = primaryStage; // set window to primary stage

        ///////////////////////////////////////////////// START OF LAYOUT 1

        
      Canvas canvas = new Canvas(350,200 ); // canvas upon gc graphics title will be drawn

      GraphicsContext gc = canvas.getGraphicsContext2D();
         
     gc.setFill( Color.RED );
     gc.setStroke( Color.BLACK );
     gc.setLineWidth(2);
     Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
     gc.setFont( theFont );
     gc.fillText( "Yahtzee!", 90, 35 );
     gc.strokeText( "Yahtzee",90, 35 );
     Image dice = new Image( "resources/dice.png" );
     gc.drawImage( dice, 130, 100 );      // END OF GRAPHICS DRAW


    for (int i =0; i<5; i++) // set up dice images
    {
      int repetitive =i; // avoid final issue with i on setOnAction reserveDice call
     diceButtons[i] = new Button("",new ImageView(diceNullImg)); //set up intial dice images (change dice to array so, this could be 1 for loop)
     diceButtons[i].setStyle("-fx-background-color: transparent;"); // set buttons to transparent so only the image is shown
     diceButtons[i].setOnAction(e->{ reserveDice(repetitive);}); 
    }
     
        //Button 1
        Label label1 = new Label("Yahtzee!"); // set up welcome label
        label1.setFont(Font.font("Arial Black",25));

        Button startGame = new Button("Click me to play"); // button that when clicked, sets player (# of players) to textfield input and switches scenes
        Button instruct = new Button("Instructions"); // FINISH?
        startGame.setOnAction(e->{ setPlayerGui(input);}); // sets up intial player values
        input.setMaxWidth(310); // set textfeild width where user enters number of players
        input.setOnAction(e->{ setPlayerGui(input);});

        //Layout 1 - elements in vertical column for main menu/start screen
        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.BASELINE_CENTER);
        layout1.getChildren().addAll(label1, canvas, input, startGame, instruct);
        scene1 = new Scene(layout1, 350, 500);

        ///////////////////////////////////////////////////////////////////////////////////// START OF LAYOUT 2

        //Button 2
        Button Menu = new Button("Back to the Main Menu");
        Menu.setOnAction(e -> {
          window.setScene(scene1); playerobj.saveGame();}); // change scene to main menu and saveGame func

        Button button3 = new Button("Next Player/Turn");
        button3.setOnAction(e -> {playerobj.setCurrentPlayer(); curLabel();}); // increment turn to next player or next roll if 1 player

        HBox topBorder = new HBox(); // HBox and Border Top
        topBorder.setPadding(new Insets(10,0,0,0));
        topBorder.setSpacing(10);
        curPlayer.setFont(Font.font("Arial Black",14));
        HBox.setMargin(curPlayer,new Insets(5,0,0,0));
        topBorder.getChildren().addAll(Menu,button3,curPlayer);

        HBox centerBorder = new HBox();
        centerBorder.getChildren().addAll(diceButtons[0],diceButtons[1],diceButtons[2],diceButtons[3],diceButtons[4]);

        // Button Roll
        Button roll = new Button("Roll!"); // button to roll dice 
        roll.setFont(Font.font("Arial Black",14));
        roll.setOnAction(e->{rollSet();}); // roll 5 di, calls rollLogic function

        HBox bottomBorder = new HBox();
        bottomBorder.setSpacing(10);
        bottomBorder.setPadding(new Insets(10,10,10,10));
        bottomBorder.getChildren().addAll(numberPlayers, roll, rollVal, numRolls, availableChoice);

        //LAYOUT 2 grid and border
        GridPane eastBorder = new GridPane(); // scene 2 element put into a FlowPane
        eastBorder.setVgap(20);
        eastBorder.setPadding(new Insets(10,0,-40,10));

        BorderPane layout2 = new BorderPane(); // scene 2 main gui layout

        for (setButtonArr=0; setButtonArr<13; setButtonArr++ ) // set button text to correct numbered option.
        {
          int i = setButtonArr; // set i here because of final int issue if declared in for loop
          categories[setButtonArr] = new Button(options[i]);
          categories[setButtonArr].setOnAction(e->scoreRoll(i+1)); // Score roll based on corresponding number
          GridPane.setConstraints(categories[setButtonArr], 0,i); // add button at i to the layout
          categories[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // set button to a max size so they are all the same width
          categories[i].setFont(Font.font("Arial Black",14)); // set font style
          eastBorder.getChildren().add(categories[i]); // add each button to the eastborder vbox
        }
        layout2.setRight(eastBorder);
        layout2.setTop(topBorder);
        layout2.setBottom(bottomBorder);
        layout2.setCenter(centerBorder);
        scene2 = new Scene(layout2, 850, 770); // set scene to new scene

        //Display scene 1 at first
        scene1.getStylesheets().add("style.css");
        window.setScene(scene1);
        window.setTitle("Yahtzee!");
        window.show();
    }

    public void setPlayerGui(TextField input)
    {    
      playerobj = new logic();
      curPlayer.setText("Player "+(playerobj.getCurPlayer()+1)+"'s Turn");
      if(playerobj.setPlayer(input))
      {
        numberPlayers.setText("Number of players: "+playerobj.getPlayers());
        window.setScene(scene2);
      }
      else return;
    }

    public void curLabel()
    {
       curPlayer.setText("Player "+(playerobj.getCurPlayer()+1)+"'s Turn");
       System.out.print(playerobj.getCurPlayer());
       numRolls.setText("Rolls Left: "+ playerobj.getreroll());
       rollVal.setText("You rolled a: ");
    }

    public void reserveDice(int idex)
    {
      if(rollVal.getText()!="You rolled a: ")
      {
        playerobj.toggleDice(idex);

        for(int i =0; i<5; i++)
        {          
          if (playerobj.getDiceToggle(i)==0)
          diceButtons[i].setGraphic(new ImageView(new Image("resources/dice"+(playerobj.getMulti()[playerobj.getCurPlayer()].getRoll(i))+".png")));
          else
          diceButtons[i].setGraphic(new ImageView(new Image("resources/dice"+(playerobj.getMulti()[playerobj.getCurPlayer()].getRoll(i))+"dark.png")));
        }
      
      }
    }

    public void rollSet()
    {
      if(playerobj.getreroll()!=0)
      rollVal.setText("You rolled a: "); // reset roll label

      int test = playerobj.YahtzeeRollLogic();
      
      if(test==0)
      {
        numRolls.setText("OUT OF ROLLS!");
        return;
      }
      if(test==-1)
      {
        playerobj.setCurrentPlayer(); 
        curLabel();
        return;
      }

      //if(playerobj.getreroll()!=0)
      numRolls.setText("Rolls Left: "+ playerobj.getreroll());

      for(int i = 0; i < 5; i++) // set roll label to sorted roll
        rollVal.setText(rollVal.getText()+ " " +playerobj.getMulti()[playerobj.getCurPlayer()].getRoll(i));

        for(int i =0; i<5; i++)
        {          
          if (playerobj.getDiceToggle(i)==0)
          diceButtons[i].setGraphic(new ImageView(new Image("resources/dice"+(playerobj.getMulti()[playerobj.getCurPlayer()].getRoll(i))+".png")));
          else
          diceButtons[i].setGraphic(new ImageView(new Image("resources/dice"+(playerobj.getMulti()[playerobj.getCurPlayer()].getRoll(i))+"dark.png")));
        }
    }

    public void scoreRoll(int choice)
    {
           if(rollVal.getText()=="You rolled a: ")
       {
        System.out.println("You need to roll first!");
        return;
       }

       if(playerobj.yahtzeeScoreRoll(choice))
       {
       rollVal.setText("You rolled a: ");
       numRolls.setText("Rolls Left: "+ playerobj.getreroll());
       curPlayer.setText("Player "+(playerobj.getCurPlayer()+1)+"'s Turn");
       
       for(int i =0; i<5; i++)
       playerobj.setDiceToggle(i,0);

       for(int i =0; i<5; i++)
        diceButtons[i].setGraphic(new ImageView(diceNullImg)); // reset dice images to dicenull/?
      
        availableChoice.setText("");// SET LABEL TO ""

       }
       else
       availableChoice.setText("Option Already Taken!");// SET LABEL TO OPTION TAKEN!
    }

  }