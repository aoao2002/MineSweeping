package components;

import minesweeper.MainFrame;
import minesweeper.StartFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuDemo extends JMenuBar {
    //判断改变变量
    private int judgeA=0;
    //判断
    boolean aBoolean=false;
    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    //Mainframe
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    MainFrame mainFrame;

    //StartFrame
    public StartFrame getStartFrame() {
        return startFrame;
    }

    public void setStartFrame(StartFrame startFrame) {
        this.startFrame = startFrame;
    }

    StartFrame startFrame;
    //
    private int xCount=16;
    private int yCount=16;
    private int mineCount=40;

    public MenuDemo()
    {
        add(createFileMenu());    //添加“文件”菜单
        add(createEditMenu());    //添加“编辑”菜单

    }

    //定义“文件”菜单
    private JMenu createFileMenu()
    {
        JMenu menu=new JMenu("模式选择");
        menu.setMnemonic(KeyEvent.VK_F);    //设置快速访问符
        JMenuItem item1=new JMenuItem("初级",KeyEvent.VK_N);
        item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        item1.addActionListener(e -> {
           //待写
            GridComponent.leftCount=0;
            mainFrame.dispose();
            mainFrame=new MainFrame(9,9,10);
            mainFrame.setVisible(true);
        });
        menu.add(item1);
        JMenuItem item2=new JMenuItem("中级",KeyEvent.VK_O);
        item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        item2.addActionListener(e -> {
            //待写
            GridComponent.leftCount=0;
            mainFrame.dispose();
            mainFrame=new MainFrame(16,16,40);
            mainFrame.setVisible(true);
        });
        menu.add(item2);
        JMenuItem item3=new JMenuItem("高级",KeyEvent.VK_S);
        item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        item3.addActionListener(e -> {
            //待写
            GridComponent.leftCount=0;
            mainFrame.dispose();
            mainFrame=new MainFrame(16,30,99);
            mainFrame.setVisible(true);
        });
        menu.add(item3);
        //分割线
        menu.addSeparator();
        //
        JMenuItem item4=new JMenuItem("自定义模式",KeyEvent.VK_E);
        item4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
        item4.addActionListener(e -> {
            if (window3()){
            //待写
            GridComponent.leftCount=0;
            mainFrame.dispose();
            mainFrame=new MainFrame(xCount,yCount,mineCount);
            mainFrame.setVisible(true);}
            else {JOptionPane.showMessageDialog(null,"不合理输入","错误",JOptionPane.WARNING_MESSAGE);	}
        });
        menu.add(item4);

        return menu;
    }
    //定义“编辑”菜单
    private JMenu createEditMenu()
    {
        JMenu menu=new JMenu("帮助");
        menu.setMnemonic(KeyEvent.VK_E);
        JMenuItem item1=new JMenuItem("扫雷源代码",KeyEvent.VK_U);
        item1.setEnabled(false);
        menu.add(item1);
        menu.addSeparator();
        JMenuItem item2=new JMenuItem("游戏介绍",KeyEvent.VK_T);
        item2.addActionListener(e -> {
            window1();
        });
        menu.add(item2);
        JMenuItem item3=new JMenuItem("输赢规则",KeyEvent.VK_C);
        item3.addActionListener(e -> {
            window2();
        });
        menu.add(item3);
        menu.addSeparator();
        JCheckBoxMenuItem cbMenuItem=new JCheckBoxMenuItem("一开开一片");
        cbMenuItem.addActionListener(e -> {
            if (judgeA==0){
            this.aBoolean=true;judgeA=1;
            }
            else if (judgeA==1){
                this.aBoolean=false;judgeA=0;
            }
        });
        menu.add(cbMenuItem);
        return menu;
    }
    
    public void window1(){
        JOptionPane.showMessageDialog(null," 1.游戏区基本元素包括雷区、地雷计数器（位于左上角，记录剩余地雷数），确定大小的矩\n" +
                        "形雷区中随机布置一定数量的地雷(初级为 9*9 个方块 10 个雷，中级为 16*16 个方块 40 个\n" +
                        "雷，高级为 16*30 个方块 99 个雷，自定义级别可以自己设定雷区大小和雷数，但是雷区大\n" +
                        "小不能超过 24*30，且雷数不能超过总格子数目的一半)，玩家需要找出雷区中的所有不是\n" +
                        "地雷的方块，而不许踩到地雷。\n"+
                "2. 游戏的基本操作包括左键单击（Left Click）、右键单击（Right Click）。其中左键用于打开\n" +
                        "安全的格子，推进游戏进度；右键用于标记地雷，来获得积分。操作：\n" +
                        "2.1 左键单击：如果确定不是雷，点击左键，开一格。如果不幸触雷，则扣除玩家 1 分。\n" +
                        "2.2 右键单击：在判断为地雷的方块上按下右键，可以标记地雷。地雷被标记时立刻揭晓\n" +
                        "并结算，如果标记正确（确实是雷），则显示小红旗并给标记者加 1 分；如果标记错误，\n" +
                        "该地方实际上并没有雷，则提示标记错误并给标记者增加一次失误数。",
                "对战扫雷游戏介绍",JOptionPane.WARNING_MESSAGE);	//消息对话框
    }
    public void window2(){
        JOptionPane.showMessageDialog(null,"获胜条件：每进行一回合均需要比较双方的分数。\n" +
                        "a. 如果双方的分数差距大于游戏区中未揭晓的雷数，则直接判定优势方获胜。\n" +
                        "b. 如果在游戏中所有雷都被揭晓时双方分数依然相同，则失误数少的一方（失误包含误触\n" +
                        "雷以及标记错误）获胜。\n" +
                        "c. 如果失误数依然相同，则双方平局。",
                "对战扫雷游戏输赢判定",JOptionPane.WARNING_MESSAGE);	//消息对话框
    }
    public boolean window3(){
        String input=JOptionPane.showInputDialog(null,"请用阿拉伯数字输入X,Y,雷数 用一个空格分隔","自定义模式",JOptionPane.WARNING_MESSAGE);
        String[]a=input.split(" ");
        //boolean w=true;
        //for (String b:a){
         //   if ()
        //}
        //还需判断是不是数字
        if (isValue(a)){
            if (isLegal(a)){
        xCount=Integer.parseInt(a[0]);
        yCount=Integer.parseInt(a[1]);
        mineCount=Integer.parseInt(a[2]);
        if (xCount>24||yCount>30||mineCount>xCount*yCount/2){
            return false;
        }
            return true;}}
           return false;
    }
    public  boolean isValue(String[]a){
        for (int i=0;i<a.length;i++){
            for (int j=0;j<a[i].length();j++){
                if (!Character.isDigit(a[i].charAt(j))){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isLegal(String[]a){
        if (isValue(a)){
            if (a.length==3){
                return true;
            }
        }
        return false;
    }
}