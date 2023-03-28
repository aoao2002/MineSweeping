package minesweeper;

import components.GridComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

    //mainFrame
    private MainFrame mainFrame;
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    //
    public GridComponent[][] getMineField() {
        return mineField;
    }
    private GridComponent[][] mineField;

    public int[][] getChessboard() {
        return chessboard;
    }
    public void setChessboard(int[][] chessboard) {
        this.chessboard = chessboard;
    }

    private int[][] chessboard;

    private final Random random = new Random();

    /**
     * 初始化一个具有指定行列数格子、并埋放了指定雷数的雷区。
     *
     * @param xCount    count of grid in column
     * @param yCount    count of grid in row
     * @param mineCount mine count
     */
    public GamePanel(int xCount, int yCount, int mineCount) {
        //设置属性
        //可视化
        this.setVisible(true);
        //可获得焦点
        this.setFocusable(true);
        //手动布局
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(GridComponent.gridSize * yCount, GridComponent.gridSize * xCount);
        //初始化
        initialGame(xCount, yCount, mineCount);
        //zmx不密集方法
        while (resetMine(xCount,yCount)){
            initialGame(xCount, yCount, mineCount);
        }
        //重绘component
        repaint();
    }

    public void initialGame(int xCount, int yCount, int mineCount) {
        //生成雷区，本质是J component ，
        mineField = new GridComponent[xCount][yCount];

        generateChessBoard(xCount, yCount, mineCount);

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                GridComponent gridComponent = new GridComponent(i, j);
                gridComponent.setGamePanel(this);
                //gridComponent.setContent(chessboard[i][j]);
                gridComponent.setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
                mineField[i][j] = gridComponent;
                this.add(mineField[i][j]);
            }
        }
    }


    public void generateChessBoard(int xCount, int yCount, int mineCount) {
        // 自己重新写雷区
        //todo: generate chessboard by your own algorithm
        chessboard = new int[xCount][yCount];
        for (int i=0 ; i<mineCount;){
        int r=random.nextInt(xCount);
        int c=random.nextInt(yCount);
        if (chessboard[r][c]!=-1){
             chessboard[r][c]=-1;
             i++;
           }
        }
        //填其他格子
        for (int i=0;i<xCount;i++){
            for (int j=0;j<yCount;j++){
                if (chessboard[i][j]!=-1){
                chessboard[i][j]=countMine(chessboard,i,j,xCount,yCount);
                }
            }
        }

    }

    //非雷格子算雷数
    public static int countMine(int[][]a,int i ,int j,int x,int y){
        int g=0;
        if (i>0&&a[i-1][j]==-1){g++;}
        if (i+1<x&&a[i+1][j]==-1){g++;}
        if (j+1<y&&a[i][j+1]==-1){g++;}
        if (j>0&&a[i][j-1]==-1){g++;}
        if (i>0&&j+1<y&&a[i-1][j+1]==-1){g++;}
        if (i+1<x&&j+1<y&&a[i+1][j+1]==-1){g++;}
        if (i+1<x&&j>0&&a[i+1][j-1]==-1){g++;}
        if (i>0&&j>0&&a[i-1][j-1]==-1){g++;}
        return g;
    }

    /**
     * 获取一个指定坐标的格子。
     * 注意请不要给一个棋盘之外的坐标哦~
     *
     * @param x 第x列
     * @param y 第y行
     * @return 该坐标的格子
     */
    public GridComponent getGrid(int x, int y) {
        try {
            return mineField[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    //zmx不密集方法
    public boolean resetMine(int x,int y){
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (chessboard[i][j]==-1){
                    if ((i>0&&i<x-1)&&(j>0&&j<y-1)){
                        if (chessboard[i+1][j]==-1&&chessboard[i-1][j]==-1&&chessboard[i][j+1]==-1&&chessboard[i][j-1]==-1){
                            return true;
                        }
                    }
                    if (i==0&&(j>0&&j<y-1)){
                        if (chessboard[i][j-1]==-1&&chessboard[i][j+1]==-1&&chessboard[i+1][j]==-1){
                            return true;
                        }
                    }
                    if (i==x-1&&(j>0&&j<y-1)){
                        if (chessboard[i][j-1]==-1&&chessboard[i][j+1]==-1&&chessboard[i-1][j]==-1){
                            return true;
                        }
                    }
                    if (j==0&&(i>0&&i<x-1)){
                        if (chessboard[i+1][j]==-1&&chessboard[i-1][j]==-1&&chessboard[i][j+1]==-1){
                            return true;
                        }
                    }
                    if (j==x-1&&(i>0&&i<x-1)){
                        if (chessboard[i+1][j]==-1&&chessboard[i-1][j]==-1&&chessboard[i][j-1]==-1){
                            return true;
                        }
                    }
                    if ((i==0&&j==0)){
                        if (chessboard[0][1]==-1&&chessboard[1][0]==-1){
                            return true;
                        }
                    }
                    if (i==x-1&&j==0){
                        if (chessboard[x-1][1]==-1&&chessboard[x-2][0]==-1){
                            return true;
                        }
                    }
                    if (i==x-1&&j==y-1){
                        if (chessboard[x-1][y-2]==-1&&chessboard[x-2][y-2]==-1){
                            return true;
                        }
                    }
                    if (i==0&&j==y-1){
                        if (chessboard[1][y-1]==-1&&chessboard[0][y-2]==-1){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
