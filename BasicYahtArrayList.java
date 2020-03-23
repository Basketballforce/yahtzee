import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.ArrayList;

public class BasicYahtArrayList
{

  public static void main(String args[])
  {
    Random rand = new Random();
    int[] rolls = new int[5];
    ArrayList<Integer> scoreBoard = new ArrayList<Integer>(13);
    Scanner cat = new Scanner(System.in);
    int score=0;

    for(int i =0; i < 13; i++)
    {
        scoreBoard.add(0);
    }

    System.out.println("Hello There, Rolling 5 Dice");
    System.out.println("You rolled a:");

    for (int j = 0; j>-1; j++)
    {
      for(int i =0; i < rolls.length; i++)
      {
       rolls[i]= rand.nextInt(6)+1;
       System.out.println(rolls[i]);
      }
      printList();

      int choice = cat.nextInt();

      while(choice < 1 || choice > 13 || scoreBoard.get(choice-1)==1 )
      {
        System.out.println("Option already taken or invalid");
        choice = cat.nextInt();
      }

      scoreBoard.set(choice-1,1);

      score += categories(rolls, choice);

      System.out.println("current score is:" + score);
    }

    cat.close();
  }


  public static int categories(int[] rolls, int choice)
  {
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

    System.out.println("3 of a Kind | Total of all 5 dice: Enter 7");
    System.out.println("4 of a Kind | Total of all 5 dice: Enter 8");
    System.out.println("Full House | 25 points: Enter 9");
    System.out.println("Small Straight | 30 points: Enter 10");
    System.out.println("Large Straight | 40 points: Enter 11");
    System.out.println("YAHTZEE! | 50 points: Enter 12");
    System.out.println("Chance | Total of all 5 dice: Enter 13");
  }

}
