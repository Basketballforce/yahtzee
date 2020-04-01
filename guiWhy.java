import javafx.application.Application;
//import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos; 

//import javax.swing.JFrame;
import java.util.Random; // roll dice
//import java.util.Scanner; // user input
import java.util.stream.Collectors;

import javax.swing.SwingConstants;

import java.util.Collections; // use collections methods such as frequency
import java.util.ArrayList; // for arraylist 
import java.util.List;  // for list


public class guiWhy extends Application
{

    //Game Logic Vars
    private int players=1; // number of players
    private int currentPlayer = 0; // increment by one every main loop,  helps track current player
    private Random rand = new Random(); // random for roll
    private int reroll = 3; // tracks rerolls available
    private int setButtonArr; // used to setOnAction for button array categories
    Multiplayer[] playerobj; // holds each player obj, including their scoreboard, and overall score
    ArrayList<Integer> rolls; // holds roll values

    // gui vars
    Stage window; // main windows that gui runs ins
    Scene scene1, scene2; // scene 1 is main menu and scene 2 will be main board screen
    Button button2; // click to start playing
    Button [] categories = new Button [13];
    Button roll = new Button("Roll!"); // button to roll dice 
    Label numberPlayers = new Label("Number of players: " + players); // label that contain's the amount of players playing
    Label rollVal = new Label("You rolled a: "); // value of the roll
    Label curPlayer = new Label("Player "+(currentPlayer+1)+"'s Turn"); // who the current player is
    Label numRolls = new Label("Rolls Left: "+ reroll);
    TextField input = new TextField("# of players?"); // field for the number of players playing
    public static void main(String[] args) { // main, runs start function
        launch(args);
    }

    @Override // required for start, which is pulled from application class
    public void start(Stage primaryStage) {
        window = primaryStage; // set window to primary stage

        //Button 1
        Label label1 = new Label("Welcome to the Main Menu!"); // set up welcome label
       // Button button1 = new Button("Go to scene 2"); // no longer needed
       // button1.setOnAction(e -> window.setScene(scene2)); 

        button2 = new Button("Click me to play"); // button that when clicked, sets player (# of players) to textfield input and switches scenes
        button2.setOnAction(e->setPlayer(input)); 

        //Layout 1 - elements in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, input, button2);
        scene1 = new Scene(layout1, 350, 300);


        //Button 2
        Button button2 = new Button("Go back to the Main Menu");
        button2.setOnAction(e -> window.setScene(scene1));

        Button button3 = new Button("Increment current player by 1");
        button3.setOnAction(e -> setCurrentPlayer());

        // Button Roll
        roll.setOnAction(e->YahtzeeRollLogic());

        //Layout 2
        FlowPane layout2 = new FlowPane(20,20);
        layout2.getChildren().addAll(button2,button3, numberPlayers, roll, rollVal,curPlayer, numRolls);
        for (setButtonArr=0; setButtonArr<13; setButtonArr++ )
        {
          int i = setButtonArr; // set i here
          categories[setButtonArr] = new Button("Option " + (setButtonArr+1));
          categories[setButtonArr].setOnAction(e->yahtzeeScoreRoll(i+1));
          layout2.getChildren().add(categories[setButtonArr]);
        }
        layout2.setAlignment(Pos.CENTER);
        scene2 = new Scene(layout2, 450, 400);

        //Display scene 1 at first
        window.setScene(scene1);
        window.setTitle("Yahtzee!");
        window.show();
    }

    public void setPlayer(TextField input)
    {         // Try-catch that catches if entered value is not a valid integer
        try            
        {
            players = Integer.parseInt(input.getText());    
        } catch (NumberFormatException e) 
        {
            input.setText("Invalid value! Please enter a positive number");
            return;
        }
            // Checks that passed in int is greater than 0s
        if(Integer.parseInt(input.getText())<1)
        return;

        System.out.println(players);
        YahtzeeSetupLogic();
        numberPlayers.setText("Number of players: "+players);
        window.setScene(scene2);
        return;
    }

    public int getPlayer()
    {
        return players;
    }

    public void setCurrentPlayer()
    {
        currentPlayer=(currentPlayer+1)%players;
        curPlayer.setText("Player "+(currentPlayer+1)+"'s Turn");
        System.out.print(currentPlayer);
        reroll=3;
        numRolls.setText("Rolls Left: "+ reroll);
        rollVal.setText("You rolled a: ");
    }

    
   public void YahtzeeSetupLogic()
   {
     System.out.print("Function started with given parameter "+ players + " ");
     playerobj = new Multiplayer[players];
 
     for(int i =0; i<players; i++)
     playerobj[i] = new Multiplayer(); // intialize player array
   }
 
