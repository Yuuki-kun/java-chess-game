/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessmodel;

import java.io.Serializable;

/**
 *
 * @author tongcongminh
 */
public class Request implements Serializable {

    public enum TYPE {
        NAME,
        ASKNAME,
        MOVE,
        PLAY,
        CHALLENGE,
        MESSAGE,
        ACCEPT,
        REFUSE,
        SHOWALL,
        DIRECT,
        CHECKMATE,
        PHONGHAU,
        CHAT,
        OFFERDRAW,
        REFUSEDRAW,
        DRAW, 
        RESIGN,
        OFFERREMACTH,
        REFUSEREMATCH,
        REMATCH,
        QUIT
    }
    private TYPE type;
    private String name;
    private String enemyName;
    private Piece.Rank r;
    private boolean myKingChecked = false;
    private boolean oppKingChecked = false;
    private int time;
    private Piece.Player player;
    private String message;
    private int fC, fR, tC, tR;
    private String[] listName;

    public String[] getListName() {
        return listName;
    }

    public void setListName(String[] listName) {
        this.listName = listName;
    }

    public Request(TYPE type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public Request(TYPE type, String name, String enemyName) {
        this.type = type;
        this.name = name;
        this.enemyName = enemyName;
    }
    public Request(TYPE type, String name, String enemyName, int time) {
        this.type = type;
        this.name = name;
        this.enemyName = enemyName;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Request(TYPE type, String[] listName) {
        this.type = type;
        this.listName = listName;
    }

    public Request(TYPE type) {
        this.type = type;
    }

    public Request(TYPE type, Piece.Player player) {
        this.type = type;
        this.player = player;
    }

    public Piece.Player getPlayer() {
        return player;
    }

    public TYPE getType() {
        return this.type;
    }

    public String getName() {
        return name;
    }

    public Request(TYPE type, int fC, int fR, int tC, int tR, boolean myKingChecked, boolean oppKingChecked) {
        this.type = type;
        this.fC = fC;
        this.fR = fR;
        this.tC = tC;
        this.tR = tR;
        this.myKingChecked = myKingChecked;
        this.oppKingChecked = oppKingChecked;
    }
     public Request(TYPE type, int fC, int fR, int tC, int tR, Piece.Rank rank) {
        this.type = type;
        this.fC = fC;
        this.fR = fR;
        this.tC = tC;
        this.tR = tR;
        this.r = rank;
    }

    public Piece.Rank getR() {
        return r;
    }

    public void setR(Piece.Rank r) {
        this.r = r;
    }

    public boolean isMyKingChecked() {
        return myKingChecked;
    }

    public void setMyKingChecked(boolean myKingChecked) {
        this.myKingChecked = myKingChecked;
    }

    public boolean isOppKingChecked() {
        return oppKingChecked;
    }

    public void setOppKingChecked(boolean oppKingChecked) {
        this.oppKingChecked = oppKingChecked;
    }



    public void setType(TYPE type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setPlayer(Piece.Player player) {
        this.player = player;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setfC(int fC) {
        this.fC = fC;
    }

    public void setfR(int fR) {
        this.fR = fR;
    }

    public void settC(int tC) {
        this.tC = tC;
    }

    public void settR(int tR) {
        this.tR = tR;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getfC() {
        return fC;
    }

    public int getfR() {
        return fR;
    }

    public int gettC() {
        return tC;
    }

    public int gettR() {
        return tR;
    }

    public void init() {
        this.type = null;
        this.fC = -1;
        this.fR = -1;
        this.tC = -1;
        this.tR = -1;
        this.name = null;
        this.listName = null;
        this.player = null;
        this.message = null;
    }

}
