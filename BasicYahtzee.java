import java.util.Random;


public class BasicYahtzee
{

  public static void main(String args[])
  {
    Random rand = new Random();
    int[] rolls = new int[5];


    System.out.println("Hello There, Rolling 5 Dice");
    System.out.println("You rolled a:");

    for(int i =0; i < rolls.length; i++)
    {
      rolls[i]= rand.nextInt(5)+1;
      System.out.println(rolls[i]);
    }


  }


}
