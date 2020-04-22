import java.util.Collections; // use collections methods such as frequency
import java.util.ArrayList; // for arraylist 

public class Multiplayer
{
    private ArrayList<Integer> rolls = new ArrayList<Integer>(5); // hold roll values for players
    private int[] scoreBoard = new int[13]; // hold score for each category
    private int score; // hold total score
    

    public Multiplayer()
    {
        for (int i = 0; i<13; i++)
            scoreBoard[i]=-1;// intialize scoreboard

        for (int i =0; i < 5; i++)
            rolls.add(0); // intialize arraylist

        score = 0;
    }

    public void setRoll(int idex, int val)
    {
        rolls.set(idex, val);
    }

    public void sortRoll()
    {
        Collections.sort(rolls);
    }

    public int getRoll(int idex)
    {
        return rolls.get(idex);
    }

    public int getScoreboard(int idex)
    {
        return scoreBoard[idex];
    }

    public void setScoreboard(int idex, int val)
    {
        if(scoreBoard[idex]==-1) // for yahtzee case where setscore is called more than once for the category
        scoreBoard[idex]=0;

        scoreBoard[idex]+=val;
    }

    public int getBoardSize()
    {
        return scoreBoard.length;
    }

    public ArrayList<Integer> getRollList()
    {
        return rolls;
    }

    public int getScore()
    {
    return score;
    }

    public void setScore()
    {
        int currentScore=0;

        for(int i =0; i <13;i++)
        {
            if(getScoreboard(i)!=-1) // for yahtzee case where setscore is called more than once for the category    
            currentScore+=getScoreboard(i);
        }
        score=currentScore;
    }

    public void bonus()
    {
        score+=35;
    }

    public int getRollSize()
    {
        return rolls.size();
    }
        

}