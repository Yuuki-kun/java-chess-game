/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessview;

import chessController.IChessController;
import chessmodel.ChessConstants;
import chessmodel.ChessModel;
import chessmodel.IChessModel;
import chessmodel.Piece;
import chessmodel.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author tongcongminh
 */
public class ServerResponse extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private BufferedReader out;
    private Request q;
    public int fC;
    public int fR;
    public int tC;
    public int tR;
    public IChessModel m;
    public ClientViews client;
//    public BoardView b;
    public IChessController controller;

    public ServerResponse(ClientViews client, Socket socket, ObjectInputStream in, IChessModel m, IChessController controller) {
        this.socket = socket;
        this.in = in;
        this.m = m;
        this.client = client;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            try {
//                if (in.ready()) {
//                String messFromServer = null;
//                messFromServer = (String) in.readObject();
//              
                if (q != null) {
                    q.init();
                }

                q = (Request) in.readObject();

                System.out.println(q);

                if (null != q.getType()) {
                    switch (q.getType()) {
                        case ASKNAME:
                            System.out.println("SERVER muon biet ten cua ban: ");
                            break;
                        case NAME:
                            break;
                        case CHAT:
                            client.addChat(q.getName());
                            break;
                        case OFFERDRAW:
                            offerdraw();
                            break;
                        case REFUSEDRAW:
                            JOptionPane.showMessageDialog(client.getFrame(), client.getOpponentName() + " refused your offer!");
                            break;
                        case DRAW:
                            draw();
                            controller.setIsPlaying(false);
                            client.setIsP(false);

                            break;
                        case OFFERREMACTH:
                            System.out.println("Offer rematch from " + client.getOpponentName());
                            offerRematch();
                            break;
                        case REFUSEREMATCH:
                            JOptionPane.showMessageDialog(client.getFrame(), client.getOpponentName() + " refused your offer!");
                            break;
                        case REMATCH:
                            rematch();
                            break;
                        case MOVE: {
                            try {
                                move(q.getfC(), q.getfR(), q.gettC(), q.gettR(), q.isMyKingChecked(), q.isOppKingChecked());
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ServerResponse.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (q.isOppKingChecked()) {
                            String position = controller.chuyenDoiViTri(q.gettC(), q.gettR(), controller.pieceAt(7 - q.gettC(), 7 - q.gettR()).getPlayer());
                            setMoveHis(String.valueOf(" " + controller.pieceAt(7 - q.gettC(), 7 - q.gettR()).getRank() + " " + position + "+"));

                        } else {
                            String position = controller.chuyenDoiViTri(q.gettC(), q.gettR(), controller.pieceAt(7 - q.gettC(), 7 - q.gettR()).getPlayer());
                            setMoveHis(String.valueOf(" " + controller.pieceAt(7 - q.gettC(), 7 - q.gettR()).getRank() + " " + position));
                        }

                        break;

                        case CHECKMATE: {
                            try {
                                move(q.getfC(), q.getfR(), q.gettC(), q.gettR(), q.isMyKingChecked(), q.isOppKingChecked());
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ServerResponse.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        controller.setIsPlaying(false);
                        client.setIsP(false);

                        controller.getMoveHist().removeAll(controller.getMoveHist());
                        JOptionPane.showMessageDialog(client.getFrame(), "Ban da thua!");
                        break;

                        case RESIGN:
                            controller.getMoveHist().removeAll(controller.getMoveHist());
                            controller.setIsPlaying(false);
                            client.setIsP(false);

                            JOptionPane.showMessageDialog(client.getFrame(), "Ban da thang!");
                            break;
                        case PHONGHAU:
                            Piece p = controller.pieceAt(7 - q.gettC(), 7 - q.gettR());
                            p.setRank(q.getR());
                            System.out.println("quan o vi tri " + (7 - q.gettC()) + ", " + (7 - q.gettR()) + " la quan " + q.getR() + " " + p.getPlayer());

                            switch (p.getRank()) {
                                case PAWN:
                                    if (p.getPlayer() == Piece.Player.WHITE) {
                                        p.setImgFileName(ChessConstants.pawnWhiteFileName);
                                    } else if (p.getPlayer() == Piece.Player.BLACK) {
                                        p.setImgFileName(ChessConstants.pawnBlackFileName);
                                    }
                                    break;
                                case ROOK:
                                    if (p.getPlayer() == Piece.Player.WHITE) {
                                        p.setImgFileName(ChessConstants.rookWhiteFileName);
                                    } else if (p.getPlayer() == Piece.Player.BLACK) {
                                        p.setImgFileName(ChessConstants.rookBlackFileName);
                                    }
                                    break;
                                case KNIGHT:
                                    if (p.getPlayer() == Piece.Player.WHITE) {
                                        p.setImgFileName(ChessConstants.knightWhiteFileName);
                                    } else if (p.getPlayer() == Piece.Player.BLACK) {
                                        p.setImgFileName(ChessConstants.knightBlackFileName);
                                    }
                                    break;
                                case BISHOP:
                                    if (p.getPlayer() == Piece.Player.WHITE) {
                                        p.setImgFileName(ChessConstants.bishopWhiteFileName);
                                    } else if (p.getPlayer() == Piece.Player.BLACK) {
                                        p.setImgFileName(ChessConstants.bishopBlackFileName);
                                    }
                                    break;
                                case QUEEN:
                                    if (p.getPlayer() == Piece.Player.WHITE) {
                                        p.setImgFileName(ChessConstants.queenWhiteFileName);
                                    } else if (p.getPlayer() == Piece.Player.BLACK) {
                                        p.setImgFileName(ChessConstants.queenBlackFileName);
                                    }
                                    break;
                            }
                            controller.repaintBoard();
                            break;
                        case PLAY:
                            //set name && opponent name
                            if (client.getClientName().equals(q.getName())) {
                                client.setOpponentName(q.getEnemyName());
                            } else if (client.getClientName().equals(q.getEnemyName())) {
                                client.setOpponentName(q.getName());
                            } else {
                                System.out.println("Set name error!?");
                            }
                            client.setIsplaying(true);
                            client.time();
                            System.out.println("Name = " + client.getClientName() + ", opponent = " + client.getOpponentName());
                            setTurn(q.getPlayer());
                            client.setTime(q.getTime());
                            client.setLabelClient();
                            client.setLabelOppoent();
                            client.setVi(true);
                            break;
                        case CHALLENGE:
                            challenge(q.getName(), q.getEnemyName(), q.getTime());
                            break;
                        case SHOWALL:
                            System.out.println("showallll");
                            controller.showAll(q);
                            break;
                        case MESSAGE:
                            System.out.println(q.getName());
                            break;
                        case QUIT:
                            JOptionPane.showMessageDialog(client.getFrame(), "Opponent give up. You WIN!");
                            controller.setIsPlaying(false);
                            client.setIsP(false);
                            break;
                        case DIRECT:
                            JOptionPane.showMessageDialog(null, "[SERVER]: " + q.getEnemyName() + " refulsed your challenge.");
                            break;
                        default:
                            break;

                    }
                }

            } catch (ClassNotFoundException ex) {

            } catch (IOException ex) {

            } finally {

            }
        }
    }

    //quan vua cua doi thu co bi chieu = isMyKingChecked
    //quan vua cua ta co bi chieu = isOppKingChecked
    public void move(int fC, int fR, int tC, int tR, boolean isMyKingChecked, boolean isOppKingChecked) throws InterruptedException {
        client.setIsP(true);
        fC = 7 - fC;
        fR = 7 - fR;
        tC = 7 - tC;
        tR = 7 - tR;

        int nfC = fC;
        int nfR = fR;
        int ntC = tC;
        int ntR = tR;
        int t = 0;

//        //fc=7, fr=5, tc=6, tr=4
//        if (controller.pieceAt(fC, fR) != null && controller.pieceAt(fC, fR).getRank() == Piece.Rank.BISHOP) {
//            while (true) {
//
//                //quan tuong di xuong ben phai
//                if (nfC < tC && nfR < tR) {
//
//                    if (nfC == tC - 1 && nfR == tR - 1) {
//                        fC = nfC;
//                        fR = nfR;
//                        break;
//                    }
//
//                    System.out.println("[SVRP]: fC=" + fC + ", fR=" + fR + ", tCol=" + tC + ", tRow=" + tR + "p = " + controller.pieceAt(nfC, nfR).getRank());
//                    controller.setPiece(nfC, nfR, nfC + 1, nfR + 1);
//
//                    nfC += 1;
//                    nfR += 1;
//                } //di xuong ben trai
//                else if (nfC > tC && nfR < tR) {
//
//                    if (nfC == tC + 1 && nfR == tR - 1) {
//                        fC = nfC;
//                        fR = nfR;
//                        break;
//                    }
//
//                    System.out.println("[SVRP]: fC=" + fC + ", fR=" + fR + ", tCol=" + tC + ", tRow=" + tR + "p = " + controller.pieceAt(nfC, nfR).getRank());
//                    controller.setPiece(nfC, nfR, nfC - 1, nfR + 1);
//
//                    nfC -= 1;
//                    nfR += 1;
//                }//tren ben trai
//                else if (nfC < tC && nfR > tR) {
//                    if (nfC == tC + 1 && nfR == tR + 1) {
//                        fC = nfC;
//                        fR = nfR;
//                        break;
//                    }
//
//                    System.out.println("[SVRP]: fC=" + fC + ", fR=" + fR + ", tCol=" + tC + ", tRow=" + tR + "p = " + controller.pieceAt(nfC, nfR).getRank());
//                    controller.setPiece(nfC, nfR, nfC - 1, nfR - 1);
//
//                    nfC -= 1;
//                    nfR -= 1;
//                }//tren ben phai
//                else if (nfC < tC && nfR > tR) {
//
//                    if (nfC == tC - 1 && nfR == tR + 1) {
//                        fC = nfC;
//                        fR = nfR;
//                        break;
//                    }
//
//                    System.out.println("[SVRP]: fC=" + fC + ", fR=" + fR + ", tCol=" + tC + ", tRow=" + tR + "p = " + controller.pieceAt(nfC, nfR).getRank());
//                    controller.setPiece(nfC, nfR, nfC + 1, nfR - 1);
//
//                    nfC += 1;
//                    nfR -= 1;
//                }
//                Thread.sleep(1000 / 60);
//
//            }
//
//        }else if(controller.pieceAt(fC, fR) != null && controller.pieceAt(fC, fR).getRank() == Piece.Rank.ROOK){
//            //tren, duoi, trai, phai
//        }
        //////////////////////////////////////////////////
        System.out.println("FCCC=" + fC + ", fR===" + fR);

        controller.isChecked(controller.pieceAt(fC, fR), isMyKingChecked, isOppKingChecked);

        System.out.println("[SVRP]: fC=" + fC + ", fR=" + fR + ", tCol=" + tC + ", tRow=" + tR);
        controller.setPiece(fC, fR, tC, tR);
        client.setTurn(true);
        //////////////////////////////////////////////////

        //chuyen doi va move
        //
    }

    public void setTurn(Piece.Player player) {
        client.setPlayer(player);
        if (player == Piece.Player.WHITE) {
            client.setTurn(true);
            controller.setIsPlaying(true);
            m.reset();
        } else if (player == Piece.Player.BLACK) {
            client.setTurn(false);
            controller.setIsPlaying(true);
            m.reSetReserve();
        }
        controller.repaintBoard();
    }

    private void challenge(String name, String enemyName, int time) throws IOException {
        int option = JOptionPane.showConfirmDialog(null, "CHALLENGE from " + name + " do you want to accept?", "Challenge", JOptionPane.YES_NO_OPTION);
        if (option == 0) {
            q.init();
            q.setType(Request.TYPE.ACCEPT);
            //huỳnh chấp nhận minh
            q.setName(name);
            client.getOut().writeObject(q);
        } else if (option == 1) {
//            q.init();
            q.setType(Request.TYPE.REFUSE);
            client.getOut().writeObject(q);

        }
    }

    public void setMoveHis(String move) {

        String moveHisCurrentIdx = new String();

        if (controller.getMoveHist().get(controller.getIdxMove()).equals("")) {
            moveHisCurrentIdx = "";
            moveHisCurrentIdx += move + "       ";
            controller.getMoveHist().add(controller.getIdxMove(), moveHisCurrentIdx);
            controller.setIdxMove();

        } else {
            moveHisCurrentIdx = controller.getMoveHist().get(controller.getIdxMove());
            moveHisCurrentIdx += "-----" + move + '\n';
            controller.getMoveHist().add(controller.getIdxMove(), moveHisCurrentIdx);
            controller.setIdxMove();
        }

        textMoveHis t = controller.getTextArea();
        String allHis = new String();
        int i = 0;
//        t.addMoveHisText(chessDelegate.getMoveHist().get(0));
        for (String str : controller.getMoveHist()) {
//            t.addMoveHisText(str + "\n");
            if (!str.equals("")) {
                i += 1;
                if (i % 2 == 1) {
                    allHis += (i / 2 + 1) + str;
                } else {
                    allHis += str;

                }
                if (i % 2 == 0) {
                    allHis += "\n";
                }
            }

        }

        if (allHis != null) {
            t.addMoveHisText(allHis);
        }

    }

    public void offerdraw() throws IOException {
        int choose = JOptionPane.showConfirmDialog(client.getFrame(), client.getOpponentName() + " offered a draw, "
                + "do you want to accept?", "offer draw", JOptionPane.YES_NO_OPTION);
        if (choose == 1) {
            q.init();
            q.setType(Request.TYPE.REFUSEDRAW);
            client.getOut().writeObject(q);
        } else if (choose == 0) {
            client.setIsplaying(false);
            q.init();
            q.setType(Request.TYPE.DRAW);
            client.getOut().writeObject(q);
            JOptionPane.showMessageDialog(client.getFrame(), "DRAW!");
            controller.setIsPlaying(false);
            
        }
    }

    public void draw() {
        JOptionPane.showMessageDialog(client.getFrame(), "DRAW!");
        controller.setIsPlaying(false);
    }

    public void offerRematch() throws IOException {
        int choose = JOptionPane.showConfirmDialog(null, client.getOpponentName() + " offered rematch, "
                + "do you want to accept?", "offer draw", JOptionPane.YES_NO_OPTION);
        if (choose == 1) {
            q.setType(Request.TYPE.REFUSEREMATCH);
            client.getOut().writeObject(q);
        } else if (choose == 0) {
            q.setType(Request.TYPE.REMATCH);
            client.getOut().writeObject(q);
            //dong y rematch => init lai toan bo moi thu
            controller.getTextArea().addMoveHisText("");
            setTurn(Piece.Player.WHITE);
//            client.setLabelClient();
//            client.setLabelOppoent();

            client.setIsplaying(true);
            client.setTime(client.getBaseTime());
            client.setLabelClient();
            client.setLabelOppoent();

        }
    }

    public void rematch() throws IOException {
//        JOptionPane.showMessageDialog(client.getFrame(), client.getOpponentName() + " accept your offer rematch", "rematch", JOptionPane.PLAIN_MESSAGE);
        controller.getTextArea().addMoveHisText("");
        setTurn(Piece.Player.BLACK);
        client.setIsplaying(true);
        client.setTime(client.getBaseTime());
        client.setLabelClient();
        client.setLabelOppoent();
        JOptionPane.showMessageDialog(client.getFrame(), client.getOpponentName() + " accepted your offer rematch", "rematch", JOptionPane.PLAIN_MESSAGE);

    }
}
