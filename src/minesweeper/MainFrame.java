package minesweeper;


import components.GridComponent;
import components.MenuDemo;
import controller.GameController;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TimerTask;

public class MainFrame extends JFrame {
    public static GameController controller;

    public int getxCount() {
        return xCount;
    }

    public void setxCount(int xCount) {
        this.xCount = xCount;
    }

    public int getyCount() {
        return yCount;
    }

    public void setyCount(int yCount) {
        this.yCount = yCount;
    }

    private int xCount;
    private int yCount;
    //雷总数
    private int mineCount;
    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }
    //
    int second=0;//时钟计数
    private int steps=1;

    //刚开始菜单
    StartFrame startFrame;
    public StartFrame getStartFrame() {
        return startFrame;
    }

    public void setStartFrame(StartFrame startFrame) {
        this.startFrame = startFrame;
    }

    //菜单对象
    MenuDemo menu=new MenuDemo();
    public MenuDemo getMenu() {
        return menu;
    }
    public void setMenu(MenuDemo menu) {
        this.menu = menu;
    }


    //二级容器
    JPanel centerContainerPanel=new JPanel(new BorderLayout());
    //三级容器
    JPanel buttonPanel=new JPanel(new GridLayout(1,3,2,2));

    JLabel countTimeLabel=new JLabel("计时器： "+second+"s");
    public static JLabel countMine=new JLabel("剩余雷数： ");
    JLabel countTotalMine=new JLabel("初始雷数： ");
    public JLabel getCountMine() {
        return countMine;
    }

    public void setCountMine(JLabel countMine) {
        this.countMine = countMine;
    }


    //时钟监听器
    ActionListener timeListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            second++;
            countTimeLabel.setText("计时器： "+second+"s");
            timer.start();
        }
    };
    //时钟对象
    Timer timer=new Timer(1000,timeListener);



    public MainFrame() {
        //todo: change the count of xCount, yCount and mineCount by passing parameters from constructor
        this.xCount = 16;//grid of row
        this.yCount = 16;// grid of column
        this.mineCount = 40;// mine count
        //
        countTotalMine.setText("初始雷数： "+mineCount);
        countMine.setText("剩余雷数： "+mineCount);
        //
        this.setTitle("2021 CS102A Project");
        this.setLayout(new BorderLayout());
        //总界面大小
        this.setSize(yCount * GridComponent.gridSize , xCount * GridComponent.gridSize + 75+70);//上75，下12
        this.setLocationRelativeTo(null);

        //不能调界面大小
        //this.setResizable(false);

    //玩家
        //玩家命名
        String str1 = JOptionPane.showInputDialog("Enter 1st player's name:");
        String str2 = JOptionPane.showInputDialog("Enter 2st player's name:");
        if (str1!=null&&str2!=null){
        while (str1.equals(str2)&&!str1.equals("")&&!str2.equals("")){
             str2 = JOptionPane.showInputDialog("Repeated name!Please rewrite 2st player's name:");
        }}
        String str3=JOptionPane.showInputDialog("Set your steps:");
        if (str3!=null){
            if (!str3.equals("")){
                  steps=Integer.parseInt(str3);
            }
        }
        //玩家
        Player p1;Player p2;
        //创建玩家对象
        if (str1==null){p1 = new Player();}
        else if (str1.equals("")){ p1 = new Player();}
        else { p1 = new Player(str1);}

        if (str2==null){p2=new Player();}
        else if (str2.equals("")||str2==null){p2=new Player();}
        else if (str2.equals("AI")){
            AI ai=new AI();
            p2=ai;
        }
        else{ p2 = new Player(str2);}


        controller = new GameController(p1, p2,steps);
        GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
        gamePanel.setMainFrame(this);
        controller.setGamePanel(gamePanel);
        ScoreBoard scoreBoard = new ScoreBoard(p1, p2, xCount, yCount);
        controller.setScoreBoard(scoreBoard);
        controller.setTotalMine(mineCount);
        controller.setMineCounter(mineCount);
        controller.setTotalX(xCount);
        controller.setTotalY(yCount);
        //AI
//        if (p2.getUserName().equals("AI")){
//            AI ai=new AI();
//            controller.setP2(ai);
//            controller.getScoreBoard().setP2(ai);
//        }

        //游戏panel放中间
        centerContainerPanel.add(gamePanel,BorderLayout.CENTER);
        centerContainerPanel.add(scoreBoard,BorderLayout.SOUTH);


        //计时器，计数面板
        setCountPanel();




        //对档的按钮和操作
        JButton clickBtn = new JButton("存档");//改名为Save
        clickBtn.setSize(80, 20);


        //加按钮
        buttonPanel.add(clickBtn);

        clickBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "input the fileName here");
            System.out.println("Save fileName :"+fileName);
            //增加一些read，open的操作

            Save.saveFile(fileName);


            //执行读档或存档操作
            //controller.readFileData(fileName);
            //controller.writeDataToFile(fileName);

        });


        //其他按钮
        JButton tool=new JButton("道具");
        tool.addActionListener(e -> {

            String [] options = {"透视3s","多点一次","跳过回合",};
            int n =  JOptionPane.showOptionDialog(null,"请选择你要使用的道具：","道具",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);		//选择对话框*/
            //透视
            if (n==0){
                seeWhole();
                ActionListener timeListener=new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cannotSee();
                    }
                };
                //时钟对象
                Timer timer=new Timer(3000,timeListener);
                timer.setRepeats(false);
                timer.start();
            }
            //多点一次
            if (n==1){
                controller.setSteps(controller.getSteps()-1);
            }
            //跳过回合
            if (n==2){
                controller.nextTurn();
            }
        });



        //加入透视按钮
        buttonPanel.add(tool);

        //对档的按钮和操作
        JButton readBtn = new JButton("读档");
        clickBtn.setSize(80, 20);
        readBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "input the fileName here");
            System.out.println("Read fileName :"+fileName);
            Save.readFile(fileName);
        });


        //加按钮
        buttonPanel.add(readBtn);

        //加按钮容器
        centerContainerPanel.add(buttonPanel,BorderLayout.NORTH);
        //加入中间层容器
        this.add(centerContainerPanel,BorderLayout.CENTER);

        //状态栏

        //时钟运行
        timer.start();


        //菜单栏
        menu.setMainFrame(this);
        this.add(menu,BorderLayout.NORTH);


        //可视化mainframe和关闭
        //this.setVisible(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                startFrame.setVisible(true);
                timer.stop();
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                GridComponent.leftCount=0;
            }
        });


    }
    //模式切换构造器
    public MainFrame(int x,int y,int mine){
        this.xCount = x;//grid of row
        this.yCount = y;// grid of column
        this.mineCount = mine;// mine count
        //
        countTotalMine.setText("初始雷数： "+mineCount);
        countMine.setText("剩余雷数： "+mineCount);
        //
        this.setTitle("2021 CS102A Project");
        this.setLayout(new BorderLayout());
        //总界面大小
        this.setSize(yCount * GridComponent.gridSize , xCount * GridComponent.gridSize + 75+70);//上75，下12
        this.setLocationRelativeTo(null);

        //不能调界面大小
        //this.setResizable(false);

        //玩家
        //玩家命名
        String str1 = JOptionPane.showInputDialog("Enter 1st player's name:");
        String str2 = JOptionPane.showInputDialog("Enter 2st player's name:");
        if (str1!=null&&str2!=null){
            while (str1.equals(str2)&&!str1.equals("")&&!str2.equals("")){
                str2 = JOptionPane.showInputDialog("Repeated name!Please rewrite 2st player's name:");
            }}
        String str3=JOptionPane.showInputDialog("Set your steps:");
        if (str3!=null){
            if (!str3.equals("")){
                steps=Integer.parseInt(str3);
            }
        }
        //玩家
        Player p1;Player p2;
        //创建玩家对象
        if (str1==null){p1 = new Player();}
        else if (str1.equals("")){ p1 = new Player();}
        else { p1 = new Player(str1);}

        if (str2==null){p2=new Player();}
        else if (str2.equals("")||str2==null){p2=new Player();}
        else if (str2.equals("AI")){
            AI ai=new AI();
            p2=ai;
        }
        else{ p2 = new Player(str2);}


        controller = new GameController(p1, p2,steps);
        GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
        gamePanel.setMainFrame(this);
        controller.setGamePanel(gamePanel);
        ScoreBoard scoreBoard = new ScoreBoard(p1, p2, xCount, yCount);
        controller.setScoreBoard(scoreBoard);
        controller.setTotalMine(mineCount);
        controller.setMineCounter(mineCount);
        controller.setTotalX(xCount);
        controller.setTotalY(yCount);
        //AI
//        if (p2.getUserName().equals("AI")){
//            AI ai=new AI();
//            controller.setP2(ai);
//            controller.getScoreBoard().setP2(ai);
//        }


        //游戏panel放中间
        centerContainerPanel.add(gamePanel,BorderLayout.CENTER);
        centerContainerPanel.add(scoreBoard,BorderLayout.SOUTH);


        //计时器，计数面板
        setCountPanel();




        //对档的按钮和操作
        JButton clickBtn = new JButton("存档");//改名为Save
        clickBtn.setSize(80, 20);


        //加按钮
        buttonPanel.add(clickBtn);

        clickBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "input the fileName here");
            System.out.println("fileName :"+fileName);
            //增加一些read，open的操作

            Save.saveFile(fileName);


            //执行读档或存档操作
            controller.readFileData(fileName);
            controller.writeDataToFile(fileName);

        });


        //其他按钮
        JButton tool=new JButton("道具");
        tool.addActionListener(e -> {

            String [] options = {"透视3s","多点一次","跳过回合",};
            int n =  JOptionPane.showOptionDialog(null,"请选择你要使用的道具：","道具",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);		//选择对话框*/
            //透视
            if (n==0){
               seeWhole();
                ActionListener timeListener=new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cannotSee();
                    }
                };
                //时钟对象
                Timer timer=new Timer(3000,timeListener);
                timer.setRepeats(false);
                timer.start();
            }
            //多点一次
            if (n==1){
                controller.setSteps(controller.getSteps()-1);
            }
            //跳过回合
            if (n==2){
                controller.nextTurn();
            }
        });



        //加入透视按钮
        buttonPanel.add(tool);

        //对档的按钮和操作
        JButton readBtn = new JButton("读档");
        clickBtn.setSize(80, 20);
        readBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "input the fileName here");
            System.out.println("Read fileName :"+fileName);
            Save.readFile(fileName);
        });

        //加按钮
        buttonPanel.add(readBtn);

        //加按钮容器
        centerContainerPanel.add(buttonPanel,BorderLayout.NORTH);
        //加入中间层容器
        this.add(centerContainerPanel,BorderLayout.CENTER);

        //状态栏

        //时钟运行
        timer.start();


        //菜单栏
        menu.setMainFrame(this);
        this.add(menu,BorderLayout.NORTH);


        //可视化mainframe和关闭
        //this.setVisible(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                startFrame=new StartFrame();
                startFrame.setVisible(true);
                timer.stop();
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                GridComponent.leftCount=0;
            }
        });
    }


    //透视方法
    public void seeWhole(){
        for (int i = 0; i < controller.getGamePanel().getMineField().length; i++) {
            for (int j = 0; j < controller.getGamePanel().getMineField()[i].length; j++) {
                controller.getGamePanel().getMineField()[i][j].seeWhole();
            }
        }
    }
    //恢复透视前
    public void cannotSee(){
        for (int i = 0; i < controller.getGamePanel().getMineField().length; i++) {
            for (int j = 0; j < controller.getGamePanel().getMineField()[i].length; j++) {
                controller.getGamePanel().getMineField()[i][j].cannotSee();
            }
        }
    }
    //计时计数状态栏
    private void setCountPanel(){
        JPanel countPanel=new JPanel(new GridBagLayout());

        countMine.setOpaque(true);
        countMine.setBackground(Color.white);


        countTimeLabel.setOpaque(true);
        countTimeLabel.setBackground(Color.white);

        countTotalMine.setOpaque(true);
        countTotalMine.setBackground(Color.white);

        GridBagConstraints c1=new GridBagConstraints(0,0,1,1,1.0,1.0,
                GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        countPanel.add(countMine,c1);
        GridBagConstraints c2=new GridBagConstraints(1,0,1,1,1.0,1.0,
                GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        countPanel.add(countTimeLabel,c2);
        GridBagConstraints c3=new GridBagConstraints(2,0,1,1,1.0,1.0,
                GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        countPanel.add(countTotalMine,c3);

        add(countPanel,BorderLayout.SOUTH);
    }
}
