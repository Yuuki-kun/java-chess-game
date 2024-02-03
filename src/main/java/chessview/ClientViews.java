/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chessview;

import chessController.Controller;
import chessController.IChessController;
import chessmodel.Piece;
import static chessview.clientView.chosePH;
import static chessview.clientView.phongH;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author tongcongminh
 */
public interface ClientViews extends WindowListener{

    
    public Piece.Player getPlayer();

    public void setPlayer(Piece.Player player);

    public void initClientGUI()throws IOException;

    public void run()throws IOException ;

    public void setLabelClient()throws FileNotFoundException, IOException;

    public void setLabelOppoent()throws FileNotFoundException, IOException;

    public boolean isIsP();
    

    public Scanner getSc();

    public BoardView getBoardView();

    public boolean isIsTurn();

    public String getClientName();

    //return client socket
    public Socket getClient();

    public ObjectOutputStream getOut();

    public ObjectInputStream getIn();

    public void setIsP(boolean h);

    public void setVi(boolean h);

    public void setTurn(boolean isTurn);

    public JFrame getFrame();

    public void setFrame(JFrame frame);

    public BufferedImage getClientImg();

    public void setClientImg(BufferedImage clientImg);

    public String getOpponentName();

    public void setOpponentName(String opponentName);

    public boolean getTurn();

    public void setDisplayPhongHauLabel(int x, int y);

    public void NoneDisplayPhongHau();

    public int getChosePH();

    public boolean dachonxong();

    public void setFalseDachonXong();

    public void initPH(Piece p, ObjectOutputStream out, IChessController chessDelegate, BoardView b);

    public textMoveHis getHis();

    public void addChat(String chat);
    public void setClientName(String name);
    public void displayMainMN(Controller c, ClientViews v);
    
    public void connect();
    
    public void setTime(int t);
    
    public void setIsplaying(boolean h);
    public void time();
    public int getBaseTime();
}
