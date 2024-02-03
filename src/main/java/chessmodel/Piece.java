/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessmodel;

/**
 *
 * @author tongcongminh
 */
public class Piece {
    
    public enum Player{
        WHITE,
        BLACK
    }
    
    public enum Rank{
        KING,
        QUEEN,
        ROOK,
        KNIGHT,
        BISHOP,
        PAWN
    }
    
    private boolean isChecked = false;

    public boolean isIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    private int colum;
    private int row;
    private Player player;
    private Rank rank;
    private String imgFileName;
    private boolean moved;

    public Piece(int colum, int row, Player player, Rank rank, String imgFileName, boolean moved) {
        this.colum = colum;
        this.row = row;
        this.player = player;
        this.rank = rank;
        this.imgFileName = imgFileName;
        this.moved = moved;
    }

    public int getColum() {
        return colum;
    }

    public void setColum(int colum) {
        this.colum = colum;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }
    
    public boolean getMoved(){
        return this.moved;
    }

    public void setMoved(boolean ismoved){
        this.moved=ismoved;
    }
}
