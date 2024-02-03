/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessmodel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tongcongminh
 */
public class ChessModel implements IChessModel {

    private int idxMoveHis = 0;
    private ArrayList<String> moveHis = new ArrayList<>();
    private Set<Piece> pieceOnBoard = new HashSet<>();
    private boolean isChecked = false;

    public boolean isIsChecked() {
        return isChecked;
    }

    private Piece.Player myPlayer;

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    private boolean isPlaying = true;

    //nhung quan dang chieu quan vua cua doi thu, co toi da 2 quan co the check quan vua cung mot luc
    private ArrayList<Piece> piecesCheckKing = new ArrayList<>();
    private boolean isPhongHau = false;

    //reset the board
    @Override
    public void reset() {

        this.idxMoveHis = 0;
        this.isChecked = false;
        this.isPhongHau = false;

        piecesCheckKing.removeAll(piecesCheckKing);

        this.myKingChecked = false;
        this.myWin = false;
        this.oppkKingChecked = false;
        this.piecesCheckKing.removeAll(this.piecesCheckKing);
        this.moveHis.removeAll(this.moveHis);

        this.quanCanChieu = null;
        this.vitricucuaquancanchieu = null;

        pieceOnBoard.removeAll(pieceOnBoard);

        String s = "";

        for (int i = 0; i < 100; i++) {
            this.moveHis.add(s);
        }

        /*add pieces and set their position 
        @parameter col, row, player, rank, file name*/
        for (int i = 0; i < 2; i++) {
            //rooks
            pieceOnBoard.add(new Piece(i * 7, 0, Piece.Player.BLACK, Piece.Rank.ROOK, ChessConstants.rookBlackFileName, false));
            pieceOnBoard.add(new Piece(i * 7, 7, Piece.Player.WHITE, Piece.Rank.ROOK, ChessConstants.rookWhiteFileName, false));

            //knights
            pieceOnBoard.add(new Piece(i * 5 + 1, 0, Piece.Player.BLACK, Piece.Rank.KNIGHT, ChessConstants.knightBlackFileName, false));
            pieceOnBoard.add(new Piece(i * 5 + 1, 7, Piece.Player.WHITE, Piece.Rank.KNIGHT, ChessConstants.knightWhiteFileName, false));

            //bishops
            pieceOnBoard.add(new Piece(i * 3 + 2, 0, Piece.Player.BLACK, Piece.Rank.BISHOP, ChessConstants.bishopBlackFileName, false));
            pieceOnBoard.add(new Piece(i * 3 + 2, 7, Piece.Player.WHITE, Piece.Rank.BISHOP, ChessConstants.bishopWhiteFileName, false));
        }

        //pawns
        for (int i = 0; i < 8; i++) {
            pieceOnBoard.add(new Piece(i, 1, Piece.Player.BLACK, Piece.Rank.PAWN, ChessConstants.pawnBlackFileName, false));
            pieceOnBoard.add(new Piece(i, 6, Piece.Player.WHITE, Piece.Rank.PAWN, ChessConstants.pawnWhiteFileName, false));
        }
        //kings and queens
        pieceOnBoard.add(new Piece(3, 0, Piece.Player.BLACK, Piece.Rank.QUEEN, ChessConstants.queenBlackFileName, false));
        pieceOnBoard.add(new Piece(3, 7, Piece.Player.WHITE, Piece.Rank.QUEEN, ChessConstants.queenWhiteFileName, false));

        pieceOnBoard.add(new Piece(4, 0, Piece.Player.BLACK, Piece.Rank.KING, ChessConstants.kingBlackFileName, false));
        pieceOnBoard.add(new Piece(4, 7, Piece.Player.WHITE, Piece.Rank.KING, ChessConstants.kingWhiteFileName, false));

    }

    //move piece from (col, row) to new (col, row)
    private static boolean movedPiece = false;
    private boolean myKingChecked = false, oppkKingChecked = false;
    private boolean myWin = false;

    boolean checkingMate = false;

    public boolean isCheckingMate() {
        return checkingMate;
    }

    public void setCheckingMate(boolean checkingMate) {
        this.checkingMate = checkingMate;
    }

