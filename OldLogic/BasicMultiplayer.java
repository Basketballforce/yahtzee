import java.util.Random; // roll dice
import java.util.Scanner; // user input
import java.util.stream.Collectors;
import java.util.Collections; // use collections methods such as frequency
import java.util.ArrayList; // for arraylist 
import java.util.List;

public class BasicMultiplayer
{
  public static void main(String args[])
  {

    Scanner cat = new Scanner(System.in); // input/scanner obj
    System.out.println("Welcome to Yahtzee! How many players?"); // prompt user for player count

    int numPlayers = cat.nextInt(); // int equal to number of players
    int currentPlayer = 0; // increment by one every main loop,  helps track current player

    Multiplayer[] players = new Multiplayer[numPlayers];

    for(int i =0; i<numPlayers; i++)
    players[i] = new Multiplayer(); // intialize player array
    
    Random rand = new Random(); // random for roll

    int reroll = 0; // tracks rerolls available
    int yesNo; // tracks user input related to reroll (if they want to reroll)

    System.out.println("Hello There, Rolling 5 Dice");
    System.out.println("You rolled a:");

    for (int j = 0; j>-1; j++) // main loop that runs infinitely (supposed to terminate when scoreboard is full/ all elements not 0)
    {

        if(gameOver(players))
        {  
         cat.close(); // close scanner
         return;
        }

        currentPlayer = (currentPlayer % numPlayers); 


        if(fullScorecard(players[currentPlayer]))
        {
          System.out.println("Player is out of rolls!");
          currentPlayer++;
          continue;
        }
       
      System.out.println("\nPLAYER: "+ (currentPlayer+1) + "'s TURN");
      reroll=0; // rest reroll
      do{ // roll loop that rolls 5 random dice, puts it in rolls, sorts rolls, and prints out roll. Also asks user for reroll
      for(int i =0; i < 5; i++)
       players[currentPlayer].setRoll(i, rand.nextInt(6)+1);

       players[currentPlayer].sortRoll(); // sort in between print out for user readibility

      for(int i =0; i < players[currentPlayer].getRollSize(); i++) // print out roll
        System.out.print("\t"+ players[currentPlayer].getRoll(i));  

      System.out.println("\nREROLL? 1 for yes, 0 for no");
      yesNo = cat.nextInt();
      if (yesNo == 0) // break if user doesn't want to reroll
      break;

        reroll++; // allow three rerolls
      }while(reroll < 3); // END OF DO WHILE LOOP
       
      
      printList(players[currentPlayer]); // prints options/categories for user to score their roll
      
      int choice = cat.nextInt(); // get user input for category

      while(choice < 1 || choice > 13 || players[currentPlayer].getScoreboard(choice -1)>-1 ) //  while choice is taken or not a valid choice, alert user and prompt for input
      {
        System.out.println("Option already taken or invalid");
        choice = cat.nextInt();
      }

      players[currentPlayer].setScoreboard(choice-1,categories(players[currentPlayer].getRollList(), choice)); // assign scoreboard category to score of dice based on result of category method
      players[currentPlayer].setScore(players[currentPlayer].getScoreboard(choice-1)); // assign total score to itself + new points from category

      System.out.println("current score is:" + players[currentPlayer].getScore()); // print our current score and score for that specific roll
      System.out.println("Score for category " +choice+ " is " + players[currentPlayer].getScoreboard(choice -1));
      currentPlayer++;
    }
    cat.close(); // close scanner... NOT NECCESSARRY HERE, does this when in return statement above
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

  public static void printList(Multiplayer catScore) // prompts user to score in a category for dice roll and give them their current score for each one. -1 defualt/not scored yet
  {
    System.out.println("Select where you would like to score:");
    System.out.println("Aces (Ones) | Total of Aces only: Enter 1 \t" + catScore.getScoreboard(0));
    System.out.println("Twos | Total of twos only: Enter 2 \t" + catScore.getScoreboard(1));
    System.out.println("Threes | Total of Thress only: Enter 3 \t"+ catScore.getScoreboard(2));
    System.out.println("Fours | Total of Fours only: Enter 4 \t"+ catScore.getScoreboard(3));
    System.out.println("Fives | Total of Fives only: Enter 5 \t"+ catScore.getScoreboard(4));
    System.out.println("Sixes | Total of Sixes only:Enter 6 \t"+ catScore.getScoreboard(5));

    System.out.println("\n lower section: \n");

    System.out.println("3 of a Kind | Total of all 5 dice: Enter 7 \t"+ catScore.getScoreboard(6));
    System.out.println("4 of a Kind | Total of all 5 dice: Enter 8 \t"+ catScore.getScoreboard(7));
    System.out.println("Full House | 25 points: Enter 9 \t"+ catScore.getScoreboard(8));
    System.out.println("Small Straight | 30 points: Enter 10 \t"+ catScore.getScoreboard(9));
    System.out.println("Large Straight | 40 points: Enter 11 \t"+ catScore.getScoreboard(10));
    System.out.println("YAHTZEE! | 50 points: Enter 12 \t"+ catScore.getScoreboard(11));
    System.out.println("Chance | Total of all 5 dice: Enter 13 \t"+ catScore.getScoreboard(12));
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
