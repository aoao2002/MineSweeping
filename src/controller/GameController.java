package controller;

import entity.GridStatus;
import minesweeper.AI;
import minesweeper.GamePanel;
import entity.Player;
import minesweeper.ScoreBoard;
import minesweeper.StartFrame;

import javax.swing.*;


public class GameController {

    private Player p1;
    private Player p2;
    private final Player p3=new Player();

    private Player onTurn;

    public int getTotalX() {
        return totalX;
    }

    public void setTotalX(int totalX) {
        this.totalX = totalX;
    }

    public int getTotalY() {
        return totalY;
    }

    public void setTotalY(int totalY) {
        this.totalY = totalY;
    }
    private int totalX;
    private int totalY;

    private GamePanel gamePanel;
    private ScoreBoard scoreBoard;
    private int steps=1;//玩家点击回合数
    private  int allSteps;//总共要求回合数
    private int mineCounter;//剩余雷数
    private int totalMine;//总共雷数


    public GameController(Player p1, Player p2,int steps) {
        this.init(p1, p2);
        this.onTurn = p1;
        this.allSteps=steps;
    }

    /**
     * 初始化游戏。在开始游戏前，应先调用此方法，给予游戏必要的参数。
     *
     * @param p1 玩家1
     * @param p2 玩家2
     */
    public void init(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.onTurn = p1;

        //TODO: 在初始化游戏的时候，还需要做什么？
    }

    /**
     * 进行下一个回合时应调用本方法。
     * 在这里执行每个回合结束时需要进行的操作。
     * <p>
     * (目前这里没有每个玩家进行n回合的计数机制的，请自行修改完成哦~）
     */

    //n=1时候的回合转换操作
    public void nextTurn() {
        if (steps!=allSteps) {
            steps++;
        }else {
            onTurn = onTurn == p1 ? p2 : p1;
            steps=1;}
        //AI
        if (p2.getUserName().equals("AI")){
            AI.AIClick();
            if (steps!=allSteps) {
                steps++;
            }else {
                onTurn = onTurn == p1 ? p2 : p1;
                steps=1;}
        }
        System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");
        scoreBoard.update();
        //TODO: 在每个回合结束的时候，还需要做什么 (例如...检查游戏是否结束？)
        if (checkWin()){
            if (checkWinner()!=null){
            if (checkWinner().equals(p1)){
                JOptionPane.showMessageDialog(null,p1.getUserName()+"获胜！","Game Over",
                        JOptionPane.WARNING_MESSAGE);
            }
            if (checkWinner().equals(p2)){
                JOptionPane.showMessageDialog(null,p2.getUserName()+"获胜！","Game Over",
                        JOptionPane.WARNING_MESSAGE);
            }
            if (checkWinner().equals(p3)){
                JOptionPane.showMessageDialog(null,"平局！","Game Over",
                        JOptionPane.WARNING_MESSAGE);
            }
          }
        }
    }


    /**
     * 获取正在进行当前回合的玩家。
     *
     * @return 正在进行当前回合的玩家
     */
    public Player getOnTurnPlayer() {
        return onTurn;
    }


    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }


    //读档
    public void readFileData(String fileName) {
        //todo: read date from file


    }

    //存档
    public void writeDataToFile(String fileName){
        //todo: write data into file


    }

    //checkWin
    public boolean checkWin(){
        if (Math.abs(p1.getScore()-p2.getScore())>mineCounter){
            return true;
        }
        //判断失误数
        if (mineCounter==0){
            return true;
        }
        return false;
    }

    public Player checkWinner(){
            if (p1.getScore()> p2.getScore()&&(p1.getScore()-p2.getScore())>mineCounter){
                return p1;
            }
            if (p1.getScore()< p2.getScore()&&(p2.getScore()-p1.getScore())>mineCounter){
                return p2;
            }
            if (mineCounter==0){
                if (p1.getMistake()>p2.getMistake()){
                    return p2;
                }
                if (p1.getMistake()<p2.getMistake()){
                    return p1;
                }
                else return p3;
            }
        return null;
    }
    //get and set
    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public Player getOnTurn() {
        return onTurn;
    }

    public void setOnTurn(Player onTurn) {
        this.onTurn = onTurn;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getAllSteps() {
        return allSteps;
    }

    public void setAllSteps(int allSteps) {
        this.allSteps = allSteps;
    }

    public int getMineCounter() {
        return mineCounter;
    }

    public void setMineCounter(int mineCounter) {
        this.mineCounter = mineCounter;
    }

    public int getTotalMine() {
        return totalMine;
    }

    public void setTotalMine(int totalMine) {
        this.totalMine = totalMine;
    }
}
