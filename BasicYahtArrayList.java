import java.util.Random; // roll dice
import java.util.Scanner; // user input
import java.util.Collections; // use collections methods such as frequency
import java.util.ArrayList; // for arraylist 

public class BasicYahtArrayList
{

  public static void main(String args[])
  {
    Random rand = new Random(); // random for roll
    ArrayList<Integer> rolls = new ArrayList<Integer>(5); // consider making it 2d to mark wheter player set dice aside for reroll
    int[] scoreBoard = new int[13]; // tracks scores for each category
    Scanner cat = new Scanner(System.in); // input/scanner obj

    int score=0; // tracks overall score
    int reroll = 0; // tracks rerolls available
    int yesNo; // tracks user input related to reroll (if they want to reroll)

    for (int i =0; i < 5; i++)
        rolls.add(0); // intialize arraylist

    System.out.println("Hello There, Rolling 5 Dice");
    System.out.println("You rolled a:");

    for (int j = 0; j>-1; j++) // main loop that runs infinitely (supposed to terminate when scoreboard is full/ all elements not 0)
    {
      reroll=0; // rest reroll
      do{ // roll loop that rolls 5 random dice, puts it in rolls, sorts rolls, and prints out roll. Also asks user for reroll
      for(int i =0; i < 5; i++)
       rolls.set(i,rand.nextInt(6)+1);

      Collections.sort(rolls); // sort in between print out for user readibility

      for(int i =0; i < rolls.size(); i++)
        System.out.print("\t"+rolls.get(i));  

      System.out.println("\nREROLL? 1 for yes, 0 for no");
      yesNo = cat.nextInt();
      if (yesNo == 0) // break if user doesn't want to reroll
      break;

        reroll++; // allow three rerolls
      }while(reroll < 3); // END OF DO WHILE LOOP
       
      
      printList(); // prints options/categories for user to score their roll
      
      int choice = cat.nextInt(); // get user input for category

      while(choice < 1 || choice > 13 || scoreBoard[choice-1]>0 ) //  while choice is taken or not a valid choice, alert user and prompt for input
      {
        System.out.println("Option already taken or invalid");
        choice = cat.nextInt();
      }

      scoreBoard[choice-1] = categories(rolls, choice); // assign scoreboard category to score of dice based on result of category method
      score += scoreBoard[choice-1]; // assign total score to itself + new points from category

      System.out.println("current score is:" + score); // print our current score and score for that specific roll
      System.out.println("Score for category " +choice+ " is " + scoreBoard[choice-1]);
    }

    cat.close(); // close scanner
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
        int threeKind2 = Collections.frequency(rolls, rolls.get(4));
        if(choice==7)
        {
            if (threeKind > 2 || threeKind2 > 2)
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
    score+=30;
    if (choice == 11) // if 11 look for large straight, 5 in a row. Score is 40
    score += 40;

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

  public static void printList()
  {
    System.out.println("Select where you would like to score:");
    System.out.println("Aces (Ones) | Total of Aces only: Enter 1");
    System.out.println("Twos | Total of twos only: Enter 2");
    System.out.println("Threes | Total of Thress only: Enter 3");
    System.out.println("Fours | Total of Fours only: Enter 4");
    System.out.println("Fives | Total of Fives only: Enter 5");
    System.out.println("Sixes | Total of Sixes only:Enter 6");

    System.out.println("\n lower section: \n");

    System.out.println("3 of a Kind | Total of all 5 dice: Enter 7");
    System.out.println("4 of a Kind | Total of all 5 dice: Enter 8");
    System.out.println("Full House | 25 points: Enter 9");
    System.out.println("Small Straight | 30 points: Enter 10");
    System.out.println("Large Straight | 40 points: Enter 11");
    System.out.println("YAHTZEE! | 50 points: Enter 12");
    System.out.println("Chance | Total of all 5 dice: Enter 13");
  }

}
