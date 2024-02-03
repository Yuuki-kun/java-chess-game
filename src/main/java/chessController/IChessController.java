/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chessController;

import chessmodel.Piece;
import chessmodel.Request;
import chessview.BoardView;
import chessview.ClientViews;
import chessview.clientView;
import chessview.textMoveHis;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author tongcongminh
 */
public interface IChessController {

    public void movePiece(int fromCol, int fromRow, int toCol, int toRow, ObjectOutputStream out);

    public Piece pieceAt(int col, int row);

    public void setPiece(int fC, int fR, int tC, int tR);

    public ObjectOutputStream getOut();

    public boolean canMove(Piece piece, int fromCol, int fromRow, int toCol, int toRow);

    public Piece.Player getPlayer();

    public boolean getPlayerTurn();

    public void setPlayerTurn(boolean isTurn);

    public void changeTurn();

    public void showAll(Request q);

    public void isChecked(Piece p, boolean isMyKingChecked, boolean isOppKingChecked);

    public boolean getIsMyWin();

    public Piece.Player getMyPlayer();

    public void setIsPhongHau(boolean h);

    public boolean isPhongHau();

    public void repaintBoard();

    public textMoveHis getTextArea();

    public ArrayList<String> getMoveHist();

    public int getIdxMove();

    public void setIdxMove();

    public String chuyenDoiViTri(int col, int rol, Piece.Player player);

    public void setIsPlaying(boolean h);

    public boolean getIsPlaying();

    public void stopServerResponse();

    public BoardView getB();

    public chessmodel.ChessModel getModel();

    public void setDisplayPHLabel(int x, int y);

    public void initPH(Piece p, ObjectOutputStream out, IChessController chessDelegate, BoardView b);

    public void setClient(ClientViews clientview);
    public void run(ClientViews clientview)throws IOException;
    public void setClientIsPlaying(boolean h);
}

