package minesweeper;
import entity.*;
import minesweeper.*;
import components.*;
import controller.*;

import java.util.Random;

public class AI extends Player {
    public AI(){
        super("AI");
    }
    public static void AIClick(){
        System.out.println("AI Move");
        Random randomMove=new Random();
        int AIMove=randomMove.nextInt(2);
        Random randomX=new Random();
        Random randomY=new Random();
        int x=randomX.nextInt(MainFrame.controller.getTotalX());
        int y= randomY.nextInt(MainFrame.controller.getTotalY());
        if (AIMove==0){
            while (!MainFrame.controller.getGamePanel().getMineField()[x][y].getStatus().equals(GridStatus.Covered)){
                x=randomX.nextInt(MainFrame.controller.getTotalX());
                y=randomY.nextInt(MainFrame.controller.getTotalY());
                if (MainFrame.controller.getGamePanel().getMineField()[x][y].getStatus().equals(GridStatus.Covered)){
                    break;
                }
            }
            MainFrame.controller.getGamePanel().getMineField()[x][y].onMouseLeftClicked();
        }
        if (AIMove==1){
            while (!MainFrame.controller.getGamePanel().getMineField()[x][y].getStatus().equals(GridStatus.Covered)){
                x=randomX.nextInt(MainFrame.controller.getTotalX());
                y=randomY.nextInt(MainFrame.controller.getTotalY());
                if (MainFrame.controller.getGamePanel().getMineField()[x][y].getStatus().equals(GridStatus.Covered)){
                    break;
                }
            }
            MainFrame.controller.getGamePanel().getMineField()[x][y].onMouseRightClicked();
        }
        }
    }

