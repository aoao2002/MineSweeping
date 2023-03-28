package components;

import entity.GridStatus;
import minesweeper.GamePanel;
import minesweeper.MainFrame;
import minesweeper.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

//扫雷里的小块
public class GridComponent extends BasicComponent {

    public static int gridSize = 25;  //30改为25
    ImageIcon lei=new ImageIcon("src/Picture/雷进阶2.png");
    ImageIcon flag=new ImageIcon("src/Picture/旗.jpg");

    //横纵坐标
    private int row;
    private int col;

    //左键点击次数
    public static int leftCount=0;

    //gamePanel
    private GamePanel gamePanel;
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    //格子状态
    private GridStatus status = GridStatus.Covered;
    //private int content = 0;
    //储存状态
    private int recordStatus=0;
    /**  recordStatus==0 --- covered
     *   recordStatus==-1--- bomb already
     *   recordStatus==1 --- flag already
     *   recordStatus==2 --- clicked already*/

    public GridComponent(int x, int y) {
        this.setSize(gridSize, gridSize);
        this.row = x;
        this.col = y;
    }

    @Override
    public void onMouseLeftClicked() {
        //原来代码
        System.out.printf("Gird (%d,%d) is left-clicked.\n", row, col);
        //首发不触雷
        while (leftCount==0&&MainFrame.controller.getGamePanel().getChessboard()[row][col]==-1){
            int [][]a=MainFrame.controller.getGamePanel().getChessboard();
            MainFrame.controller.getGamePanel().initialGame(a.length,a[0].length,MainFrame.controller.getGamePanel().getMainFrame().getMineCount());
            //   System.out.println("B"+leftCount);
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col]!=-1){leftCount=1;}
            //repaint();
        }
        //未被点击时转换成被点击状态
        if (this.status == GridStatus.Covered) {

            if (MainFrame.controller.getGamePanel().getChessboard()[row][col]==-1){
                this.status = GridStatus.Clicked;
                Music.boom();
                int b=MainFrame.controller.getMineCounter();
                b--;
                MainFrame.controller.setMineCounter(b);
                //
                MainFrame.countMine.setText("剩余雷数： "+b);
                //减分
                MainFrame.controller.getOnTurnPlayer().costScore();
                recordStatus=-1;
                //
                leftCount=1;
            }
            else {
                //一开开一片
                if (MainFrame.controller.getGamePanel().getChessboard()[row][col]==0&&gamePanel.getMainFrame().getMenu().aBoolean){
                    OpenTogether(this.row,this.col);
                }
                this.status = GridStatus.Clicked;
                Music.Click();
                recordStatus=2;
                leftCount=1;
            }
            repaint();
            if (!MainFrame.controller.getOnTurnPlayer().getUserName().equals("AI"))MainFrame.controller.nextTurn();
        }

        //TODO: 在左键点击一个格子的时候，还需要做什么？

    }

    @Override
    public void onMouseRightClicked() {
        System.out.printf("Gird (%d,%d) is right-clicked.\n", row, col);
        if (this.status == GridStatus.Covered) {

            Music.flag();

            if (MainFrame.controller.getGamePanel().getChessboard()[row][col]==-1){
                this.status = GridStatus.Flag;recordStatus=1;
                MainFrame.controller.getOnTurnPlayer().addScore();
                int b=MainFrame.controller.getMineCounter();
                b--;
                MainFrame.controller.setMineCounter(b);
                //
                MainFrame.countMine.setText("剩余雷数： "+b);
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col]!=-1){
                this.status = GridStatus.Clicked;recordStatus=2;
                JOptionPane.showMessageDialog(null,"标记错误啦");
                MainFrame.controller.getOnTurnPlayer().addMistake();
            }
            repaint();
            if (!MainFrame.controller.getOnTurnPlayer().getUserName().equals("AI"))MainFrame.controller.nextTurn();
        }

        //TODO: 在右键点击一个格子的时候，还需要做什么？

    }

    //对格子不同状态进行的绘画
    public void draw(Graphics g) {

        if (this.status == GridStatus.Covered) {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }


        if (this.status == GridStatus.Clicked) {
            //炸雷
            if (isMine()){
                g.setColor(Color.RED);
                g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
                g.drawImage(lei.getImage(), 0, 0, 25, 25, new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
            }
            else {
               g.setColor(Color.ORANGE);
               g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
               g.setColor(Color.BLACK);
               g.drawString(Integer.toString(MainFrame.controller.getGamePanel().getChessboard()[row][col]), getWidth() / 2 - 5, getHeight() / 2 + 5);}
        }


        if (this.status == GridStatus.Flag) {

            g.drawImage(flag.getImage(), 0, 0, 25, 25, new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        }
    }

    //public void setContent(int content) {this.content = content;}

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        draw(g);
    }
    //判断是否为雷
    public boolean isMine(){
        return MainFrame.controller.getGamePanel().getChessboard()[row][col] == -1;
    }
    //透视状态
    public  void seeWhole(){
        this.status = GridStatus.Clicked;
        repaint();
    }
    //透视复原
    public void cannotSee(){
        if(recordStatus == 0){
            this.status = GridStatus.Covered;
        }else if(recordStatus == 1){
            this.status = GridStatus.Flag;
        }else if(recordStatus == 2) {
            this.status = GridStatus.Clicked;
        }
        repaint();
    }
    //开格
    public void openGrid(){
        if (MainFrame.controller.getGamePanel().getChessboard()[row][col]!=-1){
            this.status=GridStatus.Clicked;
            recordStatus=2;
             }repaint();
    }
    //一开开一片
    public void OpenTogether(int row,int col){


        if (MainFrame.controller.getGamePanel().getGrid(row, col).getStatus()==GridStatus.Clicked){return;}
        MainFrame.controller.getGamePanel().getGrid(row, col).openGrid();


        //关联打开0
        if (row>0&&col>0&& gamePanel.getChessboard()[row-1][col-1]==0){
            OpenTogether(row-1, col-1);
            openGrid();
        }
        if (row>0&&gamePanel.getChessboard()[row-1][col]==0){
            OpenTogether(row-1, col);
            openGrid();}
        if (row>0&&col<gamePanel.getMainFrame().getyCount()-1&&gamePanel.getChessboard()[row-1][col+1]==0){
            OpenTogether(row-1, col+1);
            openGrid();}
        if (col>0&&gamePanel.getChessboard()[row][col-1]==0){
            OpenTogether(row, col-1);
            openGrid();}
        if (col<gamePanel.getMainFrame().getyCount()-1&&gamePanel.getChessboard()[row][col+1]==0){
            OpenTogether(row, col+1);
            openGrid();}
        if (row<gamePanel.getMainFrame().getxCount()-1&&col>0&&gamePanel.getChessboard()[row+1][col-1]==0){
            OpenTogether(row+1, col-1);
            openGrid();}
        if (row<gamePanel.getMainFrame().getxCount()-1&&gamePanel.getChessboard()[row+1][col]==0){
            OpenTogether(row+1, col);
            openGrid();}
        if (row<gamePanel.getMainFrame().getxCount()-1&&col<gamePanel.getMainFrame().getyCount()-1&&gamePanel.getChessboard()[row+1][col+1]==0){
            OpenTogether(row+1, col+1);
            openGrid();}


        //打开0旁边
        if (row>0&&col>0&& gamePanel.getChessboard()[row][col]==0){
            MainFrame.controller.getGamePanel().getGrid(row-1, col-1).openGrid();
        }
        if (row>0&&gamePanel.getChessboard()[row][col]==0){
            MainFrame.controller.getGamePanel().getGrid(row-1, col).openGrid();}
        if (row>0&&col<gamePanel.getMainFrame().getyCount()-1&&gamePanel.getChessboard()[row][col]==0){
            MainFrame.controller.getGamePanel().getGrid(row-1, col+1).openGrid();}
        if (col>0&&gamePanel.getChessboard()[row][col]==0){
            MainFrame.controller.getGamePanel().getGrid(row, col-1).openGrid();}
        if (col<gamePanel.getMainFrame().getxCount()-1&&gamePanel.getChessboard()[row][col]==0){
            MainFrame.controller.getGamePanel().getGrid(row, col+1).openGrid();
        }
        if (row<gamePanel.getMainFrame().getxCount()-1&&col>0&&gamePanel.getChessboard()[row][col]==0){
            MainFrame.controller.getGamePanel().getGrid(row+1, col-1).openGrid();
        }
        if (row<gamePanel.getMainFrame().getxCount()-1&&gamePanel.getChessboard()[row][col]==0){
            MainFrame.controller.getGamePanel().getGrid(row+1, col).openGrid();}
        if (row<gamePanel.getMainFrame().getxCount()-1&&col<gamePanel.getMainFrame().getyCount()-1
                &&gamePanel.getChessboard()[row][col]==0){
            MainFrame.controller.getGamePanel().getGrid(row+1, col+1).openGrid();
        }

    }
    //get and set
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public GridStatus getStatus() {
        return status;
    }

    public void setStatus(GridStatus status) {
        this.status = status;
    }

    //public int getContent() {return content;}

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

}
