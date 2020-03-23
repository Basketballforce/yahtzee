import java.util.Random;
import java.util.Scanner;

public class BasicYahtzee
{

  public static void main(String args[])
  {
    Random rand = new Random();
    int[] rolls = new int[5];


    System.out.println("Hello There, Rolling 5 Dice");
    System.out.println("You rolled a:");

    for (int j = 0; j>-1; j++)
    {
      for(int i =0; i < rolls.length; i++)
      {
       rolls[i]= rand.nextInt(5)+1;
       System.out.println(rolls[i]);
      }
      printList();
      int score;
      score = categories(rolls);

      System.out.println("current score is:" + score);

      return;
    }


  }


  public static int categories(int[] rolls)
  {
  
    Scanner cat = new Scanner(System.in);
    int choice = cat.nextInt();
    cat.close();
    int score = 0;

    for(int i =0; i< rolls.length; i++)
    {
      if(rolls[i]== choice)
      score+=choice;
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

    System.out.println("3 of a Kind | Total of all 5 dice: Enter 3k");
    System.out.println("4 of a Kind | Total of all 5 dice: Enter 4k");
    System.out.println("Full House | 25 points: Enter fh");
    System.out.println("Small Straight | 30 points: Enter ss");
    System.out.println("Large Straight | 40 points: Enter ls");
    System.out.println("YAHTZEE! | 50 points: Enter y");
    System.out.println("Chance | Total of all 5 dice: Enter c");
  }

}
