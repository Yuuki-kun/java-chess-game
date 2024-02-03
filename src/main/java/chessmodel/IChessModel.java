/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chessmodel;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author tongcongminh
 */
public interface IChessModel {

    public void reset();

    public void reSetReserve();

    public void movePiece(int fromCol, int fromRow, int toCol, int toRow, ObjectOutputStream out);

    public Piece pieceAt(int col, int row);

}
