/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessController;

import chessmodel.ChessModel;
import chessmodel.IChessModel;
import chessmodel.Piece;
import chessmodel.Request;
import chessview.BoardView;
import chessview.ClientViews;
import chessview.GameMode;
import chessview.MainMenu1;
import chessview.PlayOnline1;
import chessview.ServerResponse;
import chessview.clientView;
import chessview.textMoveHis;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author tongcongminh
 */
public class Controller implements IChessController, WindowListener {

    private ChessModel model = new ChessModel();
    private BoardView boardView;
    private ClientViews client;
    ServerResponse svrp;
    boolean isLoggin;

    public Controller(boolean isLoggin) throws IOException {
        this.isLoggin = isLoggin;
    }

//    }
    public void run(ClientViews clientview) throws IOException {

        setClient(clientview);
        startClientListeniner();
        runClient();
        displayGameMode();

    }

    public void initBoardView() throws IOException {
        boardView = new BoardView(this);

    }

    public void startClientListeniner() {
        svrp = new ServerResponse(client, client.getClient(), client.getIn(), model, this);
        svrp.start();
    }

    public void runClient() throws IOException {
        client.run();

    }

    public void displayGameMode() {

        GameMode gm = new GameMode(this.isLoggin, client.getClientName());
        gm.setController(this);
        gm.addWindowListener(this);
        gm.setVisible();

    }

    public void initClient(BoardView b, IChessController controller) throws IOException {
        client = new clientView(b, controller);
        client.initClientGUI();
    }

    public void setClient(ClientViews clientview) {
        this.client = clientview;
        try {
            client.initClientGUI();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stopServerResponse() {
        svrp.interrupt();
    }

    @Override
    public void movePiece(int fromCol, int fromRow, int toCol, int toRow, ObjectOutputStream out) {
        model.movePiece(fromCol, fromRow, toCol, toRow, out);
        boardView.repaint();
    }

    @Override
    public Piece pieceAt(int col, int row) {
        return model.pieceAt(col, row);
    }

    public ObjectOutputStream getsOut() {
        return client.getOut();
    }

    @Override
    public void setPiece(int fC, int fR, int tC, int tR) {
        Piece p = model.pieceAt(fC, fR);

        Piece target = pieceAt(tC, tR);

        if (target != null) {
            //if the target is your piece, do nothing
            if (p.getPlayer() == target.getPlayer()) {
                return;
            } else //capture this target
            {
                model.getPieceOnBoard().remove(target);
            }
        }

        p.setColum(tC);
        p.setRow(tR);
        boardView.repaint();
    }

    @Override
    public boolean canMove(Piece piece, int fromCol, int fromRow, int toCol, int toRow) {
        return model.canMove(piece, fromCol, fromRow, toCol, toRow);
    }

    @Override
    public Piece.Player getPlayer() {
        return client.getPlayer();
    }

    @Override
    public boolean getPlayerTurn() {
        return client.getTurn();
    }

    @Override
    public void setPlayerTurn(boolean isTurn) {
        client.setTurn(isTurn);
    }

    @Override
    public void changeTurn() {
        if (model.getIsplaying() == false) {
            client.setIsplaying(false);
        } else {
            client.setIsplaying(true);

        }
        boolean h = model.changeTurn();

        if (h) {
            setPlayerTurn(false);
        }
    }

    @Override
    public ObjectOutputStream getOut() {
        return client.getOut();
    }

    //gui yc
    public void showAllRequest() throws IOException {
        Request q = new Request(Request.TYPE.SHOWALL);
        System.out.println("DA GUI YEU CAU = " + q.getType());

        client.getOut().writeObject(q);

        System.out.println("DA WRITE OBJECT???");
    }

    //show
    @Override
    public void showAll(Request q) {
        System.out.println("SHOWALL???");
        PlayOnline1 p = new PlayOnline1(this);

        p.showPlayer(q.getListName());
        p.visible();
    }

    public void challenge(String name, int time) throws IOException {
        //thách đấu name => name = enemyName
        //A thach dau B
        Request q = new Request(Request.TYPE.CHALLENGE, client.getClientName(), name, time);

        client.getOut().writeObject(q);
    }

    //myKing la vua cua p => vi p vua moi di chuyen
    @Override
    public void isChecked(Piece p, boolean isMyKingChecked, boolean isOppKingChecked) {
        int soluongvua = 0;
        for (Piece piece : model.getPieceOnBoard()) {
            if (piece.getRank() == Piece.Rank.KING) {
                soluongvua += 1;
                if (piece.getPlayer() == p.getPlayer()) {
                    if (isMyKingChecked) {
                        piece.setIsChecked(true);
                    } else {
                        piece.setIsChecked(false);
                    }
                } else {
                    if (isOppKingChecked) {
                        //
                        piece.setIsChecked(true);
                    } else {
                        piece.setIsChecked(false);
                    }
                }
            }
            if (soluongvua == 2) {
                break;
            }
        }
    }

    @Override
    public boolean getIsMyWin() {
        return model.isMyWin();
    }

    @Override
    public Piece.Player getMyPlayer() {
        return model.getMyPlayer();
    }

    @Override
    public void setIsPhongHau(boolean h) {
        model.setIsPhongHau(h);
    }

    @Override
    public boolean isPhongHau() {
        return model.isIsPhongHau();
    }

    @Override
    public void repaintBoard() {
        boardView.repaint();
    }

    @Override
    public textMoveHis getTextArea() {
        return client.getHis();
    }

    @Override
    public ArrayList<String> getMoveHist() {
        return model.getHisMove();
    }

    @Override
    public int getIdxMove() {
        return model.getIdxMoveHis();
    }

    @Override
    public void setIdxMove() {
        model.setIdxMoveHis(model.getIdxMoveHis() + 1);
    }

    @Override
    public String chuyenDoiViTri(int col, int rol, Piece.Player player) {
        return model.chuyenDoiToaDo(col, rol, player);
    }

    @Override
    public void setIsPlaying(boolean h) {
        model.setIsPlaying(h);
    }

    @Override
    public boolean getIsPlaying() {
        return model.isIsPlaying();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("DONG JFRAME");
        client.windowClosing(e);
//        this.stopServerResponse();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public BoardView getB() {
        return this.boardView;
    }

    @Override
    public ChessModel getModel() {
        return this.model;
    }

    @Override
    public void setDisplayPHLabel(int x, int y) {
        client.setDisplayPhongHauLabel(x, y);
    }

    @Override
    public void initPH(Piece p, ObjectOutputStream out, IChessController chessDelegate, BoardView b) {
        client.initPH(p, out, chessDelegate, b);
    }

    public void setTime(int t) {
        client.setTime(t);
    }

    @Override
    public void setClientIsPlaying(boolean h) {
        client.setIsplaying(h);
    }

}