     // USED TO BE INFINITE LOOP
   public void YahtzeeRollLogic()
   {
        if(reroll!=0)
        rollVal.setText("You rolled a: "); // reset roll label

         if(gameOver(playerobj))
         {
          System.out.print("GAME OVER");
          return; // change to bool or kill statement
         }
 
         if(fullScorecard(playerobj[currentPlayer]))
         {
           System.out.println("Player is out of categories to score!");
           currentPlayer++;
           return; 
         }

         if (reroll==0)
         {
         System.out.println("Player is out of rolls!");
         return;
         }
        
       System.out.println("\nPLAYER: "+ (currentPlayer+1) + "'s TURN");
       reroll--; // rest reroll
       if(reroll!=0)
       numRolls.setText("Rolls Left: "+ reroll);
       else
       numRolls.setText("OUT OF ROLLS!");
       
       int rolled; // track single roll value
        // roll loop that rolls 5 random dice, puts it in rolls, sorts rolls, and prints out roll. Also asks user for reroll
       for(int i =0; i < 5; i++)
       {
        rolled = rand.nextInt(6)+1;
        playerobj[currentPlayer].setRoll(i, rolled);
       }
 
        playerobj[currentPlayer].sortRoll(); // sort in between print out for user readibility
        for(int i = 0; i < 5; i++) // set roll label to sorted roll
        rollVal.setText(rollVal.getText()+ " " +playerobj[currentPlayer].getRoll(i));
 
       for(int i =0; i < playerobj[currentPlayer].getRollSize(); i++) // print out roll
         System.out.print("\t"+ playerobj[currentPlayer].getRoll(i));  
 
       System.out.println("\nREROLL?");
       return;
       }

       
       public void yahtzeeScoreRoll(int choice)
       {
        System.out.print(choice);
        if(playerobj[currentPlayer].getScoreboard(choice -1)>-1 ) //  while choice is taken alert user and prompt for input
       {
         System.out.println("Option already taken or invalid" ); // + option picked by player AKA choice
         //choice = cat.nextInt();
         return;
       }
       if(rollVal.getText()=="You rolled a: ")
       {
        System.out.println("You need to roll first!");
        return;
       }
 
       playerobj[currentPlayer].setScoreboard(choice-1,categories(playerobj[currentPlayer].getRollList(), choice)); // assign scoreboard category to score of dice based on result of category method
       playerobj[currentPlayer].setScore(playerobj[currentPlayer].getScoreboard(choice-1)); // assign total score to itself + new points from category
 
       System.out.println("current score is:" + playerobj[currentPlayer].getScore()); // print our current score and score for that specific roll
       System.out.println("Score for category " +choice+ " is " + playerobj[currentPlayer].getScoreboard(choice -1));
       
       setCurrentPlayer();
     }
 
   
 
   public static int categories(ArrayList<Integer> rolls, int choice) // method to determine score of that roll in relation category chosen by user
   {
     int score = 0;
    
     if (choice < 7) // if less than 7, then score is the sum of dice that value was chosen by user (choice)
     {
         for(int i =0; i< rolls.size(); i++)
         {
         if(rolls.get(i)== choice)
         score+=choice;
         }
 
         return score;
     }
     
     if (choice == 7 || choice == 8) // if 7 or 8 check frequency for three of a kind or four of a kind. If valid score is sum off all dice, otherwise 0
     {
         int threeKind = Collections.frequency(rolls, rolls.get(0));
         int threeKindMid = Collections.frequency(rolls, rolls.get(2));
         int threeKind2 = Collections.frequency(rolls, rolls.get(4));
         if(choice==7)
         {
             if (threeKind > 2 || threeKind2 > 2 || threeKindMid > 2)
             {
                 for(int total : rolls)
                     score+=total;
             }
         }
         else if (choice == 8)
         {
             if (threeKind > 3 || threeKind2 > 3)
             {
                 for(int total : rolls)
                     score+=total;
             }
         }
         
             return score;
             
     }
 
     if (choice == 9) // if 9, check for full house. Score is 25
     {
         int fullHouse = Collections.frequency(rolls, rolls.get(0));
         int fullHouse2 = Collections.frequency(rolls, rolls.get(4));
 
          if  (  (fullHouse > 1 & fullHouse2 > 2) || (fullHouse > 2 && fullHouse2 > 1)  )
             score+=25;
 
         return score;
     }
 
     if (choice == 10) // if 10 look for small straight, 4 in a row. Score is 30
     {
       List<Integer> Straights = rolls.stream().distinct().collect(Collectors.toList());
       if(Straights.size()<4) // convert rolls into distinct list with uniques integers
       return score; // if size is less than 4, than can't be a small straight
 
       boolean straight=true;
 
         for(int i=0; i<3; i++)
         {
          if (Straights.get(i)!=Straights.get(i+1)-1)
          straight=false;
         }
          if(straight==true)
          return 30;
       
       //otherwise size is 5
       if(Straights.size()==5)
       {
         for(int i=1; i<Straights.size()-1; i++)
         {
          if (Straights.get(i)!=Straights.get(i+1)-1)
          straight=false;
         }
         if(straight==true)
         return 30;
 
         for(int i=0; i<Straights.size()-2; i++)
         { 
          if (Straights.get(i)!=Straights.get(i+1)-1)
          straight=false;
        }
         if(straight==true)
         return 30;
       }
       return score;
     }
     if (choice == 11) // if 11 look for large straight, 5 in a row. Score is 40
     {
       boolean straight=true;
       for(int i=0; i<rolls.size()-1; i++)
       {
         if (rolls.get(i)!=rolls.get(i+1)-1)
         straight=false;
       }
       if(straight==true)
       score += 40;
 
       return score;
     }
 
     if (choice == 12) // if 12 look for five of a kind. Score is 50, YAHTZEE
     {
         int fiveKind = Collections.frequency(rolls, rolls.get(0));
         if (fiveKind > 4)
           score+=50;
 
         return score;
     }
 
     if (choice == 13) // if 13 chance, score is sum of dice
     {
         for(int total : rolls)
             score+=total;
 
             return score;
     }
 
     return score;
   }
 
   public static boolean fullScorecard (Multiplayer scoreBoard) // checks scoreboard to see if it is full
   {
       boolean full = true;
       for (int i=0; i<13; i++)
       {
         if(scoreBoard.getScoreboard(i) <0)
         full = false;
       }
 
     return full;
   }
 
   public static boolean gameOver (Multiplayer[] scoreBoard) // uses fullScorecard to check if every player's scorecard is full
   {
     boolean full = true;
       for (int i=0; i<scoreBoard.length; i++)
       {
         if(!fullScorecard(scoreBoard[i]))
         full = false;
       }
 
     return full;
   }
}