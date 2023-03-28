package minesweeper;

import components.GridComponent;
import controller.GameController;
import entity.GridStatus;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class Save extends JComponent {
    private int x;
    private int y;

    public static void saveFile(String file){
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            //存棋盘
            for (int[] v : MainFrame.controller.getGamePanel().getChessboard()) {
                for (int d : v) {
                    bw.write(d +" ");
                    //bw.newLine();
                }
                bw.newLine();
            }

            //存图案
            for (GridComponent[] a:MainFrame.controller.getGamePanel().getMineField()){
                for (GridComponent b:a){
                    if (b.getStatus().equals(GridStatus.Covered)){
                        bw.write("Cover ");
                    }
                    if (b.getStatus().equals(GridStatus.Clicked)){
                        bw.write("Clicked ");
                    }
                    if (b.getStatus().equals(GridStatus.Flag)){
                        bw.write("Flag ");
                    }
                    // System.out.println(b.getStatus());
                } bw.newLine();
            }

            //总共的雷数和剩余雷数和总要求回合数
            bw.write(MainFrame.controller.getTotalMine() +" ");
            bw.write(MainFrame.controller.getMineCounter() +" ");
            bw.write(MainFrame.controller.getAllSteps()+" ");
            bw.newLine();

            //存当前玩家
            bw.write(MainFrame.controller.getOnTurnPlayer().getUserName()+" ");
            bw.write(MainFrame.controller.getSteps() +" ");
            bw.newLine();
            //存玩家1
            bw.write(MainFrame.controller.getP1().getUserName()+" ");
            bw.write(MainFrame.controller.getP1().getScore() +" ");
            bw.write(MainFrame.controller.getP1().getMistake() +" ");
            bw.newLine();
            //存玩家2
            bw.write(MainFrame.controller.getP2().getUserName()+" ");
            bw.write(MainFrame.controller.getP2().getScore() +" ");
            bw.write(MainFrame.controller.getP2().getMistake() +" ");
            bw.newLine();

            bw.close();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String fileName){
        File file=new File(fileName);
        BufferedReader br=null;
        int line=1;
        Player p1=new Player();
        Player p2=new Player();
        try {
            br=new BufferedReader(new FileReader(file));
            int xCount=MainFrame.controller.getGamePanel().getChessboard().length;
            int yCount=MainFrame.controller.getGamePanel().getChessboard()[0].length;
            int [][]chessBoard=new int[xCount][yCount];
            GridComponent[][]mineFiled=new GridComponent[xCount][yCount];
            GridStatus[][]statuses=new GridStatus[xCount][yCount];
            String str=null;

            GameController gameController=new GameController(p1,p2,1);

            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    mineFiled[i][j]=new GridComponent(i,j);
                    statuses[i][j]=mineFiled[i][j].getStatus();
                }
            }

            //读取棋盘
            while ((line>=1&&line<=xCount)&&(str=br.readLine())!=null){
                String []ss=str.split("\\s+");
                for (int i=0;i<ss.length;i++){
                    chessBoard[line-1][i]=Integer.parseInt(ss[i]);
                }
                line++;
            }
            // gameController.getGamePanel()=MainFrame.controller.getGamePanel();
            MainFrame.controller.getGamePanel().setChessboard(chessBoard);

            //读取状态
            while ((line>xCount&&line<=2*xCount)&&(str=br.readLine())!=null){
                String[]ss=str.split("\\s+");
                for (int i=0;i< ss.length;i++){
                    if (ss[i].equals("Cover")){
                        MainFrame.controller.getGamePanel().getMineField()[line-xCount-1][i].setStatus(GridStatus.Covered);
                    }
                    if (ss[i].equals("Clicked")){
                        MainFrame.controller.getGamePanel().getMineField()[line-xCount-1][i].setStatus(GridStatus.Clicked);
                    }
                    if (ss[i].equals("Flag")){
                        MainFrame.controller.getGamePanel().getMineField()[line-xCount-1][i].setStatus(GridStatus.Flag);
                    }
                }
                line++;
            }

//            for (int i = 0; i < xCount; i++) {
//                for (int j = 0; j < yCount; j++) {
//                    mineFiled[i][j].setStatus(statuses[i][j]);
//                }
//            }
//            for (GridComponent []arr:mineFiled){
//                for (GridComponent gridComponent:arr){
//                    System.out.println("MineField "+gridComponent.getStatus());
//                }
//            }
            //gameController.getGamePanel().setMineField(mineFiled);

            //MainFrame.controller.getGamePanel().setMineField(mineFiled);

            for (GridComponent []arr:MainFrame.controller.getGamePanel().getMineField()){
                for (GridComponent gridComponent:arr){
                    //  System.out.println(gridComponent.getStatus());
                    gridComponent.repaint();
                }
            }
//             MainFrame.controller.getGamePanel().getGrid()repaint();



            //状态
            while ((line>2*xCount&&line<=2*xCount+1)&&(str=br.readLine())!=null){
                String[]ss=str.split("\\s+");
                int allMine=Integer.parseInt(ss[0]);

                MainFrame.controller.setTotalMine(allMine);
                //gameController.setTotalMine(allMine);
                //剩余雷数
                MainFrame.controller.setMineCounter(Integer.parseInt(ss[1]));
                //gameController.setMineCounter(Integer.parseInt(ss[1]));
                //总共回合数
                MainFrame.controller.setAllSteps(Integer.parseInt(ss[2]));
                //gameController.setAllSteps(Integer.parseInt(ss[2]));

                line++;
            }


            //当前玩家以及状态
            while ((line>2*xCount+1&&line<=2*xCount+2)&&(str=br.readLine())!=null){
                String[]ss=str.split("\\s+");
                Player onTurn=new Player(ss[0]);
                MainFrame.controller.setOnTurn(onTurn);
                //gameController.setOnTurn(onTurn);
                System.out.println("onturn"+MainFrame.controller.getOnTurnPlayer().getUserName());
                line++;
            }

            //玩家一以及状态
            while ((line>2*xCount+2&&line<=2*xCount+3)&&(str=br.readLine())!=null){
                String[]ss=str.split("\\s+");
                p1 =new Player(ss[0]);
                MainFrame.controller.setP1(p1);
                MainFrame.controller.getScoreBoard().setP1(p1);
                //gameController.setP1(p1);
                MainFrame.controller.getP1().setScore(Integer.parseInt(ss[1]));
                //gameController.getP1().setScore(Integer.parseInt(ss[1]));
                MainFrame.controller.getP1().setMistake(Integer.parseInt(ss[2]));
                //gameController.getP1().setMistake(Integer.parseInt(ss[2]));
//                System.out.println("p1"+MainFrame.controller.getP1().getUserName());
//                System.out.println("p1 score "+MainFrame.controller.getP1().getScore());
//                System.out.println("p1 mistake "+MainFrame.controller.getP1().getMistake());
                System.out.println("p1 "+MainFrame.controller.getP1().getUserName());
                line++;
            }

            //玩家二以及状态
            while ((line==2*xCount+4)&&(str=br.readLine())!=null){
                String[]ss=str.split("\\s+");
                p2=new Player(ss[0]);
                MainFrame.controller.setP2(p2);
                MainFrame.controller.getScoreBoard().setP2(p2);
                //gameController.setP2(p2);
                MainFrame.controller.getP2().setScore(Integer.parseInt(ss[1]));
                //gameController.getP2().setScore(Integer.parseInt(ss[1]));
                MainFrame.controller.getP2().setMistake(Integer.parseInt(ss[2]));
//                gameController.getP2().setMistake(Integer.parseInt(ss[2]));
//                System.out.println("p2"+MainFrame.controller.getP2().getUserName());
//                System.out.println("p2 score "+MainFrame.controller.getP2().getScore());
//                System.out.println("p2 mistake "+MainFrame.controller.getP2().getMistake());
                System.out.println("p2 "+MainFrame.controller.getP2().getUserName());
                line++;
            }
            //System.out.println("Read successful");System.out.println(Arrays.toString());
            GridComponent[][] gridComponent=MainFrame.controller.getGamePanel().getMineField();
            for (GridComponent[]gp:gridComponent){
                for (GridComponent gridComponent1:gp){
                    gridComponent1.repaint();
                }
            }
            MainFrame.controller.getScoreBoard().update();



            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                }
            }
        }

    }
}
