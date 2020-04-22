
// GAME LOGIC Libraries
import java.util.Random; // roll dice
import java.util.stream.Collectors; // to use on list in categories function to score certain choices

import javafx.scene.control.TextField;

import java.util.Collections; // use collections methods such as frequency
import java.util.ArrayList; // for arraylist 
import java.util.List;  // for list

public class logic {

    //Game Logic Vars
private int players; // number of players
private int currentPlayer = 0; // increment by one every main loop, tracks current player
private Random rand = new Random(); // random for roll values
private int reroll = 3; // tracks rerolls available
Multiplayer[] playerobj; // holds each player obj, including their scoreboard, and overall score
int[] toggleDice = new int[] {0,0,0,0,0}; // track if dice is being reserved or not

public void setup(int p) // setup inital values
{
    System.out.print("Function started with given parameter "+ players + " "); // command line
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
            // Checks that passed in int is greater than 0s and less than max numbers of players which is 20 cause why not
        if(Integer.parseInt(input.getText())<1 || Integer.parseInt(input.getText())>20)
        {
          input.setText("Invalid value! Number to small or large!");
          return false;
        }

        System.out.println(players); // command line
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

      System.out.println("toggling dice" + i + "val:" + toggleDice[i]); // CMD Line
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
          System.out.print("GAME OVER");//CMD LINE
          return -2; // change to bool or kill statement
         }
 
         if(fullScorecard(playerobj[currentPlayer])) // if a player's scorecard is full then return -1
         {
           System.out.println("Player is out of categories to score!");//CMD LINE
           return -1; 
         }

         if (reroll==0) // if player is out of rolls return 0
         {
         System.out.println("Player is out of rolls!");
         return 0;
         }
        
       System.out.println("\nPLAYER: "+ (currentPlayer+1) + "'s TURN"); // CMD LINE
       reroll--; // reset reroll
      
       
       int rolled; // track single roll value

        // roll loop that rolls 5 random dice, puts it in rolls, sorts rolls, and prints out roll. Also asks user for reroll
       for(int i =0; i < 5; i++)
       {
         if(toggleDice[i]==0)
         {
          rolled = rand.nextInt(6)+1;
          playerobj[currentPlayer].setRoll(i, rolled);
         }
       }
        
//CMD LINE
       for(int i =0; i < playerobj[currentPlayer].getRollSize(); i++) // print out roll
         System.out.print("\t"+ playerobj[currentPlayer].getRoll(i));  //CMD LINE
 
       System.out.println("\nREROLL?"); // prompt for reroll, command Line
       return 1;
//CMD LINE
       }

       
       public boolean yahtzeeScoreRoll(int choice) // internal score logic
       {
        System.out.print(choice); //CMD LINE

        if(choice==12) //  if choice is yahtzee, special case
       {
        if(playerobj[currentPlayer].getScoreboard(choice -1)==0 || (playerobj[currentPlayer].getScoreboard(choice -1)>0 && categories(playerobj[currentPlayer].getRollList(), choice)==0))
        { // if invalid yahztee choice then deny score 
          System.out.println("Option already taken or invalid" ); // + option picked by player AKA choice, CMD LINE
          return false;
        }
       } // if category other than yahtzee has already been selected, deny score
        else if(playerobj[currentPlayer].getScoreboard(choice -1)>-1 && choice !=12) //  while choice is taken alert user and prompt for input
       {
         System.out.println("Option already taken or invalid" ); // + option picked by player AKA choice, COMMAND LINE
         return false;
       }
       
       // set scoreboard and score for current player
       playerobj[currentPlayer].setScoreboard(choice-1,categories(playerobj[currentPlayer].getRollList(), choice)); // assign scoreboard category to score of dice based on result of category method
       playerobj[currentPlayer].setScore();

       if(getBonus()) // if player has reached the lower section bonus score 
       playerobj[currentPlayer].bonus(); // add 35 to total score

       //CMD Line
       System.out.println("current score is:" + playerobj[currentPlayer].getScore()); // print our current score and score for that specific roll
       System.out.println("Score for category " +choice+ " is " + playerobj[currentPlayer].getScoreboard(choice -1));
       //CMD Line

       setCurrentPlayer(); // increment currentplayer/next player
       return true;
     }
 
   
 
   public int categories(ArrayList<Integer> rolls, int choice) // method to determine score of that roll in relation to category chosen by user
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

   public int getSboard(int i) // get value of a scoreboard category
   {
     return playerobj[currentPlayer].getScoreboard(i);
   }

   
   public int getTotalScore(int i) // get a players score
   {
     return playerobj[i].getScore();
   }

   public Multiplayer[] getMulti() // get multiplayer obj.. used in gui setup of scene 2 to set dice reserved/not reserved image
   {
       return playerobj;
   }

   public boolean getBonus() // add bonus if reached by player
   {
     int bonus=0;
     for(int i=0;i<6;i++)
     bonus+=playerobj[currentPlayer].getScoreboard(i);

     if (bonus>62)
     return true;
     else 
     return false;
   }

   public int winner() // Determine which player wins, includes tie and loss for 1p
   {
     int winner=0; // Tracks what player wins or if its a tie. In 1p games tracks if one player beat 220
     int tie=0; // track tie
     int bestScore = playerobj[0].getScore(); // tracks best score
     
     if(playerobj.length>1) // if more than one player
     {
      for (int i=0; i<playerobj.length-1; i++)
      {
        if(bestScore<playerobj[i+1].getScore())
        {
          winner=i+1;
          bestScore=playerobj[i+1].getScore();
        }
      }

      for(int i=0; i<playerobj.length; i++) // check if game ended in a tie
      {
        if(bestScore==playerobj[i].getScore())
        tie++;
      }

      if(tie>1) // if more than one player had the best score than it is a tie
      return -3;

      return winner;
    }
    else // only one player
    winner=playerobj[0].getScore();
    if(winner<220) // if winner did NOT score better than predetermined score to beat
    return -2;
    else
    return -1; // if they did score more

   }
}