    @Override
    public void movePiece(int fromCol, int fromRow, int toCol, int toRow, ObjectOutputStream out) {

        String moveHis = null;

        if (!isPlaying) {
            return;
        }

        System.out.println("Model: move " + fromCol + " " + fromRow + " " + toCol + " " + toRow);

        Piece piece = pieceAt(fromCol, fromRow);
        //my king
        Piece king = null;
        for (Piece pie : pieceOnBoard) {
            if (pie.getRank() == Piece.Rank.KING && piece != null && pie.getPlayer() == piece.getPlayer()) {
                king = pie;
            }
        }

        if (piece == null || !canMove(piece, fromCol, fromRow, toCol, toRow) || (toCol == fromCol && toRow == fromRow)) {
            movedPiece = false;
            return;
        }

        Piece target = pieceAt(toCol, toRow);

        //neu di toi do ma quan vua k bi chieu => OK
        if (target != null) {
            //if the target is your piece, do nothing
            if (piece.getPlayer() == target.getPlayer()) {
                movedPiece = false;
                return;
            } else //capture this target
            {

                pieceOnBoard.remove(target);
            }
        }

        //set new position 
        piece.setColum(toCol);
        piece.setRow(toRow);

        movedPiece = true;

        //di chuyen xong xem quan vua ben kia co bi chieu ko?
//        this.piecesCheckKing.removeAll(this.piecesCheckKing);
        this.piecesCheckKing = new ArrayList<>();
        System.out.println(this.piecesCheckKing);
        for (Piece pic : piecesCheckKing) {
            System.out.println("P=" + pic.getRank());
        }
        setKingCheck(piece.getPlayer(),false);

        //neu sau khi di chuyen quan ma vua minh bi chieu => ko cho di chuyen
        if (myKingChecked) {

            if (target != null) {
                pieceOnBoard.add(target);
            }

            piece.setColum(fromCol);
            piece.setRow(fromRow);
            setKingCheck(piece.getPlayer(),false);
            movedPiece = false;
            return;
        }
        ///////////////////////
        this.piecesCheckKing = new ArrayList<>();
        setKingCheck(piece.getPlayer(), true);

        //neu quan den dang bi chieu "oppkKingChecked = true"
        if (oppkKingChecked) {
            System.out.println("King doi phuong dang bi chieu!=> goi ham checkCheckedMate");
            boolean isCheckmate = checkCheckdMate(piece.getPlayer());
            if (isCheckmate) {
                isPlaying = false;
                //tao request va thong bao check mate
                setKingCheck(piece.getPlayer(),false);

                ///////////////////
                this.piecesCheckKing = new ArrayList<>();

                myWin = true;

                this.moveHis.removeAll(this.moveHis);

                Request q = new Request(Request.TYPE.CHECKMATE, fromCol, fromRow, toCol, toRow, myKingChecked, oppkKingChecked);
                try {
                    out.writeObject(q);
                } catch (IOException ex) {
                    Logger.getLogger(ChessModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("DA CHIEU HET!!!");
                return;
            }
        }
        setKingCheck(piece.getPlayer(),false);
        //phải di chuyển quân đó được thì mới gửi data
//        out.println("MOVE " + fromCol + " " + fromRow + " " + toCol + " " + toRow);
        myPlayer = piece.getPlayer();

        if (oppkKingChecked) {
            String position = chuyenDoiToaDo(toCol, toRow, piece.getPlayer());
            moveHis = String.valueOf(" " + piece.getRank() + position + "+");
        } else {
            String position = chuyenDoiToaDo(toCol, toRow, piece.getPlayer());
            moveHis = String.valueOf(" " + piece.getRank() + " " + position);
        }

//        this.moveHis.add(moveHis);
//        idxMoveHis+=1;
        String moveHisCurrentIdx = new String();

        if (this.moveHis.get(idxMoveHis).equals("")) {
            moveHisCurrentIdx = "";
            moveHisCurrentIdx += moveHis + "      ";
            this.moveHis.add(idxMoveHis, moveHisCurrentIdx);
            idxMoveHis += 1;

        } else {
            moveHisCurrentIdx = this.moveHis.get(idxMoveHis);
            moveHisCurrentIdx += "-----" + moveHis + "\n";
            this.moveHis.add(idxMoveHis, moveHisCurrentIdx);
            idxMoveHis += 1;
        }

        //kiem tra quan vua di co phai la quan tot va no co di toi hang cao nhat tren ban co khong?
        if (piece.getRank() == Piece.Rank.PAWN && piece.getRow() == 0) {
            //neu la phai thi du dieu kien phong cap cho no
            isPhongHau = true;
        }

        Request q = new Request(Request.TYPE.MOVE, fromCol, fromRow, toCol, toRow, myKingChecked, oppkKingChecked);
        myPlayer = piece.getPlayer();

        try {
            out.writeObject(q);
        } catch (IOException ex) {
            Logger.getLogger(ChessModel.class.getName()).log(Level.SEVERE, null, ex);
        }

//        piecesCheckKing.removeAll(piecesCheckKing);
        this.piecesCheckKing = new ArrayList<>();

    }

    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public String chuyenDoiToaDo(int col, int row, Piece.Player player) {
        String result = "";
        if (player == Piece.Player.WHITE) {
            switch (col) {
                case 0:
                    result = String.valueOf("a" + (8 - row));
                    break;
                case 1:
                    result = String.valueOf("b" + (8 - row));
                    break;
                case 2:
                    result = String.valueOf("c" + (8 - row));
                    break;
                case 3:
                    result = String.valueOf("d" + (8 - row));
                    break;
                case 4:
                    result = String.valueOf("e" + (8 - row));
                    break;
                case 5:
                    result = String.valueOf("f" + (8 - row));
                    break;
                case 6:
                    result = String.valueOf("g" + (8 - row));
                    break;
                case 7:
                    result = String.valueOf("h" + (8 - row));
                    break;
            }

        } else {
            switch (col) {

                case 0:
                    result = String.valueOf("h" + (row + 1));
                    break;
                case 1:
                    result = String.valueOf("g" + (row + 1));
                    break;
                case 2:
                    result = String.valueOf("f" + (row + 1));
                    break;
                case 3:
                    result = String.valueOf("e" + (row + 1));
                    break;
                case 4:
                    result = String.valueOf("d" + (row + 1));
                    break;
                case 5:
                    result = String.valueOf("c" + (row + 1));
                    break;
                case 6:
                    result = String.valueOf("b" + (row + 1));
                    break;
                case 7:
                    result = String.valueOf("a" + (row + 1));
                    break;
            }
        }
        return result;
    }

    public int getIdxMoveHis() {
        return idxMoveHis;
    }

    public void setIdxMoveHis(int idxMoveHis) {
        this.idxMoveHis = idxMoveHis;
    }

    public boolean isIsPhongHau() {
        return isPhongHau;
    }

    public void setIsPhongHau(boolean isPhongHau) {
        this.isPhongHau = isPhongHau;
    }

    public Piece.Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Piece.Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public boolean isMyWin() {
        return myWin;
    }

    public void setMyWin(boolean myWin) {
        this.myWin = myWin;
    }
    Piece quanCanChieu;
    Piece vitricucuaquancanchieu;

    public boolean checkCheckdMate(Piece.Player myPlayer) {
        //kiem tra trong pham vi di chuyen cua vua doi phuong xem no con kha nang di chuyen khong?
        ArrayList<Piece> OpponentPiece = new ArrayList<>();
        //tim vi tri quan vua
        Piece kingOpp = null;
        for (Piece piece : pieceOnBoard) {
            if (piece.getRank() == Piece.Rank.KING && piece.getPlayer() != myPlayer) {
                kingOpp = piece;
//                break;
            }
            if (piece.getRank() != Piece.Rank.KING && piece.getPlayer() != myPlayer) {
                //day la quan cua doi thu va no k phai la quan vua
                OpponentPiece.add(piece);
            }
        }

        //xet xem quan vua co the di chuyen duoc nua khong?
        //vi tri hien tai cua quan vua
        int kingCol = kingOpp.getColum(), kingRow = kingOpp.getRow();

        System.out.println("vi tri quan vua doi thu dang bi chieu la:" + kingCol + ", " + kingRow);

        //vi tri phia tren quan vua
        int topCol = kingCol, topRow = kingRow - 1;
        //vi tri phia duoi quan vua
        int bottomCol = kingCol, bottomRow = kingRow + 1;
        //vi tri ben trai quan vua
        int leftCol = kingCol - 1, leftRow = kingRow;
        //vi tri ben phai vua
        int rightCol = kingCol + 1, rightRow = kingRow;

        int topLCol = kingCol - 1, topLRow = kingRow - 1;
        //vi tri phia duoi quan vua
        int bottomLCol = kingCol - 1, bottomLRow = kingRow + 1;
        //vi tri ben trai quan vua
        int topRCol = kingCol + 1, topRRow = kingRow - 1;
        //vi tri ben phai vua
        int bottomRcol = kingCol + 1, bottomRRow = kingRow + 1;

        boolean canMoveKing = false;
        //xet xem quan vua co the di den cac vi tri do khong? neu co the -> van chua mate -> return fasle 
        if (topRow >= 0) {
            canMoveKing = canMove(kingOpp, kingCol, kingRow, topCol, topRow);
            if (canMoveKing) {
//                setKingCheck(myPlayer);
                System.out.println("quan vua van co the di chuyen sang o " + topCol + ", " + topRow);
//                return false;
                canMoveKing = true;
            }
        }
        if (bottomRow < 8) {
            System.out.println("co goi ham nay ma?");
            canMoveKing = canMove(kingOpp, kingCol, kingRow, bottomCol, bottomRow);
            System.out.println("King doi thu can move " + kingCol + ", " + kingRow + " to " + bottomCol + ", " + bottomRow + " ? = " + canMoveKing);
            if (canMoveKing) {
                System.out.println("quan vua van co the di chuyen sang o " + bottomCol + ", " + bottomRow);
//                setKingCheck(myPlayer);
                return false;
            }
        }
        if (leftCol >= 0) {
            canMoveKing = canMove(kingOpp, kingCol, kingRow, leftCol, leftRow);
            if (canMoveKing) {
//                setKingCheck(myPlayer);
                System.out.println("quan vua van co the di chuyen sang o " + leftCol + ", " + leftRow);

                return false;
            }
        }
        if (rightCol < 8) {
            canMoveKing = canMove(kingOpp, kingCol, kingRow, rightCol, rightRow);
            if (canMoveKing) {
                System.out.println("quan vua van co the di chuyen sang o " + rightCol + ", " + rightRow);
//                setKingCheck(myPlayer);
                return false;
            }
        }
        if (topLCol >= 0 && topLRow >= 0) {
            canMoveKing = canMove(kingOpp, kingCol, kingRow, topLCol, topLRow);
            if (canMoveKing) {
                System.out.println("quan vua van co the di chuyen sang o " + topLCol + ", " + topLRow);
//                setKingCheck(myPlayer);
                return false;
            }
        }

        if (bottomLCol >= 0 && bottomLRow < 8) {
            canMoveKing = canMove(kingOpp, kingCol, kingRow, bottomLCol, bottomLRow);
            if (canMoveKing) {
                System.out.println("quan vua van co the di chuyen sang o " + bottomLCol + ", " + bottomLRow);
//                setKingCheck(myPlayer);
                return false;
            }
        }

        if (bottomRcol < 8 && bottomRRow < 8) {
            canMoveKing = canMove(kingOpp, kingCol, kingRow, bottomRcol, bottomRRow);
            if (canMoveKing) {
                System.out.println("quan vua van co the di chuyen sang o " + bottomRcol + ", " + bottomRRow);
//                setKingCheck(myPlayer);
                return false;
            }
        }

        if (topRCol < 8 && topRRow >= 0) {
            canMoveKing = canMove(kingOpp, kingCol, kingRow, topRCol, topRRow);
            if (canMoveKing) {
                System.out.println("quan vua van co the di chuyen sang o " + topRCol + ", " + topRRow);
//              setKingCheck(myPlayer);
                return false;

            }
        }

//        if (canMoveKing) {
//            return false;
//        }
        boolean[] cothecanchieu = new boolean[piecesCheckKing.size()];
        System.out.println("size cua so cac quan dang chieu = " + piecesCheckKing.size());
        int i = 0;

        for (Piece pic : this.piecesCheckKing) {
            System.out.println("P=" + pic.getRank());
        }

        for (Piece pie : piecesCheckKing) {
            System.out.println("QUAN DANG CHECK QUAN VUA LA: " + pie.getRank());
            int checkerCol = pie.getColum();
            int checkerRow = pie.getRow();
            //mang chua cac o nam trong pham vi tan cong
//            int[][] attackedPoint = new int[8][8];
            //xet xem trong pham vi tan cong cua cac quan nay, co quan nao di chuyen toi che duoc khong?
            switch (pie.getRank()) {
                case BISHOP:
                    //tim xem dang chieu theo duong cheo nao?
                    //co quan nao ben den co the can chieu quan tuong?
                    cothecanchieu[i] = coCanChieuQuanTuong(kingCol, kingRow, checkerCol, checkerRow, OpponentPiece);
                    break;
                case ROOK:
                    //co quan nao ben den co the can chieu xe?
                    //vua nam ben trai cua xe
                    cothecanchieu[i] = coCanChieuDuocQuanXe(kingCol, kingRow, checkerCol, checkerRow, OpponentPiece);
                    break;
                case QUEEN:
                    //co quan nao co the can chieu hau?
                    cothecanchieu[i] = coCanChieuQuanTuong(kingCol, kingRow, checkerCol, checkerRow, OpponentPiece) || coCanChieuDuocQuanXe(kingCol, kingRow, checkerCol, checkerRow, OpponentPiece);
                    break;
                case PAWN:
                    //xet xem co quan nao an duoc quan tot khong?
                    for (Piece pi : OpponentPiece) {
                        if (canMove(pi, pi.getColum(), pi.getRow(), checkerCol, checkerRow)) {
                            cothecanchieu[i] = true;
                        }
                    }
                    break;
                case KNIGHT:
                    //xet xem co quan nao an duoc quan ma khong?
                    for (Piece pi : OpponentPiece) {
                        if (canMove(pi, pi.getColum(), pi.getRow(), checkerCol, checkerRow)) {
                            cothecanchieu[i] = true;
                        }
                    }
                    break;
            }
            i++;

        }

        for (boolean co : cothecanchieu) {
            //
            if (!co) {
                return true;
            } else {
                System.out.println("Co the can  chieu!");
            }
        }

        //neu quan vua khong the di chuyen den cac o do duoc nua
        return false;
    }

    public boolean coCanChieuQuanTuong(int kingCol, int kingRow, int checkerCol, int checkerRow, ArrayList<Piece> OpponentPiece) {
        //vua nam tren duong cheo ben  trai
        if (kingCol < checkerCol && kingRow < checkerRow) {
            int attackedCol = kingCol, attackedRow = kingRow;
            while (true) {
                attackedCol += 1;
                attackedRow += 1;

//                            attackedPoint[attackedCol][attackedRow]=1;
                //neu co quan co the di den o do thi chua mate
                for (Piece pi : OpponentPiece) {
                    if (canMove(pi, pi.getColum(), pi.getRow(), attackedCol, attackedRow)) {

                        return true;
                    }
                }
                if (attackedCol == checkerCol || attackedRow == checkerRow) {
                    break;
                }

            }
//                        for(int i=0; i<8; i++){
//                            for(int j=0; j<8; j++){
//                                if(attackedPoint[i][j]==1){
//                                    
//                                    //tim xem trong ban co co quan nao di toi duoc o i, j ko?
//                                    //neu co => chua mate
//                                    
//                                }
//                            }
//                        }
        }//quan vua o phia tren ben phai cua quan tuong dang chieu
        else if (kingCol > checkerCol && kingRow < checkerRow) {
            int attackedCol = checkerCol, attackedRow = checkerRow;
            while (true) {

                for (Piece pi : OpponentPiece) {
                    if (canMove(pi, pi.getColum(), pi.getRow(), attackedCol, attackedRow)) {

                        return true;
                    }
                }

                attackedCol += 1;
                attackedRow -= 1;

                if (attackedCol == kingCol || attackedRow == kingRow) {
                    break;
                }

            }
        }//duoi ben trai
        else if (kingCol < checkerCol && kingRow > checkerRow) {
            int attackedCol = kingCol, attackedRow = kingRow;
            while (true) {
                attackedCol -= 1;
                attackedRow += 1;

                for (Piece pi : OpponentPiece) {
                    if (canMove(pi, pi.getColum(), pi.getRow(), attackedCol, attackedRow)) {

                        return true;
                    }
                }
                if (attackedCol == checkerCol || attackedRow == checkerRow) {
                    break;
                }
            }
        }//duoi ben phai
        else if (kingCol > checkerCol && kingRow > checkerRow) {
            int attackedCol = checkerCol, attackedRow = checkerRow;
            while (true) {

                for (Piece pi : OpponentPiece) {
                    if (canMove(pi, pi.getColum(), pi.getRow(), attackedCol, attackedRow)) {

                        return true;
                    }
                }

                attackedCol += 1;
                attackedRow += 1;

                if (attackedCol == kingCol || attackedRow == kingRow) {
                    break;
                }

            }
        }
        return false;
    }

    public boolean coCanChieuDuocQuanXe(int kingCol, int kingRow, int checkerCol, int checkerRow, ArrayList<Piece> OpponentPiece) {
        //quan vua nam ben trai quan xe
        if (kingCol < checkerCol) {
            int attackedCol = kingCol;
            while (true) {
                attackedCol += 1;

                for (Piece pi : OpponentPiece) {
                    if (canMove(pi, pi.getColum(), pi.getRow(), attackedCol, checkerRow)) {

                        return true;
                    }
                }
                if (attackedCol == checkerCol) {
                    break;
                }
            }
        }//vua nam ben phai xe
        else if (kingCol > checkerCol) {
            int attackedCol = checkerCol;
            while (true) {
                for (Piece pi : OpponentPiece) {
                    if (canMove(pi, pi.getColum(), pi.getRow(), attackedCol, checkerRow)) {

                        return true;
                    }
                }
                attackedCol += 1;
                if (attackedCol == kingCol) {
                    break;
                }

            }
        }//vua nam tren xe
        else if (kingRow < checkerRow) {
            int attackedRow = kingRow;
            while (true) {
                attackedRow += 1;

                for (Piece pi : OpponentPiece) {
                    if (canMove(pi, pi.getColum(), pi.getRow(), checkerCol, attackedRow)) {

                        return true;
                    }
                }
                if (attackedRow == checkerRow) {
                    break;
                }
            }
        }//vua nam duoi xe
        else if (kingRow > checkerRow) {
            int attackedRow = checkerRow;
            while (true) {
                for (Piece pi : OpponentPiece) {
                    if (canMove(pi, pi.getColum(), pi.getRow(), checkerCol, attackedRow)) {

                        return true;
                    }
                }
                attackedRow += 1;
                if (attackedRow == kingRow) {
                    break;
                }

            }
        }
        return false;
    }

    //co quan vua moi di chuyen de biet duong so sanh la nguoi choi den hay trang
    public void setKingCheck(Piece.Player myPlayer, boolean checkingMate) {
        int soQuanChieuCuaMy = 0;
        int soQuanChieuCuaOpp = 0;
        Piece myking = null, oppking = null;
        for (Piece piece : pieceOnBoard) {
            if (piece.getRank() == Piece.Rank.KING && piece.getPlayer() == myPlayer) {
                myking = piece;
            } else if (piece.getRank() == Piece.Rank.KING && piece.getPlayer() != myPlayer) {
                oppking = piece;
            }
            switch (piece.getRank()) {
                //dau tien la quan tot:
                case PAWN:
                    boolean paw = checkPawnChecked(piece, myPlayer);
                    if (paw == true) {
                        if (piece.getPlayer() == myPlayer) {
                            if (checkingMate) {
                                piecesCheckKing.add(piece);
                            }
                            oppkKingChecked = true;
                            soQuanChieuCuaOpp += 1;
                        } else {
                            soQuanChieuCuaMy += 1;
                            myKingChecked = true;
                        }
                    }
                    break;
                case ROOK:
                    boolean rook = checkRookChecked(piece);
                    if (rook == true) {
                        if (piece.getPlayer() == myPlayer) {
                            if (checkingMate) {
                                piecesCheckKing.add(piece);
                            }
                            oppkKingChecked = true;
                            soQuanChieuCuaOpp += 1;

                        } else {
                            soQuanChieuCuaMy += 1;
                            myKingChecked = true;
                        }
                    }
                    break;
                case BISHOP:
                    boolean bishop = checkBishopChecked(piece);
                    if (bishop == true) {
                        if (piece.getPlayer() == myPlayer) {
                            if (checkingMate) {
                                piecesCheckKing.add(piece);
                            }
                            oppkKingChecked = true;
                            soQuanChieuCuaOpp += 1;

                        } else {
                            soQuanChieuCuaMy += 1;
                            myKingChecked = true;
                        }
                    }
                    break;
                case QUEEN:
                    boolean queen = checkQueenChecked(piece);
                    if (queen == true) {
                        if (piece.getPlayer() == myPlayer) {
                            if (checkingMate) {
                                piecesCheckKing.add(piece);
                            }
                            oppkKingChecked = true;
                            soQuanChieuCuaOpp += 1;

                        } else {
                            soQuanChieuCuaMy += 1;
                            myKingChecked = true;
                        }
                    }
                    break;
                case KNIGHT:
                    boolean knight = checkKnightChecked(piece);
                    if (knight == true) {
                        if (piece.getPlayer() == myPlayer) {
                            piecesCheckKing.add(piece);
                            if (checkingMate) {
                                oppkKingChecked = true;
                            }
                            soQuanChieuCuaOpp += 1;

                        } else {
                            soQuanChieuCuaMy += 1;
                            myKingChecked = true;
                        }
                    }
                    break;
            }
        }
        oppkKingChecked = soQuanChieuCuaOpp > 0;

        myKingChecked = soQuanChieuCuaMy > 0;

        System.out.println("oppKINGCHECKED = " + oppkKingChecked);
        System.out.println("myKINGCHECKED = " + myKingChecked);

//        myking.setIsChecked(myKingChecked);
//        oppking.setIsChecked(oppkKingChecked);
        if (myking != null) {
            myking.setIsChecked(myKingChecked);
        }
        if (oppking != null) {
            oppking.setIsChecked(oppkKingChecked);
        }

    }

    public boolean checkPawnChecked(Piece piece, Piece.Player myPlayer) {
        int col = -1, row = -1, capCol1 = -1, capRow1 = -1, capCol2 = -1, capRow2 = -1;
        col = piece.getColum();
        row = piece.getRow();

        //xet tot tu duoi len
        if (piece.getPlayer() == myPlayer) {
            if (col == 0) {
                capCol2 = col + 1;
                capRow2 = row - 1;
            } else if (col == 7) {
                capCol1 = col - 1;
                capRow1 = row - 1;
            } else {
                capCol1 = col - 1;
                capRow1 = row - 1;
                capCol2 = col + 1;
                capRow2 = row - 1;
            }

        }//xet tot tu tren xuong
        else {
            if (col == 0) {
                capCol2 = col + 1;
                capRow2 = row + 1;
            } else if (col == 7) {
                capCol1 = col - 1;
                capRow1 = row + 1;
            } else {
                capCol1 = col - 1;
                capRow1 = row + 1;
                capCol2 = col + 1;
                capRow2 = row + 1;
            }
        }

        Piece p1 = null;
        Piece p2 = null;

        if (capCol1 != -1 && capRow1 != -1) {
            p1 = pieceAt(capCol1, capRow1);
            if (p1 != null && p1.getRank() == Piece.Rank.KING && p1.getPlayer() != piece.getPlayer()) {
                return true;
            }
        }

        if (capCol2 != -1 && capRow2 != -1) {
            p2 = pieceAt(capCol2, capRow2);
            if (p2 != null && p2.getRank() == Piece.Rank.KING && p2.getPlayer() != piece.getPlayer()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRookChecked(Piece piece) {
        //xet tren hang ngang, cot doc, co quan vua cua doi thu khong? 

        Piece pieceLeftSide = null;
        Piece pieceRightSide = null;
        Piece pieceTopSide = null;
        Piece pieceBottomSide = null;

        /*xet ve ben trai cua xe:
            +neu ben trai co quan?
               -> khong ve phia sau quan do
         */
        //tu ben trai nhat qua vi tri hien tai cua xe
        for (int i = piece.getColum() - 1; i >= 0; i--) {
            //neu gap phai 1 quan nao do
            pieceLeftSide = pieceAt(i, piece.getRow());
            if (pieceLeftSide != null && pieceLeftSide.getRank() == Piece.Rank.KING && pieceLeftSide.getPlayer() != piece.getPlayer()) {
                return true;
            } else if (pieceLeftSide != null && pieceLeftSide.getRank() != Piece.Rank.KING) {
                break;
            }
        }

        //xet ve ben phai cua xe
        for (int i = piece.getColum() + 1; i < 8; i++) {
            pieceRightSide = pieceAt(i, piece.getRow());
            if (pieceRightSide != null && pieceRightSide.getRank() == Piece.Rank.KING && pieceRightSide.getPlayer() != piece.getPlayer()) {
                return true;
            } else if (pieceRightSide != null && pieceRightSide.getRank() != Piece.Rank.KING) {
                break;
            }
        }

        //xet ve phia tren cua xe
        for (int i = piece.getRow() - 1; i >= 0; i--) {
            pieceTopSide = pieceAt(piece.getColum(), i);
            if (pieceTopSide != null && pieceTopSide.getRank() == Piece.Rank.KING && pieceTopSide.getPlayer() != piece.getPlayer()) {
                return true;
            } else if (pieceTopSide != null && pieceTopSide.getRank() != Piece.Rank.KING) {
                break;
            }
        }

        //xet ve phia duoi cua xe
        for (int i = piece.getRow() + 1; i < 8; i++) {
            pieceBottomSide = pieceAt(piece.getColum(), i);
            if (pieceBottomSide != null && pieceBottomSide.getRank() == Piece.Rank.KING && pieceBottomSide.getPlayer() != piece.getPlayer()) {
                return true;
            } else if (pieceBottomSide != null && pieceBottomSide.getRank() != Piece.Rank.KING) {
                break;
            }
        }
        return false;
    }

    public boolean checkBishopChecked(Piece piece) {
        //xet tren 2 duong cheo cua quan tuong xem co quan vua khong?
        //xet tren duong cheo chinh \

        int col = piece.getColum();
        int row = piece.getRow();
        Piece p = null;

        //   nua tren ben trai, hang giam cot giam
        while (col > 0 && row > 0) {
            col -= 1;
            row -= 1;
            p = pieceAt(col, row);
            if (p != null && p.getRank() == Piece.Rank.KING && p.getPlayer() != piece.getPlayer()) {
                return true;
            } else if (p != null && p.getRank() != Piece.Rank.KING) {
                break;
            }
        }

        col = piece.getColum();
        row = piece.getRow();
        //nua tren ben phai, hang giam cot tang
        while (col < 7 && row > 0) {
            col += 1;
            row -= 1;
            p = pieceAt(col, row);
            if (p != null && p.getRank() == Piece.Rank.KING && p.getPlayer() != piece.getPlayer()) {
                return true;
            } else if (p != null && p.getRank() != Piece.Rank.KING) {
                break;
            }
        }

        //nua duoi ben trai, cot giam hang tang
        col = piece.getColum();
        row = piece.getRow();
        while (col > 0 && row < 7) {
            col -= 1;
            row += 1;
            p = pieceAt(col, row);
            if (p != null && p.getRank() == Piece.Rank.KING && p.getPlayer() != piece.getPlayer()) {
                return true;
            } else if (p != null && p.getRank() != Piece.Rank.KING) {
                break;
            }
        }

        //nua duoi ben phai, cot tang hang tang
        col = piece.getColum();
        row = piece.getRow();
        while (col < 7 && row < 7) {
            col += 1;
            row += 1;
            p = pieceAt(col, row);
            if (p != null && p.getRank() == Piece.Rank.KING && p.getPlayer() != piece.getPlayer()) {
                return true;
            } else if (p != null && p.getRank() != Piece.Rank.KING) {
                break;
            }
        }

        return false;
    }

    public boolean checkQueenChecked(Piece piece) {
        boolean checkRookCheck = checkRookChecked(piece);
        boolean checkBishopCheck = checkBishopChecked(piece);
        return checkRookCheck || checkBishopCheck;
    }

    public boolean checkKnightChecked(Piece piece) {
        int row = piece.getRow();
        int col = piece.getColum();

        int[] dx = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dy = {-1, 1, -2, 2, -2, 2, -1, 1};

        Piece p = null;

        for (int i = 0; i < 8; i++) {
            int newCol = col + dx[i];
            int newRow = row + dy[i];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                p = pieceAt(newCol, newRow);
                if (p != null && p.getRank() == Piece.Rank.KING && p.getPlayer() != piece.getPlayer()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean changeTurn() {
        return ChessModel.movedPiece;
    }

    public Set<Piece> getPieceOnBoard() {
        return pieceOnBoard;
    }

    //returns piece at col & rol, return null if no piece found
    @Override
    public Piece pieceAt(int col, int row) {
        for (Piece piece : pieceOnBoard) {
            if (piece.getColum() == col && piece.getRow() == row) {
                return piece;
            }
        }
        return null;
    }

    public boolean canMove(Piece piece, int fCol, int fRow, int tCol, int tRow) {
        boolean canMoveTo = false;
        switch (piece.getRank()) {
            case PAWN:
                canMoveTo = checkPawn(fCol, fRow, tCol, tRow, piece);
                break;
            case ROOK:
                canMoveTo = checkRook(fCol, fRow, tCol, tRow);
                break;
            case BISHOP:
                canMoveTo = checkBishop(fCol, fRow, tCol, tRow);
                break;
            case KING:
                canMoveTo = checkKing(fCol, fRow, tCol, tRow);
                break;
            case QUEEN:
                canMoveTo = checkQueen(fCol, fRow, tCol, tRow);
                break;
            case KNIGHT:
                canMoveTo = checkKnight(fCol, fRow, tCol, tRow);
                break;
        }
        System.out.println("From " + fCol + ", " + fRow + ", " + "Can move? " + canMoveTo + ", " + tCol + ", " + tRow + ", player=" + piece.getPlayer() + ", piece = " + piece.getRank());
        return canMoveTo;
    }

    public boolean checkPawn(int fCol, int fRow, int tCol, int tRow, Piece piece) {
        boolean result = true;

        if (fCol == tCol && ((tRow == fRow - 1 || (tRow == fRow - 2 && !piece.getMoved())))) {
            result = true;
            Piece p = pieceAt(tCol, tRow);
            if (p != null) {
                return false;
            }

            piece.setMoved(true);
        }//nuoc an quan
        else if ((tCol == fCol - 1 && tRow == fRow - 1) || (tCol == fCol + 1 && tRow == fRow - 1)) {
            Piece p = pieceAt(tCol, tRow);
            return p != null && p.getPlayer() != myPlayer;
        } else {
            result = false;
        }
//            default: System.out.println("ERROR: CheckPawn");
//                    break;

        return result;
    }

    //check vi tri toi?
    //check tren duong di co quan can duong hay khong?
    public boolean checkRook(int fCol, int fRow, int tCol, int tRow) {
        //xet tu vi tri cua quan xe cho toi vi tri no di toi co quan nao hay khong?
        Piece p = null;
        //tren hang
        if (fRow == tRow) {
            //ben trai
            if (tCol < fCol) {
                for (int i = fCol - 1; i > tCol; i--) {
                    p = pieceAt(i, fRow);
                    if (p != null) {
                        return false;
                    }
                }
            } else {
                //ben phai
                for (int i = fCol + 1; i < tCol; i++) {
                    p = pieceAt(i, fRow);
                    if (p != null) {
                        return false;
                    }
                }
            }
            if (p == null) {
                return true;
            }
        }
        if (fCol == tCol) {
            //ben tren
            if (tRow < fRow) {
                for (int i = fRow - 1; i > tRow; i--) {
                    p = pieceAt(tCol, i);
                    if (p != null) {
                        return false;
                    }
                }
            } else {
                //ben duoi
                for (int i = fRow + 1; i < tRow; i++) {
                    p = pieceAt(tCol, i);
                    if (p != null) {
                        return false;
                    }
                }
            }
            if (p == null) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBishop(int fCol, int fRow, int tCol, int tRow) {
        int subCol = Math.abs(tCol - fCol);
        int subRow = Math.abs(tRow - fRow);

        boolean canmove = (subCol == subRow) && (subCol <= 7 && subRow <= 7); //check o di toi co nam tren duong cheo k

        //neu k == true
        //check trong khoang giua quan tuong va o no toi co quan nao ko? 
        Piece p = null;

        if (canmove) {
            //neu cot di toi nam ben trai (tCol < fCol) va o tren (tRow < fRow)
            //=> duong cheo tren ben trai => cot giam + hang giam
            int col = fCol;
            int row = fRow;
            if (tCol < fCol && tRow < fRow) {
                while (col > tCol) {
                    col -= 1;
                    row -= 1;
                    if (col != tCol && row != tRow) //                    System.out.println("");
                    {
                        p = pieceAt(col, row);
                    }
//                    System.out.println("Ua co cai quan +"+p.getRank());
                    if (p != null) {
                        return false;
                    }
                }
            } //xet duong cheo duoi ben trai
            else if (tCol < fCol && tRow > fRow) {
                while (col > tCol) {
                    col -= 1;
                    row += 1;
                    if (col != tCol && row != tRow) {
                        p = pieceAt(col, row);
                    }
                    if (p != null) {
                        return false;
                    }
                }
            } //xet duong cheo tren ben phai
            else if (tCol > fCol && tRow < fRow) {
                while (col < tCol) {
                    col += 1;
                    row -= 1;
                    if (col != tCol && row != tRow) {
                        p = pieceAt(col, row);
                    }
                    if (p != null) {
                        return false;
                    }
                }
            } else if (tCol > fCol && tRow > fRow) {
                while (col < tCol) {
                    col += 1;
                    row += 1;
                    if (col != tCol && row != tRow) {
                        p = pieceAt(col, row);
                    }
                    if (p != null) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
//        //

        return true;
    }

    public boolean checkKing(int fCol, int fRow, int tCol, int tRow) {
        //check quan vua dich bi tot minh chieu bi loi!
        int subCol = Math.abs(tCol - fCol);
        int subRow = Math.abs(tRow - fRow);

        //lay quan vua ra
        Piece p = pieceAt(fCol, fRow);
        Piece trmp = pieceAt(tCol, tRow);
        if (trmp != null && p.getPlayer() == trmp.getPlayer()) {
            return false;
        }
        //check xem co gap tuan tot khong?
        if (p.getPlayer() != myPlayer) {
            Piece p1 = pieceAt(tCol + 1, tRow + 1);
            if (p1 != null && p1.getRank() == Piece.Rank.PAWN && p1.getPlayer() != p.getPlayer()) {
                return false;
            }

            Piece p2 = pieceAt(tCol - 1, tRow + 1);
            if (p2 != null && p2.getRank() == Piece.Rank.PAWN && p2.getPlayer() != p.getPlayer()) {
                return false;
            }
        } else {
            Piece p1 = pieceAt(tCol - 1, tRow - 1);
            if (p1 != null && p1.getRank() == Piece.Rank.PAWN && p1.getPlayer() != p.getPlayer()) {
                return false;
            }

            Piece p2 = pieceAt(tCol + 1, tRow - 1);
            if (p2 != null && p2.getRank() == Piece.Rank.PAWN && p2.getPlayer() != p.getPlayer()) {
                return false;
            }
        }

        //neu nhu vi tri di chuyen toi khong nam trong pham vi tan cong cua quan tot
        if ((tCol == fCol && (tRow == fRow + 1 || tRow == fRow - 1)) || (tRow == fRow && (tCol == fCol + 1 || tCol == fCol - 1))
                || subCol == subRow && (subCol == 1 && subRow == 1)) {

            //gia su quan p di toi o tCol tRow
            //quan ban dau o vi tri tcol trow la:
            Piece temp = pieceAt(tCol, tRow);
            if (temp != null) {
                pieceOnBoard.remove(pieceAt(tCol, tRow));
            }
            p.setColum(tCol);
            p.setRow(tRow);

            System.out.println("O QUAN VUA DI CHUYEN TOI " + tCol + ", " + tRow + " KHONG NAM TRONG PHAM VI QUAN TOT");
            setKingCheck(myPlayer,false);
            System.out.println("QUAN " + p.getRank() + ", " + p.getPlayer() + " CO DANG BI CHIEU KHONG? = " + p.isIsChecked());
            System.out.println("checking: opp = " + oppkKingChecked + ", my = " + myKingChecked);
            if (oppkKingChecked && p.getPlayer() != myPlayer) {
                System.out.println("vi tri vua = " + fCol + ", " + fRow + " to " + tCol + ", " + tRow + "la quan = " + pieceAt(tCol, tRow));

                System.out.println("QUAN VUA CUA " + p.getRank() + ", " + p.getPlayer() + " DANG BI CHIEU???? NEU DI TOI O DO");
                //RETURN FALSE = DI TOI DO K DUOC?
                if (temp != null) {
                    pieceOnBoard.add(temp);
                }

                p.setColum(fCol);
                p.setRow(fRow);
                setKingCheck(myPlayer,false);
                return false;
            } else if (oppkKingChecked == false && p.getPlayer() != myPlayer) {
                p.setColum(fCol);
                p.setRow(fRow);
                if (temp != null) {
                    pieceOnBoard.add(temp);
                }
                setKingCheck(myPlayer,false);

                return true;
            } else if (myKingChecked && p.getPlayer() == myPlayer) {
                p.setColum(fCol);
                p.setRow(fRow);
                if (temp != null) {
                    pieceOnBoard.add(temp);
                }
                setKingCheck(myPlayer,false);
                return false;
            } else if (!myKingChecked && p.getPlayer() == myPlayer) {
                p.setColum(fCol);
                p.setRow(fRow);
                if (temp != null) {
                    pieceOnBoard.add(temp);
                }
                setKingCheck(myPlayer,false);
                return true;
            }

        }

        return false;
    }

    public boolean checkQueen(int fCol, int fRow, int tCol, int tRow) {
//        int subCol = Math.abs(tCol - fCol);
//        int subRow = Math.abs(tRow - fRow);
//        if (fCol == tCol || fRow == tRow || (subCol == subRow && (subCol <= 7 && subRow <= 7))) {
//            return true;
//        }
        return checkRook(fCol, fRow, tCol, tRow) || checkBishop(fCol, fRow, tCol, tRow);
    }

    public boolean checkKnight(int fCol, int fRow, int tCol, int tRow) {
        int subCol = Math.abs(tCol - fCol);
        int subRow = Math.abs(tRow - fRow);

        return ((subCol == 1 && subRow == 2) || (subCol == 2 && subRow == 1));
        // cho di chuyển;

    }

    public boolean checkRookCheck(Piece p) {
        //check tren hang + cot xem co quan vua cua doi phuong ko?
        int newCol = 0, newRow = 0;
        //tren hang
        newRow = p.getRow();
        Piece piece = null;
        while (newCol < 8) {
            piece = pieceAt(newCol, newRow);
            if (piece != null) {
                if (piece.getRank() == Piece.Rank.KING && piece.getPlayer() != p.getPlayer()) {
                    return true;
                }
            } else {
                newCol += 1;
            }
        }

        //tren cot
        newCol = p.getColum();
        newRow = 0;
        Piece piece2 = null;
        while (newRow < 8) {
            piece2 = pieceAt(newCol, newRow);
            if (piece2 != null) {
                if (piece2.getRank() == Piece.Rank.KING && piece2.getPlayer() != p.getPlayer()) {
                    return true;
                }
            } else {
                newRow += 1;
            }
        }
        return false;
    }

//    @Override
//    public String toString() {
//        String desc = "";
//        for (int row = 7; row >= 0; row--) {
//            desc += "" + row;
//            for (int col = 0; col < 8; col++) {
//                Piece p = pieceAt(col, row);
//                if (p == null) {
//                    desc += " .";
//                } else {
//                    desc += " ";
//                    switch (p.getRank()) {
//                        case KING:
//                            desc += p.getPlayer() == Piece.Player.WHITE ? "k" : "K";
//                            break;
//                        case QUEEN:
//                            desc += p.getPlayer() == Piece.Player.WHITE ? "q" : "Q";
//                            break;
//                        case ROOK:
//                            desc += p.getPlayer() == Piece.Player.WHITE ? "r" : "R";
//                            break;
//                        case KNIGHT:
//                            desc += p.getPlayer() == Piece.Player.WHITE ? "n" : "N";
//                            break;
//                        case BISHOP:
//                            desc += p.getPlayer() == Piece.Player.WHITE ? "b" : "B";
//                            break;
//                        case PAWN:
//                            desc += p.getPlayer() == Piece.Player.WHITE ? "p" : "P";
//                            break;
//                    }
//                }
//
//            }
//            desc += "\n";
//        }
//        desc += "  0 1 2 3 4 5 6 7";
//        return desc;
//    }
    @Override
    public void reSetReserve() {
        this.idxMoveHis = 0;
        this.isChecked = false;
        this.isPhongHau = false;

        this.myKingChecked = false;
        this.myWin = false;
        this.oppkKingChecked = false;
        this.piecesCheckKing.removeAll(this.piecesCheckKing);

        this.quanCanChieu = null;
        this.vitricucuaquancanchieu = null;

        pieceOnBoard.removeAll(pieceOnBoard);

        this.moveHis.removeAll(this.moveHis);

        String s = "";

        for (int i = 0; i < 100; i++) {
            this.moveHis.add(s);
        }


        /*add pieces and set their position 
        @parameter col, row, player, rank, file name*/
        for (int i = 0; i < 2; i++) {
            //rooks
            pieceOnBoard.add(new Piece(i * 7, 7, Piece.Player.BLACK, Piece.Rank.ROOK, ChessConstants.rookBlackFileName, false));
            pieceOnBoard.add(new Piece(i * 7, 0, Piece.Player.WHITE, Piece.Rank.ROOK, ChessConstants.rookWhiteFileName, false));

            //knights
            pieceOnBoard.add(new Piece(i * 5 + 1, 7, Piece.Player.BLACK, Piece.Rank.KNIGHT, ChessConstants.knightBlackFileName, false));
            pieceOnBoard.add(new Piece(i * 5 + 1, 0, Piece.Player.WHITE, Piece.Rank.KNIGHT, ChessConstants.knightWhiteFileName, false));

            //bishops
            pieceOnBoard.add(new Piece(i * 3 + 2, 7, Piece.Player.BLACK, Piece.Rank.BISHOP, ChessConstants.bishopBlackFileName, false));
            pieceOnBoard.add(new Piece(i * 3 + 2, 0, Piece.Player.WHITE, Piece.Rank.BISHOP, ChessConstants.bishopWhiteFileName, false));
        }

        //pawns
        for (int i = 0; i < 8; i++) {
            pieceOnBoard.add(new Piece(i, 6, Piece.Player.BLACK, Piece.Rank.PAWN, ChessConstants.pawnBlackFileName, false));
            pieceOnBoard.add(new Piece(i, 1, Piece.Player.WHITE, Piece.Rank.PAWN, ChessConstants.pawnWhiteFileName, false));
        }
        //kings and queens
        pieceOnBoard.add(new Piece(4, 7, Piece.Player.BLACK, Piece.Rank.QUEEN, ChessConstants.queenBlackFileName, false));
        pieceOnBoard.add(new Piece(4, 0, Piece.Player.WHITE, Piece.Rank.QUEEN, ChessConstants.queenWhiteFileName, false));

        pieceOnBoard.add(new Piece(3, 7, Piece.Player.BLACK, Piece.Rank.KING, ChessConstants.kingBlackFileName, false));
        pieceOnBoard.add(new Piece(3, 0, Piece.Player.WHITE, Piece.Rank.KING, ChessConstants.kingWhiteFileName, false));
    }

    public ArrayList<String> getHisMove() {
        return this.moveHis;
    }
    
    public boolean getIsplaying(){
        return this.isPlaying;
    }

}
