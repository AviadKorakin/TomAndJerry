package com.example.tomandjerry.GameUtilities;


import com.example.tomandjerry.R;
import com.example.tomandjerry.Utilities.MyScore;
import com.example.tomandjerry.Utilities.MySharedPreferences;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private final ArrayList<Integer> obstacles = new ArrayList<>();
    private final ArrayList<Bonus> bonuses=new ArrayList<>();
    private int lives ;
    private int maxLives;

    public int getMeters() {
        return meters;
    }
    private int meters=0;
    private int score = 0;
    private final int rows;
    private final int cols;
    private final int[][] gameMatrix;
    private final MainCharacter mainCharacter;

    public GameManager(int initialLives, int maxLives, int rows, int cols, MainCharacter mainCharacter) {
        if (initialLives > 0 && initialLives <= 4) {

            lives = initialLives;
            this.maxLives=maxLives;

        }
        if(rows <0 || cols<0) {
            throw new RuntimeException("Invalid arguments");
        }
        this.mainCharacter=mainCharacter;
        this.rows=rows;
        this.cols=cols;
        gameMatrix=new int[rows][cols];
        gameMatrix[rows-1][mainCharacter.getLocation()]= mainCharacter.getImage();
        setObstacles();
        setBonuses();


    }

    private void setObstacles()
    {
        obstacles.add(R.drawable.ic_tom);
        obstacles.add(R.drawable.ic_tomevil);
        obstacles.add(R.drawable.ic_tombomb);
        obstacles.add(R.drawable.ic_tomplan);
    }
    private void setBonuses()
    {
        bonuses.add(new Bonus(R.drawable.ic_jerrybounslife,-1));
        bonuses.add(new Bonus(R.drawable.ic_jerryloverpoint10,10));
        bonuses.add(new Bonus(R.drawable.ic_jerryloverpoint30,30));
    }
    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public int generateAObstacle()
    {
        return new Random().nextInt(obstacles.size());
    }
    public int generateABonus()
    {
        return new Random().nextInt(bonuses.size());
    }
    public int generateLocation()
    {
        return new Random().nextInt(cols);
    }
    public int toGenerate(int num)
    {
        return new Random().nextInt(num);
    }

    public int[][] UpdateGame()
    {
        //next step
        for(int row=rows-2;row>=1;row--)
        {
            System.arraycopy(gameMatrix[row - 1], 0, gameMatrix[row], 0, cols);
        }
        for(int col=0;col<cols;col++)
        {
            gameMatrix[0][col]=0;
        }
        if (toGenerate(6) == 0) {
            gameMatrix[0][generateLocation()] = bonuses.get(generateABonus()).getImage();
        } else {
            gameMatrix[0][generateLocation()] = obstacles.get(generateAObstacle());
        }
        meters++;
        return gameMatrix;
    }
    public int isHit()
    {
        if(obstacles.contains(gameMatrix[rows - 2][mainCharacter.getLocation()]))
        {
            lives--;
            return 0;
        }
        else {
            for (Bonus b : bonuses) {
                if (b.getImage()==gameMatrix[rows - 2][mainCharacter.getLocation()]) {
                    if(b.getScoreValue()==-1)
                    {
                        if(lives<maxLives) {
                            lives++;
                        }
                        return 1;
                    }
                    else {

                        score += b.getScoreValue();
                        return 2;
                    }
                }
            }
        }
        return 3;
    }
    public int[] mainCharacterMoveRight(boolean right)
    {
        int prev=mainCharacter.getLocation();
        int next=right ? mainCharacter.moveRight()  : mainCharacter.moveLeft();
        gameMatrix[rows-1][prev]= 0;
        gameMatrix[rows-1][next]= mainCharacter.getImage();

        return new int[]{prev, next};//send it to the ui for next generate.
    }

    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }
    public void restGame(int lives)
    {
        for(int row=0;row<rows-1;row++)
        {
            for(int col=0;col<cols;col++)
            {
                gameMatrix[row][col]=0;
            }
        }
        gameMatrix[rows-1][mainCharacter.getLocation()]= mainCharacter.getImage();
        score=0;
        this.lives=lives;

    }
@Deprecated
    public void updateScoreBoard() {
        if (score > 0) {
            MySharedPreferences mySharedPreferences = MySharedPreferences.getInstance();
            MyScore[] bundle = new MyScore[11];
            boolean flag = false;
            int index = 0;
            MyScore temp;
            for (int x = 0; x < 10; x++) {
                bundle[index] = new MyScore(mySharedPreferences.readString("SCORE" + x, "-1/0/0"));
                if (!flag && bundle[index].getScore() < score) {
                    temp = bundle[index];
                    // bundle[index++] = new MyScore(score, lat, lng);
                    bundle[index++] = temp;
                    flag = true;
                } else index++;
            }
            for (int x = 0; x < 10; x++) {
                mySharedPreferences.saveString("SCORE" + x, bundle[x].toString());
            }
        }
    }

        public void updateScoreBoard(double lat,double lng) {
            if(score>0) {
                MySharedPreferences mySharedPreferences = MySharedPreferences.getInstance();
                MyScore[] bundle = new MyScore[11];
                boolean flag = false;
                int index = 0;
                MyScore temp;
                for (int x = 0; x < 10; x++) {
                    bundle[index] = new MyScore(mySharedPreferences.readString("SCORE" + x, "-1/0/0"));
                    if (!flag && bundle[index].getScore() < score) {
                        temp = bundle[index];
                        bundle[index++] = new MyScore(score, lat, lng);
                        bundle[index++] = temp;
                        flag = true;
                    } else index++;
                }
                for (int x = 0; x < 10; x++) {
                    mySharedPreferences.saveString("SCORE" + x, bundle[x].toString());
                }
            }


    }
}
