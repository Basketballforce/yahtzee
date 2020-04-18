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
    private Scene scene1, scene2, scene3; // scene 1 is main menu and scene 2 will be main board screen
    private Button [] categories = new Button [13]; // buttons to that score roll in a category
    private Label [] sBoard = new Label [13];
    private Label sboardTitle = new Label(" SCOREBOARD ");
    private Label totalScore = new Label("  Score:  - ");

    private Button diceButtons [] = new Button[5]; // dice images for roll values and reserve toggle
    private Image diceNullImg = new Image("resources/diceNull.png"); // default image for dice
    private Image rules = new Image("resources/helpviewSmallEdited.png");

 //   private Label numberPlayers = new Label("     Number of players: " + playerobj.getPlayers()); // label that contain's the amount of players playing
    private Label rollVal = new Label("You rolled a: "); // value of the roll
    private Label curPlayer = new Label("Player "+(playerobj.getCurPlayer()+1)+"'s Turn"); // who the current player is
    private Label numRolls = new Label("Rolls Left: "+ playerobj.getreroll()); // number of rolls available per turn
    private Label availableChoice = new Label(""); // tells player if option is already taken
    private Label EndWinner = new Label(""); // tells player if option is already taken
    
    private TextField input = new TextField("# of players?"); // field for the number of players playing
    private TextArea textarea = new TextArea("Beginning the game is simple, type how many players will play in the box and click the 'click me to play button'. Once in game the player will roll the dice, if the player chooses they may select dice they wish to reserve and roll again or pick a category that will give them points. If the player changes their mind they may choose to unselect a die at any time and that die will roll when the user selects the roll button. The player gets three rolls and then must decide on the category that would best suit their current situation. When all players have all categories scorred the game is over and a winner will be determined.\n\n Player will receive a 35 point bonus at the end of the game if they scored more than 63 in the upper section (Total of 1 dice trough total of 6 dice)\n If you are playing as 1 player than your goal is to be a predetemined score of 220!");
    private String [] options = new String[] {"Total of Aces: 1's","Total of Twos: 2's","Total of Threes: 3's", "Total of Fours: 4's",
    "Total of Fives: 5's", "Total of Sixes: 6's", "3 of A Kind | Total of All 5 Dice", "4 of A Kind | Total of All 5 Dice", "Full House | 25 points","Small Straight | 30 points", "Large Straight | 40 points", 
    "YAHTZEE! | 50 points", "Chance | Total of All 5 Dice"}; // list of player's scoring options for dice

    private String [] scoreOptions = new String[] {" Aces: 1's "," Twos: 2's "," Threes: 3's ", " Fours: 4's ",
    " Fives: 5's ", " Sixes: 6's ", " 3 of A Kind ", " 4 of A Kind ", " Full House "," Small Straight ", " Large Straight ", 
    " YAHTZEE! ", " Chance "}; // list of player's scoring options for sBoard

    public static void main(String[] args) { // main, runs start function
        launch(args);
    }

    @Override // required for start, which is pulled from application class
    public void start(Stage primaryStage) {
        window = primaryStage; // set window to primary stage

        ///////////////////////////////////////////////// START OF LAYOUT 1

        
      Canvas canvas = new Canvas(350,75 ); // canvas upon gc graphics title will be drawn

      GraphicsContext gc = canvas.getGraphicsContext2D();
         
     gc.setFill( Color.RED );
     gc.setStroke( Color.BLACK );
     gc.setLineWidth(2);
     Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
     gc.setFont( theFont );
     gc.fillText( "Yahtzee!", 90, 35 );
     gc.strokeText( "Yahtzee!",90, 35 );
     //Image dice = new Image( "resources/dice.png" );
     //gc.drawImage( dice, 130, 100 );      // END OF GRAPHICS DRAW


    for (int i =0; i<5; i++) // set up dice images
    {
      int repetitive =i; // avoid final issue with i on setOnAction reserveDice call
     diceButtons[i] = new Button("",new ImageView(diceNullImg)); //set up intial dice images (change dice to array so, this could be 1 for loop)
     diceButtons[i].setStyle("-fx-background-color: transparent;"); // set buttons to transparent so only the image is shown
     diceButtons[i].setOnAction(e->{ reserveDice(repetitive);}); 
    }

        Image ximg = new Image("resources/x.png"); // default image for dice
        Button xbut=new Button("", new ImageView(ximg));
        xbut.setStyle("-fx-background-color: transparent;");
        Label olabel = new Label("= Reroll Dice"); // set up welcome label

        Image oimg = new Image("resources/o.png"); // default image for dice
        Button obut=new Button("", new ImageView(oimg));
        obut.setStyle("-fx-background-color: transparent;");
        Label xlabel = new Label("= Don't Reroll/Reserve Dice"); // set up welcome label
                
        xlabel.setPadding(new Insets(6,0,0,-15)); // set paddin on dice images
        olabel.setPadding(new Insets(6,0,0,-15));

     
        //Button 1
        Label label1 = new Label("Yahtzee!"); // set up welcome label
        label1.setFont(Font.font("Arial Black",25));
        label1.setVisible(false);

        Button startGame = new Button("Click me to play"); // button that when clicked, sets player (# of players) to textfield input and switches scenes
        Button instruct = new Button("Instructions"); // FINISH?
        startGame.setOnAction(e->{ setPlayerGui(input);}); // sets up intial player values
        input.setMaxWidth(310); // set textfeild width where user enters number of players
        input.setOnAction(e->{ setPlayerGui(input);});
        instruct.setOnAction(e->{instructbutton();});

        //Layout 1 - elements in vertical column for main menu/start screen
        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.BASELINE_CENTER);
        layout1.getChildren().addAll(label1, canvas, input, startGame, instruct);
        scene1 = new Scene(layout1, 350, 420);

        ///////////////////////////////////////////////////////////////////////////////////// START OF LAYOUT 2

        //Button 2
        Button Menu = new Button("Back to the Main Menu");
        Menu.setOnAction(e -> {
          window.setScene(scene1); playerobj.saveGame();}); // change scene to main menu and saveGame func

          Button InstructionsMenu = new Button("Back to the Main Menu");
          InstructionsMenu.setOnAction(e -> {
            window.setScene(scene1); playerobj.saveGame();});


        HBox topBorder = new HBox(); // HBox and Border Top, Contains main menu, current player, dice help/tip images
        topBorder.setPadding(new Insets(10,0,0,22));
        topBorder.setSpacing(10);
        curPlayer.setFont(Font.font("Arial Black",14)); // current player label
        HBox.setMargin(curPlayer,new Insets(5,0,0,0));
        topBorder.getChildren().addAll(Menu,curPlayer,obut,olabel,xbut,xlabel);  //add items to border top

           // Button Roll
           Button roll = new Button("Roll!"); // button to roll dice 
           roll.setFont(Font.font("Arial Black",20));
           roll.setTranslateY(-10);

           roll.setOnAction(e->{rollSet();}); // roll 5 di, calls rollLogic function

        HBox innercenterBorder1 = new HBox(); // Hbox for center row 1, contains row images with roll values
        HBox innercenterBorder2 = new HBox(30); // Hbox for center row 2, contains roll button, numeric roll values, number of rolls left and if availableChoice label
        HBox innercenterBorder3 = new HBox(); // center row 3, with minimal scoreboard for all players and label with winner

        innercenterBorder1.getChildren().addAll(diceButtons[0],diceButtons[1],diceButtons[2],diceButtons[3],diceButtons[4]);
        innercenterBorder2.getChildren().addAll(roll, rollVal, numRolls, availableChoice);
        innercenterBorder2.setPadding(new Insets(0, 0, 0, 12));
        innercenterBorder3.setAlignment(Pos.CENTER);
        innercenterBorder3.getChildren().addAll(EndWinner);

        VBox centerBorder = new VBox(40); // main vbox for center border that contains inner rowsS
        centerBorder.getChildren().addAll(innercenterBorder1,innercenterBorder2,innercenterBorder3);

        //LAYOUT 2 grid and border
        GridPane eastBorder = new GridPane(); // scene 2 element put into a FlowPane. Categories[]
        eastBorder.setVgap(20);
        eastBorder.setPadding(new Insets(10,0,-40,10));

        
        GridPane westBorder = new GridPane(); //sBoard, extended scoredboard for each player, shown on their turn
        westBorder.setVgap(25);
        westBorder.setPadding(new Insets(30,0,0,10));
        westBorder.setGridLinesVisible(true);

        sboardTitle.setFont(Font.font("Arial Black",14));
        totalScore.setFont(Font.font("Arial Black",14));
        westBorder.getChildren().add(sboardTitle);
    
      

        BorderPane layout2 = new BorderPane(); // scene 2 main gui layout

        for (setButtonArr=0; setButtonArr<13; setButtonArr++ ) // set button text to correct numbered option.
        {
          int i = setButtonArr; // set i here because of final int issue if declared in for loop

          categories[setButtonArr] = new Button(options[i]); // Set Buttons for scoring
          categories[setButtonArr].setOnAction(e->scoreRoll(i+1)); // Score roll based on corresponding number
          GridPane.setConstraints(categories[setButtonArr], 0,i); // add button at i to the layout
          categories[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // set button to a max size so they are all the same width
          categories[i].setFont(Font.font("Arial Black",14)); // set font style
          eastBorder.getChildren().add(categories[i]); // add each button to the eastborder vbox

          sBoard[setButtonArr] = new Label(scoreOptions[i]+" -  "); // set Label for sBoard
          GridPane.setConstraints(sBoard[setButtonArr], 0,i); // add button at i to the layout
          sBoard[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // set button to a max size so they are all the same width
          sBoard[i].setFont(Font.font("Verdana",14)); // set font style
          westBorder.add(sBoard[i],0,i+1); // add each button to the eastborder vbox // i + 1 to avoid aces and scoreboard on same cell
        }

        westBorder.add(totalScore,0,14); // add total score to end of scoredboard

        layout2.setRight(eastBorder); // set border values for main scene2 borderlayout
        layout2.setLeft(westBorder);
        layout2.setTop(topBorder);
        layout2.setCenter(centerBorder);

        BorderPane.setMargin(centerBorder, new Insets(50,0,0,0)); // insets for each border
        BorderPane.setMargin(westBorder, new Insets(0,12,0,12));
        BorderPane.setMargin(eastBorder, new Insets(0,20,0,0));

        VBox Layout3 = new VBox(); // layout 3, for Instructions page
       
        Layout3.setPadding(new Insets(15, 12, 15, 12));
        Layout3.setSpacing(10);

        textarea.setWrapText(true); // textbox for instructions
        textarea.setEditable(false);
        //textarea.setFont(Font.font("Arial",20));
  
        final ImageView selectedImage = new ImageView();  // image for instructions with roll examples
        selectedImage.setImage(rules);
        Layout3.setAlignment(Pos.CENTER);
        Layout3.getChildren().addAll(InstructionsMenu,textarea,selectedImage); // add menu button, textarea, and image to instructions scene
        
        scene2 = new Scene(layout2, 1030, 740); // set scene to new scene
        scene3 = new Scene(Layout3, 1030, 770);
        scene3.getStylesheets().add("resources/style_1.css"); // set style sheet for instructions

        //Display scene 1 at first
        scene1.getStylesheets().add("resources/style.css"); // style sheet for main menu
        window.setScene(scene1);
        window.setTitle("Yahtzee!");
        window.show();
    }

    //    START OF GUI LOGIC, DYNAMIC vars
    public void setPlayerGui(TextField input)
    {    
      playerobj = new logic();
      curPlayer.setText("Player "+(playerobj.getCurPlayer()+1)+"'s Turn");
      if(playerobj.setPlayer(input))
      {
        //numberPlayers.setText("     Number of players: "+playerobj.getPlayers());
        window.setScene(scene2); // switch scenes
        curLabel(); // reset labels
      }
      else return;
    }

    public void curLabel()
    {
       curPlayer.setText("Player "+(playerobj.getCurPlayer()+1)+"'s Turn");
       System.out.print(playerobj.getCurPlayer());
       numRolls.setText("Rolls Left: "+ playerobj.getreroll());
       rollVal.setText("You rolled a: ");

       for(int i =0; i<5; i++)
       diceButtons[i].setGraphic(new ImageView(diceNullImg)); // reset dice images to dicenull/?

        for(int i=0;i<13;i++) // set scoreboard
        {
        if(playerobj.getSboard(i)!=-1)
        sBoard[i].setText(scoreOptions[i]+": "+playerobj.getSboard(i)+" ");
        else
        sBoard[i].setText(scoreOptions[i]+" -  "); // if other players categories values are still default
        }

        if(playerobj.getTotalScore(playerobj.getCurPlayer())==0)
        totalScore.setText("  Score:  - ");
        else
        totalScore.setText("  Score: : "+playerobj.getTotalScore(playerobj.getCurPlayer()) );
        
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
      if(test==-1) // change players
      {
        playerobj.setCurrentPlayer(); 
        curLabel();
        return;
      }
      if(test==-2) //end game and display winner
      {
        int winner = playerobj.winner();

        if(winner>-1)
        EndWinner.setText("PLAYER "+(winner+1)+" WINS with "+playerobj.getTotalScore(winner)+" points!");

        if(winner==-1)
        EndWinner.setText("PLAYER 1 WINS, beating 220 with "+playerobj.getTotalScore(winner)+" points!");

        if(winner==-2)
        EndWinner.setText("PLAYER 1 Loses, unable to beat 220 with "+playerobj.getTotalScore(winner)+" points.");
        
        if(winner==-2)
        EndWinner.setText("It's a TIE!");
        
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

        for(int i=0;i<13;i++) // set scoreboard
        {
        if(playerobj.getSboard(i)!=-1)
        sBoard[i].setText(scoreOptions[i]+": "+playerobj.getSboard(i)+ " ");
        else
        sBoard[i].setText(scoreOptions[i]+" -  "); // if other players categories values are still default
        }

        if(playerobj.getTotalScore(playerobj.getCurPlayer())==0)
        totalScore.setText("  Score:  - ");
        else
        totalScore.setText("  Score: : "+playerobj.getTotalScore(playerobj.getCurPlayer()) );
      
        availableChoice.setText("");// SET LABEL TO ""

       }
       else
       availableChoice.setText("Option Already Taken!");// SET LABEL TO OPTION TAKEN!
    }

    private void instructbutton()
    {
      window.setScene(scene3);
    }

  }