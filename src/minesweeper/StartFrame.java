package minesweeper;

import components.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame {
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    MainFrame mainFrame;
    public StartFrame(){
        this.setTitle("初始界面");
        this.setLayout(new BorderLayout());
        //总界面大小
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        //不能调界面大小
        this.setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton StartGame=new JButton("Start NewGame");
        StartGame.setBackground(Color.white);
        StartGame.setFont(new Font("Impact",0,30));
        StartGame.setSize(200,60);
        StartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runMain();
            }
        });
        add(StartGame,BorderLayout.CENTER);

        JButton ContinueGame=new JButton("Continue Game");
        ContinueGame.setSize(200,60);
        ContinueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runMain1();
            }
        });
        ContinueGame.setFont(new Font("Impact",0,30));
        add(ContinueGame,BorderLayout.CENTER);

        StartGame.setLocation(115,270);
        ContinueGame.setLocation(115,340);
        //背景图
        Image image=new ImageIcon("src/Picture/刚开始界面3.jpg").getImage();
        JPanel panelPicture=new BackgroundPanel(image);

        this.add(panelPicture,BorderLayout.CENTER);
        //播放音乐
        Music.BGM1();
    }
    public void runMain(){
        SwingUtilities.invokeLater(() -> {


            MainFrame mainFrame = new MainFrame();
            this.mainFrame=mainFrame;
            mainFrame.setStartFrame(this);
            mainFrame.setVisible(true);
            this.setVisible(false);

        });
    }
    public void runMain1(){
        SwingUtilities.invokeLater(() -> {


            MainFrame mainFrame = this.mainFrame;
            mainFrame.timer.start();
            mainFrame.setStartFrame(this);
            mainFrame.setVisible(true);
            this.setVisible(false);

        });
    }
}