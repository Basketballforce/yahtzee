
// GAME LOGIC Libraries
import java.util.Random; // roll dice
import java.util.stream.Collectors;

import javafx.scene.control.TextField;

import java.util.Collections; // use collections methods such as frequency
import java.util.ArrayList; // for arraylist 
import java.util.List;  // for list

// Libraries for writing and reading to file. Save and load game functionality
import java.io.File; // for saving and loading game
import java.io.FileWriter; // write files
import java.io.IOException;
//import java.util.Scanner; // reading from file

public class logic {

    //Game Logic Vars
private int players; // number of players
private int currentPlayer = 0; // increment by one every main loop,  helps track current player
private Random rand = new Random(); // random for roll
private int reroll = 3; // tracks rerolls available
Multiplayer[] playerobj; // holds each player obj, including their scoreboard, and overall score
int[] toggleDice = new int[] {0,0,0,0,0}; // track if dice is being reserved or not

public void setup(int p) // setup inital values
{
    System.out.print("Function started with given parameter "+ players + " ");
    players=p;
    playerobj = new Multiplayer[players];
    
    for(int i =0; i<players; i++)
    playerobj[i] = new Multiplayer(); // intialize player array
}


    public boolean setPlayer(TextField input)
    {         // Try-catch that catches if entered value is not a valid integer
        try            
        {
            players = Integer.parseInt(input.getText());    
        } catch (NumberFormatException e) 
        {
            input.setText("Invalid value! Please enter a positive number");
            return false;
        }
            // Checks that passed in int is greater than 0s
        if(Integer.parseInt(input.getText())<1 || Integer.parseInt(input.getText())>100)
        return false;

        System.out.println(players); 
        setup( Integer.parseInt(input.getText())); // CALLS SETUP HERE
        return true;
    }

    public int getPlayers() // returns number of players
    {
        return players;
    }

    public void setCurrentPlayer() // sets currentplayer value
    {
        currentPlayer=(currentPlayer+1)%players;

        reroll=3;
       
    }

    public void toggleDice(int i) // toggles dice on and off (whether they are rerolled or not)
    {
      if(toggleDice[i]==0)
      toggleDice[i]=1;
      else
      toggleDice[i]=0;

      System.out.println("toggling dice" + i + "val:" + toggleDice[i]);
    }

    public void setDiceToggle(int idex,int val) // set toggle value in array
    {
      toggleDice[idex]=val;
    }

    public int getDiceToggle(int idex) // get toggle value
    {
      return toggleDice[idex];
    }

 
   public int YahtzeeRollLogic() // internal roll logic
   {

         if(gameOver(playerobj)) // if the game is over return 0 and print game over... to be changed to results or winner screen in gui
         {
          System.out.print("GAME OVER");
          return 0; // change to bool or kill statement
         }
 
         if(fullScorecard(playerobj[currentPlayer])) // if a player's scorecard is full then return -1
         {
           System.out.println("Player is out of categories to score!");
           return -1; 
         }

         if (reroll==0) // if player is out of rolls return 0
         {
         System.out.println("Player is out of rolls!");
         return 0;
         }
        
       System.out.println("\nPLAYER: "+ (currentPlayer+1) + "'s TURN");
       reroll--; // reset reroll
      
       
       int rolled; // track single roll value

        // roll loop that rolls 5 random dice, puts it in rolls, sorts rolls, and prints out roll. Also asks user for reroll
       for(int i =0; i < 5; i++)
       {
         if(toggleDice[i]==0)
         {
          rolled = rand.nextInt(2)+1;
          playerobj[currentPlayer].setRoll(i, rolled);
         }
       }
        
//CMD LINE
       for(int i =0; i < playerobj[currentPlayer].getRollSize(); i++) // print out roll
         System.out.print("\t"+ playerobj[currentPlayer].getRoll(i));  
 
       System.out.println("\nREROLL?"); // prompt for reroll 
       return 1;
       //CMD LINE
       }

       
       public boolean yahtzeeScoreRoll(int choice) // internal score logic
       {
        System.out.print(choice);

        if(choice==12) //  if choice is yahtzee, special case
       {
        if(playerobj[currentPlayer].getScoreboard(choice -1)==0 || (playerobj[currentPlayer].getScoreboard(choice -1)>0 && categories(playerobj[currentPlayer].getRollList(), choice)==0))
        { // if invalid yahztee choice then deny score 
          System.out.println("Option already taken or invalid" ); // + option picked by player AKA choice
          return false;
        }
       } // if category other than yahtzee has already been selected, deny score
        else if(playerobj[currentPlayer].getScoreboard(choice -1)>-1 && choice !=12) //  while choice is taken alert user and prompt for input
       {
         System.out.println("Option already taken or invalid" ); // + option picked by player AKA choice
         return false;
       }
       
       // set scoreboard and score for current player
       playerobj[currentPlayer].setScoreboard(choice-1,categories(playerobj[currentPlayer].getRollList(), choice)); // assign scoreboard category to score of dice based on result of category method
       playerobj[currentPlayer].setScore();

       //CMD Line
       System.out.println("current score is:" + playerobj[currentPlayer].getScore()); // print our current score and score for that specific roll
       System.out.println("Score for category " +choice+ " is " + playerobj[currentPlayer].getScoreboard(choice -1));
       
       setCurrentPlayer();
       //CMD Line
       return true;
     }
 
   
 
   public int categories(ArrayList<Integer> rolls, int choice) // method to determine score of that roll in relation category chosen by user
   {
     int score = 0;
     playerobj[currentPlayer].sortRoll(); // sort in between print out for user readibility
    
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

         if(fiveKind > 4 && playerobj[currentPlayer].getScoreboard(11)>0) // another 50 for +100 when seconde yahtzee or more
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

   public int getCurPlayer() // get currentPlayer value
   {
       return currentPlayer;
   }

   public int getreroll() // get reroll value
   {
       return reroll;
   }

   public Multiplayer[] getMulti() // get multiplayer obj.. used in gui setup of scene 2
   {
       return playerobj;
   }

   public void saveGame() // MIGHT implement save option
   { // try block neccessarry due to ioexception  
    try            
    {
      FileWriter writeSave = new FileWriter("saveGame.txt");
      for(int i =0; i<13; i++)
      {
      writeSave.write("boo"); 
      }


      
      writeSave.close();   
    } catch (IOException e) 
    {
   //     input.setText("Issue with save file");
        return;
    }
    
   }

   public void loadGame() // MIGHT implement load from save option
   {
    File saveFile = new File("saveGame.txt");
   }
}
