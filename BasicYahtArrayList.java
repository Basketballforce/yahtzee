import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.ArrayList;

public class BasicYahtArrayList
{

  public static void main(String args[])
  {
    Random rand = new Random();
    ArrayList<Integer> rolls = new ArrayList<Integer>(5);
    int[] scoreBoard = new int[13];
    Scanner cat = new Scanner(System.in);
    int score=0;

    for (int i =0; i < 5; i++)
        rolls.add(0); // intialize arraylist

    System.out.println("Hello There, Rolling 5 Dice");
    System.out.println("You rolled a:");

    for (int j = 0; j>-1; j++)
    {
      for(int i =0; i < 5; i++)
       rolls.set(i,rand.nextInt(6)+1);

      Collections.sort(rolls); // sort in between print out for user readibility

      for(int i =0; i < rolls.size(); i++)
        System.out.print("\t"+rolls.get(i));
      
      
     // printList();
      
      int choice = cat.nextInt();

      while(choice < 1 || choice > 13 || scoreBoard[choice-1]>0 )
      {
        System.out.println("Option already taken or invalid");
        choice = cat.nextInt();
      }

      scoreBoard[choice-1] = categories(rolls, choice);
      score += scoreBoard[choice-1];

      System.out.println("current score is:" + score);
      System.out.println("Score for category " +(choice-1)+ " is " + scoreBoard[choice-1]);
    }

    cat.close();
  }


  public static int categories(ArrayList<Integer> rolls, int choice)
  {
    int score = 0;
   
    if (choice < 7)
    {
        for(int i =0; i< rolls.size(); i++)
        {
        if(rolls.get(i)== choice)
        score+=choice;
        }

        return score;
    }
    
    if (choice == 7 || choice == 8)
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

    if (choice == 9)
    {
        int fullHouse = Collections.frequency(rolls, rolls.get(0));
        int fullHouse2 = Collections.frequency(rolls, rolls.get(4));

         if  (  (fullHouse > 1 & fullHouse2 > 2) || (fullHouse > 2 && fullHouse2 > 1)  )
        {
            for(int total : rolls)
            score+=total;
        }
    }

    if (choice == 10)
    score+=30;
    if (choice == 11)
    score += 40;

    if (choice == 12)
    {
        int fiveKind = Collections.frequency(rolls, rolls.get(0));
        if (fiveKind > 4)
        {
            for(int total : rolls)
                score+=total;
        }
    }

    if (choice == 13)
    {
        for(int total : rolls)
            score+=total;
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
