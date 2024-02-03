/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessview;

import chessController.IChessController;
import chessmodel.ChessConstants;
import chessmodel.Piece;
import chessmodel.Request;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author tongcongminh
 */
public class PhongHau extends JLabel implements ActionListener {

    int chose = -1;
    JButton[] pieces;
    boolean dachonxong = false;

    ClientViews client;
    
    public PhongHau(ClientViews client) {
        this.client = client;
        pieces = new JButton[5];

        for (int i = 0; i < 5; i++) {
            pieces[i] = new JButton();
        }

        this.setSize(100, 500);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        pieces[0].setText("PAWN");
        pieces[1].setText("ROOK");
        pieces[2].setText("KNIGHT");
        pieces[3].setText("BISHOP");
        pieces[4].setText("QUEEN");

        for (JButton b : pieces) {
            b.addActionListener(this);
            this.add(b);
        }
    }

    public int getChosePH() {
        return chose;
    }

    public int getChose() {
        return chose;
    }

    public void setChose(int chose) {
        this.chose = chose;
    }

    public boolean isDachonxong() {
        return dachonxong;
    }

    public void setDachonxong(boolean dachonxong) {
        this.dachonxong = dachonxong;
    }

    Piece p;

    ObjectOutputStream out;
    IChessController chessDelegate;
    BoardView b;
    public void setTT(Piece p, ObjectOutputStream out, IChessController chessDelegate, BoardView b) {
        this.p = p;
        this.out = out;
        this.chessDelegate = chessDelegate;
        this.b=b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 5; i++) {
            if (pieces[i] == e.getSource()) {
                System.out.println("DA CHON VI TRI + " + i);
                dachonxong = true;
                chose = i;

                Piece.Rank r = Piece.Rank.QUEEN;
                switch (chose) {
                    case 0:
                        p.setRank(Piece.Rank.PAWN);
                        if (p.getPlayer() == Piece.Player.WHITE) {
                            p.setImgFileName(ChessConstants.pawnWhiteFileName);
                        } else if (p.getPlayer() == Piece.Player.BLACK) {
                            p.setImgFileName(ChessConstants.pawnBlackFileName);
                        }
                        r = Piece.Rank.PAWN;
                        break;
                    case 1:
                        p.setRank(Piece.Rank.ROOK);
                        if (p.getPlayer() == Piece.Player.WHITE) {
                            p.setImgFileName(ChessConstants.rookWhiteFileName);
                        } else if (p.getPlayer() == Piece.Player.BLACK) {
                            p.setImgFileName(ChessConstants.rookBlackFileName);
                        }
                        r = Piece.Rank.ROOK;
                        break;
                    case 2:
                        p.setRank(Piece.Rank.KNIGHT);
                        if (p.getPlayer() == Piece.Player.WHITE) {
                            p.setImgFileName(ChessConstants.knightWhiteFileName);
                        } else if (p.getPlayer() == Piece.Player.BLACK) {
                            p.setImgFileName(ChessConstants.knightBlackFileName);
                        }
                        r = Piece.Rank.KNIGHT;
                        break;
                    case 3:
                        p.setRank(Piece.Rank.BISHOP);
                        if (p.getPlayer() == Piece.Player.WHITE) {
                            p.setImgFileName(ChessConstants.bishopWhiteFileName);
                        } else if (p.getPlayer() == Piece.Player.BLACK) {
                            p.setImgFileName(ChessConstants.bishopBlackFileName);
                        }
                        r = Piece.Rank.BISHOP;
                        break;
                    case 4:
                        p.setRank(Piece.Rank.QUEEN);
                        if (p.getPlayer() == Piece.Player.WHITE) {
                            p.setImgFileName(ChessConstants.queenWhiteFileName);
                        } else if (p.getPlayer() == Piece.Player.BLACK) {
                            p.setImgFileName(ChessConstants.queenBlackFileName);
                        }
                        r = Piece.Rank.QUEEN;
                        break;
                }
                client.setFalseDachonXong();
                //gui du lieu da phong hau cho ben client kia biet
                System.out.println("PHONG QUAN + " + r);
                Request q = new Request(Request.TYPE.PHONGHAU, 0, 0, p.getColum(), p.getRow(), r);

                try {
                    out.writeObject(q);
                } catch (IOException ex) {
                    Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
                }

                client.NoneDisplayPhongHau();
                chessDelegate.setIsPhongHau(false);
                b.repaint();
                break;
            }
        }
        
        this.setVisible(false);
    }

}
