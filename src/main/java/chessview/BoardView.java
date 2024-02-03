/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessview;

import chessController.IChessController;
import chessmodel.ChessConstants;
import chessmodel.Piece;
import chessmodel.Request;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author tongcongminh
 */
public class BoardView extends JPanel implements MouseListener, MouseMotionListener {

    private IChessController chessDelegate;

    double scaleFactor = 0.9;
    int originX = -1;
    int originY = -1;
    int cellSize = -1;

    int fromCol = -1;
    int fromRow = -1;

    private Piece movingPiece;
    private Point movingPiecePoint;
    private int[][] hint = new int[8][8];

    private BufferedImage board;

    public String[] piecesName;
    Map<String, BufferedImage> keyNamePiece = new HashMap<String, BufferedImage>();

    public BoardView(IChessController chessDelegate) throws IOException {
//        this.board = ImageIO.read(new FileInputStream("res/board/board512-512 copy.png"));
//        if (chessDelegate.getPlayer() == Piece.Player.WHITE) {
//            this.board = ImageIO.read(new FileInputStream("res/board/board512-512-Nau-2-01.png"));
//        } else if (chessDelegate.getPlayer() == Piece.Player.BLACK) {
//            this.board = ImageIO.read(new FileInputStream("res/board/board512-512-nau-black-01.png"));
//        }
        this.chessDelegate = chessDelegate;

        piecesName = new String[]{
            "Bishop-black",
            "Bishop-white",
            "Knight-black",
            "Knight-white",
            "King-black",
            "King-white",
            "queen-black",
            "queen-white",
            "Pawn-black",
            "Pawn-white",
            "Rook-black",
            "Rook-white"
        };
        for (String pieceName : piecesName) {
            keyNamePiece.put(pieceName, loadImage(pieceName));
        }

        //init hint
        initHint();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void initHint() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                hint[i][j] = 0;
            }
        }
    }

    public void loadBoard(String name) {
        this.board = loadImage(name);
    }

    public BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new FileInputStream("res/pices-img/" + fileName + ".png"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        
        
        int smaller = Math.min(600, 572);
        cellSize = (int) (((double) smaller) * scaleFactor / 8);

        originX = (getSize().width - 8 * cellSize) / 2;
        originY = (getSize().height - 8 * cellSize) / 2;

        try {
            drawBoard(g2);
        } catch (IOException ex) {
            Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
        }

        drawAllPieces(g2);
    }
    boolean daloadBoard = false;

    public void drawBoard(Graphics2D g2) throws FileNotFoundException, IOException {
//        for (int j = 0; j < 4; j++) {
//            for (int i = 0; i < 4; i++) {
//
//                drawSquare(g2, i * 2, 2 * j, true);
//                drawSquare(g2, 1 + 2 * i, 1 + 2 * j, true);
//
//                //https://color.adobe.com/create/color-wheel/
//                drawSquare(g2, i * 2 + 1, 2 * j, false);
//                drawSquare(g2, 2 * i, 1 + 2 * j, false);
//            }
//        }
//        BufferedImage board = ImageIO.read(new FileInputStream("res/board/board512-512 copy.png"));

        System.out.println("DRAW BOARDDDDDDDDDDDDDD = " + chessDelegate.getPlayer());
        if (chessDelegate.getPlayer() != null) {
            if (chessDelegate.getPlayer() == Piece.Player.WHITE && !daloadBoard) {

                board = ImageIO.read(new FileInputStream("res/board/board512-512-Nau-2-01.png"));
                daloadBoard = true;
            } else if (chessDelegate.getPlayer() == Piece.Player.BLACK && !daloadBoard) {
                board = ImageIO.read(new FileInputStream("res/board/board512-512-nau-black-01.png"));
//                board = ImageIO.read(new FileInputStream("res/board/board512-512Black-01.png"));

                daloadBoard = true;

            }
            g2.drawImage(board, 0 + originX, 0 + originY, 512, 512, null);

        }

    }

    public void drawSquare(Graphics2D g2, int col, int row, boolean light) {
        g2.setColor(light ? Color.white : (Color.decode("#69B8FF")));
        g2.fillRect(originX + col * cellSize, originY + row * cellSize, cellSize, cellSize);
    }

    public void drawAllPieces(Graphics2D g2) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = chessDelegate.pieceAt(col, row);
                //dont draw the first position of moving piece
                if (p != null && p != movingPiece) {
                    drawPiece(g2, p.getColum(), p.getRow(), p.getImgFileName());
                    //cai nay chi hoat dong doi voi 1 ben?
//                    if (p.getRank() == Piece.Rank.KING) {
//                        boolean h = chessDelegate.isCheckd(p.getPlayer());
////                        System.out.println("CHECKKKKKKKK");
//                        if (h) {
//                            g2.setColor(new Color(255, 0, 0, 100));
//                            g2.fillRect(col * cellSize + originX, row * cellSize + originY, cellSize, cellSize);
//                        }
//                    }
                    //phien ban cai tien voi thuoc tinh isChecked 
                    if (p.getRank() == Piece.Rank.KING) {
                        if (p.isIsChecked()) {
                            g2.setColor(new Color(255, 0, 0, 100));
                            g2.fillRect(col * cellSize + originX, row * cellSize + originY, cellSize, cellSize);
                        }
                    }
                }
                if (hint[row][col] == 1) {
                    g2.setColor(new Color(154, 154, 154, 150));
                    g2.fillRect(row * cellSize + originX, col * cellSize + originY, cellSize, cellSize);
                }
                //vua co bi chieu ko? => to do neu bi chieu
            }
        }

        if (movingPiece != null && movingPiecePoint != null) {
            ///draw image when drag mouse
            BufferedImage img = keyNamePiece.get(movingPiece.getImgFileName());
            g2.drawImage(img, movingPiecePoint.x - cellSize / 2, movingPiecePoint.y - cellSize / 2, cellSize, cellSize, null);

        }
    }

    public void drawPiece(Graphics2D g2, int col, int row, String peaceName) {
//        BufferedImage peaceImg = loadImage(peaceName);
//        g2.drawImage(peaceImg, originX + col * cellSize, originY + row * cellSize, cellSize, cellSize, null);
        BufferedImage pieceImg = keyNamePiece.get(peaceName);
        g2.drawImage(pieceImg, originX + col * cellSize, originY + row * cellSize, cellSize, cellSize, null);

    }

    public void showHintPawn(Piece p) {
//        if (p.getMoved()) {
//            hint[p.getColum()][p.getRow() + 1] = 1;
//
//        } else {
//            hint[p.getColum()][p.getRow() + 1] = 1;
//            hint[p.getColum()][p.getRow() + 2] = 1;
//        }

        if (p.getMoved() && p.getRow() != 0 && p.getRow() != 7) {
            if (chessDelegate.pieceAt(p.getColum(), p.getRow() - 1) == null) {
                hint[p.getColum()][p.getRow() - 1] = 1;
            }
        } else {
            if (chessDelegate.pieceAt(p.getColum(), p.getRow() - 1) == null) {
                hint[p.getColum()][p.getRow() - 1] = 1;
            }

            if (chessDelegate.pieceAt(p.getColum(), p.getRow() - 2) == null) {
                hint[p.getColum()][p.getRow() - 2] = 1;
            }
        }

        int leftCapCol = p.getColum() - 1;
        int leftCapRow = p.getRow() - 1;
        int rightCapCol = p.getColum() + 1;
        int rightCapRow = p.getRow() - 1;

        if (leftCapCol >= 0 && leftCapRow < 8) {
            if (chessDelegate.pieceAt(leftCapCol, leftCapRow) != null && p.getPlayer() != chessDelegate.pieceAt(leftCapCol, leftCapRow).getPlayer()) {
                hint[leftCapCol][leftCapRow] = 1;
            }
        }
        if (rightCapCol < 8 && leftCapRow < 8) {
            if (chessDelegate.pieceAt(rightCapCol, rightCapRow) != null && p.getPlayer() != chessDelegate.pieceAt(rightCapCol, rightCapRow).getPlayer()) {
                hint[rightCapCol][rightCapRow] = 1;
            }
        }
    }

    public void showHintRook(Piece p) {
        Piece pieceLeftSide = null;
        Piece pieceRightSide = null;
        Piece pieceTopSide = null;
        Piece pieceBottomSide = null;

        /*xet ve ben trai cua xe:
            +neu ben trai co quan?
               -> khong ve phia sau quan do
         */
        //tu ben trai nhat qua vi tri hien tai cua xe
        for (int i = p.getColum() - 1; i >= 0; i--) {
            //neu gap phai 1 quan nao do
            pieceLeftSide = chessDelegate.pieceAt(i, p.getRow());
            if (pieceLeftSide != null) {
                if (pieceLeftSide.getPlayer() != p.getPlayer()) {
                    hint[i][p.getRow()] = 1;
                }

                break;
            }
            hint[i][p.getRow()] = 1;
        }

        //xet ve ben phai cua xe
        for (int i = p.getColum() + 1; i < 8; i++) {
            pieceRightSide = chessDelegate.pieceAt(i, p.getRow());
            if (pieceRightSide != null) {
                if (pieceRightSide.getPlayer() != p.getPlayer()) {
                    hint[i][p.getRow()] = 1;
                }
                break;
            }
            hint[i][p.getRow()] = 1;
        }

        //xet ve phia tren cua xe
        for (int i = p.getRow() - 1; i >= 0; i--) {
            pieceTopSide = chessDelegate.pieceAt(p.getColum(), i);
            if (pieceTopSide != null) {
                if (pieceTopSide.getPlayer() != p.getPlayer()) {
                    hint[p.getColum()][i] = 1;
                }
                break;
            }
            hint[p.getColum()][i] = 1;
        }

        //xet ve phia duoi cua xe
        for (int i = p.getRow() + 1; i < 8; i++) {
            pieceBottomSide = chessDelegate.pieceAt(p.getColum(), i);
            if (pieceBottomSide != null) {
                if (pieceBottomSide.getPlayer() != p.getPlayer()) {
                    hint[p.getColum()][i] = 1;
                }
                break;
            }
            hint[p.getColum()][i] = 1;
        }

    }

    public void showHintKnight(Piece p) {
        int row = p.getRow();
        int col = p.getColum();

        int[] dx = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dy = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int i = 0; i < 8; i++) {
            int newCol = col + dx[i];
            int newRow = row + dy[i];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                // Nếu ô đó chưa có quân cờ hoặc có quân cờ khác p trên đó
                if (chessDelegate.pieceAt(newCol, newRow) == null || chessDelegate.pieceAt(newCol, newRow).getPlayer() != p.getPlayer()) {
                    hint[newCol][newRow] = 1;
                    // Tô màu đỏ các ô được di chuyển

                }
            }
        }

    }

    public void showHintBishop(Piece p) {

        Piece pieceLeftTopSide = null;
        Piece pieceRighToptSide = null;
        Piece pieceLeftBottomSide = null;
        Piece pieceRightBottomSide = null;

        //xet ve duong cheo \ ben tren 
        int col = p.getColum();
        int row = p.getRow();
        while (col != 0 && row != 0) {
            col -= 1;
            row -= 1;

            pieceLeftTopSide = chessDelegate.pieceAt(col, row);
            if (pieceLeftTopSide != null) {
                if (pieceLeftTopSide.getPlayer() != p.getPlayer()) {
                    hint[col][row] = 1;
                }
                break;
            }
            hint[col][row] = 1;
        }
        //        //xet ve duong cheo \ ben tren  duoi
        col = p.getColum();
        row = p.getRow();
        while (row != 7 && col != 7) {
            col += 1;
            row += 1;

            pieceRightBottomSide = chessDelegate.pieceAt(col, row);
            if (pieceRightBottomSide != null) {
                if (pieceRightBottomSide.getPlayer() != p.getPlayer()) {
                    hint[col][row] = 1;
                }
                break;
            }
            hint[col][row] = 1;
        }

        //xet duong cheo tren ben phai /
        col = p.getColum();
        row = p.getRow();
        while (col != 7 && row != 0) {
            col += 1;
            row -= 1;

            pieceRighToptSide = chessDelegate.pieceAt(col, row);
            if (pieceRighToptSide != null) {
                if (pieceRighToptSide.getPlayer() != p.getPlayer()) {
                    hint[col][row] = 1;
                }
                break;
            }
            hint[col][row] = 1;
        }

        //xet duong cheo duoi ben trai /
        col = p.getColum();
        row = p.getRow();
        while (col != 0 && row != 7) {
            col -= 1;
            row += 1;

            pieceLeftBottomSide = chessDelegate.pieceAt(col, row);
            if (pieceLeftBottomSide != null) {
                if (pieceLeftBottomSide.getPlayer() != p.getPlayer()) {
                    hint[col][row] = 1;
                }
                break;
            }
            hint[col][row] = 1;
        }

    }

    public void showHintQueen(Piece p) {
        showHintBishop(p);
        showHintRook(p);
    }

    public void showHintKing(Piece p) {
        //xet ben tren, duoi, trai, phai
        int col = p.getColum();
        int row = p.getRow();

        Piece top = null;
        Piece bottom = null;
        Piece left = null;
        Piece right = null;

        Piece topLeft = null;
        Piece bottomLeft = null;
        Piece topRight = null;
        Piece bottomRight = null;

        top = chessDelegate.pieceAt(col, row - 1);
        bottom = chessDelegate.pieceAt(col, row + 1);
        left = chessDelegate.pieceAt(col - 1, row);
        right = chessDelegate.pieceAt(col + 1, row);

        topLeft = chessDelegate.pieceAt(col - 1, row - 1);
        bottomLeft = chessDelegate.pieceAt(col - 1, row + 1);
        topRight = chessDelegate.pieceAt(col + 1, row - 1);
        bottomRight = chessDelegate.pieceAt(col + 1, row + 1);

        if (top == null && row > 0 || (top != null && top.getPlayer() != p.getPlayer())) {
            if (chessDelegate.canMove(p, col, row, col, row - 1)) {
                hint[col][row - 1] = 1;
            }
        }
        if (bottom == null && row < 7 || (bottom != null && bottom.getPlayer() != p.getPlayer())) {
            if (chessDelegate.canMove(p, col, row, col, row + 1)) {
                hint[col][row + 1] = 1;
            }
        }
        if (left == null && col > 0 || (left != null && left.getPlayer() != p.getPlayer())) {
            if (chessDelegate.canMove(p, col, row, col - 1, row)) {
                hint[col - 1][row] = 1;
            }
        }
        if (right == null && col < 7 || (right != null && right.getPlayer() != p.getPlayer())) {
            if (chessDelegate.canMove(p, col, row, col + 1, row)) {
                hint[col + 1][row] = 1;
            }
        }

        if (topLeft == null && col > 0 && row > 0 || (topLeft != null && topLeft.getPlayer() != p.getPlayer())) {
            if (chessDelegate.canMove(p, col, row, col - 1, row - 1)) {
                hint[col - 1][row - 1] = 1;
            }
        }
        if (bottomLeft == null && col > 0 && row < 7 || (bottomLeft != null && bottomLeft.getPlayer() != p.getPlayer())) {
            if (chessDelegate.canMove(p, col, row, col - 1, row + 1)) {
                hint[col - 1][row + 1] = 1;
            }
        }
        if (topRight == null && col < 7 && row > 0 || (topRight != null && topRight.getPlayer() != p.getPlayer())) {
            if (chessDelegate.canMove(p, col, row, col + 1, row - 1)) {
                hint[col + 1][row - 1] = 1;
            }
        }
        if (bottomRight == null && col < 7 && row < 7 || (bottomRight != null && bottomRight.getPlayer() != p.getPlayer())) {
            if (chessDelegate.canMove(p, col, row, col + 1, row + 1)) {
                hint[col + 1][row + 1] = 1;
            }
        }

    }

    public void showHint(Piece p) {
        if (p != null) {
            switch (p.getRank()) {
                case PAWN:
                    showHintPawn(p);
                    break;
                case ROOK:
                    showHintRook(p);
                    break;
                case KNIGHT:
                    showHintKnight(p);
                    break;
                case BISHOP:
                    showHintBishop(p);
                    break;
                case QUEEN:
                    showHintQueen(p);
                    break;
                case KING:
                    showHintKing(p);
                    break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    //nhấn chuột
    @Override
    public void mousePressed(MouseEvent e) {

        if (chessDelegate.getPlayerTurn() == false) {
            return;
        }

        fromCol = (int) (e.getPoint().x - originX) / cellSize;
        fromRow = (int) (e.getPoint().y - originY) / cellSize;

        //show hint
        //piece is moved
        movingPiece = chessDelegate.pieceAt(fromCol, fromRow);

        if (movingPiece != null && movingPiece.getPlayer() != chessDelegate.getPlayer()) {
            return;
        }

        showHint(movingPiece);
//        System.out.println("mouse press: ");
        //        pieceClicked = !pieceClicked;
        //        if(pieceClicked){
        //            
        //        }
        //        if (isClicking) {
        //            int x = e.getPoint().x;
        //            int y = e.getPoint().y;
        //
        //            int toCol = (int) (x - originX) / cellSize;
        //            int toRow = (int) (y - originY) / cellSize;
        //            PrintWriter out = chessDelegate.getOut();
        //            out.println("MOVE " + fromCol + " " + fromRow + " " + toCol + " " + toRow);
        //            chessDelegate.movePiece(fromCol, fromRow, toCol, toRow);
        //            isClicking = false;
        //        } else {
        //            fromCol = (int) (e.getPoint().x - originX) / cellSize;
        //            fromRow = (int) (e.getPoint().y - originY) / cellSize;
        //            movingPiece = chessDelegate.pieceAt(fromCol, fromRow);
        //            if (movingPiece != null) {
        //                if (isClicking == false) {
        //                    isClicking = true;
        //                }
        //
        //            }
        //        }

    }

    //nhả chuột ra
    @Override
    public void mouseReleased(MouseEvent e) {

        if (chessDelegate.getPlayerTurn() == false) {
            return;
        }

        if (movingPiece != null && movingPiece.getPlayer() != chessDelegate.getPlayer()) {
            return;
        }

        System.out.println("move released!");
        int x = e.getPoint().x;
        int y = e.getPoint().y;

        int toCol = (int) (x - originX) / cellSize;
        int toRow = (int) (y - originY) / cellSize;
        System.out.println(" from col:" + fromCol + ", row" + fromRow + ", to col:" + toCol + ", row:" + toRow);

        //moving piece from a to b
        ObjectOutputStream out = chessDelegate.getOut();
//        out.println(fromCol);
//        out.println(fromRow);
//        out.println(toCol);
//        out.println(toRow);

        chessDelegate.movePiece(fromCol, fromRow, toCol, toRow, out);

        textMoveHis t = chessDelegate.getTextArea();
        String allHis = new String();
        int i = 0;
//        t.addMoveHisText(chessDelegate.getMoveHist().get(0));
        for (String str : chessDelegate.getMoveHist()) {
//            t.addMoveHisText(str + "\n");
            if (!str.equals("")) {
                i += 1;
                if (i % 2 == 1) {
                    //1->1
                    //3->2
                    //5->3
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

        if (chessDelegate.isPhongHau()) {
            //show jpanel phong hau len

            System.out.println("DISPLAY LABEL PHONG HAU!");

            chessDelegate.setDisplayPHLabel(0, 0);

            //phong hau cho quan o vi tri tocol torow
            Piece p = chessDelegate.pieceAt(toCol, toRow);

            //init phonghau
            chessDelegate.initPH(p, out, chessDelegate, this);

            //viec con lai de actionperform cua lop phong hau lo
        }

        if (chessDelegate.getIsMyWin()) {
            chessDelegate.setIsPlaying(false);
            chessDelegate.getMoveHist().removeAll(chessDelegate.getMoveHist());
            chessDelegate.setClientIsPlaying(false);
            JOptionPane.showMessageDialog(null, "Ben " + chessDelegate.getMyPlayer() + " da chien thang!");
//            clientView.getFrame().dispose();

        }

        chessDelegate.changeTurn();

//        chessDelegate.setPlayerTurn(!chessDelegate.getPlayerTurn());
//        out.println("MOVE " + fromCol + " " + fromRow + " " + toCol + " " + toRow);
        //send data to server
        //remove the last image of piece when drag
        movingPiece = null;
        initHint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (chessDelegate.getPlayerTurn() == false) {
            return;
        }

        if (movingPiece != null && movingPiece.getPlayer() != chessDelegate.getPlayer()) {
            return;
        }

        movingPiecePoint = e.getPoint();

//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
//        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
//whye(true){ //dem nguoc 30 Thread.sleep(1000) couenter -=1 
    //

}
