//import java.util.stream.Collectors;
import java.util.Collections; // use collections methods such as frequency
import java.util.ArrayList; // for arraylist 
//import java.util.List;

public class Multiplayer
{
    private ArrayList<Integer> rolls = new ArrayList<Integer>(5);
    private int[] scoreBoard = new int[13];
    private int score;
    

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
        scoreBoard[idex]=val;
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

    public void setScore(int add)
    {
        score+=add;
    }

    public int getRollSize()
    {
        return rolls.size();
    }
        

}