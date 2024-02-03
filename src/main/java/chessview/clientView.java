/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessview;

import chessController.Controller;
import chessController.IChessController;
import chessmodel.Piece;
import chessmodel.Request;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author tongcongminh
 * @comment: can cai tien giao dien cho dep mat
 */
public class clientView implements ActionListener, WindowListener, ClientViews {

    protected Socket client;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected boolean isP = false;
    protected Scanner sc = new Scanner(System.in);
    protected BoardView boardView;
    protected Piece.Player player;
    protected boolean isTurn;
    protected String clientName;
    protected JFrame frame;
    protected BufferedImage clientImg;
    protected String opponentName;
    protected BufferedImage enemyImg;
    IChessController controller;
    JPanel TopLeft;
    JPanel BottomLeft;
    JPanel CenterLeft;
    JPanel west;
    JLabel timeOutLabel;
    JLabel timeOutLabelOpp;
    protected ChatOnline chatOnline;
    protected textMoveHis his;
    JButton rematch = new JButton("Rematch");
    JButton offerDraw = new JButton("Draw");
    JButton resign = new JButton("Resign");

    private clientView() {
    }

    @Override
    public Piece.Player getPlayer() {
        return player;
    }

    public void setPlayer(Piece.Player player) {
        this.player = player;
    }

    public clientView(BoardView boardView, IChessController controller) throws IOException {

        this.boardView = boardView;
        this.controller = controller;

    }

    public void initClientGUI() throws IOException {
        frame = new JFrame();
        frame.setSize(1100, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(this);
        boardView.setBackground(Color.black);
        frame.add(boardView, BorderLayout.CENTER);

        JPanel north = new JPanel();
        north.setPreferredSize(new Dimension(1100, 50));
        north.setBackground(Color.lightGray);
        JLabel title = new JLabel("Chess Funn!");
        north.add(title);
        frame.add(north, BorderLayout.NORTH);

        JPanel south = new JPanel();
        south.setPreferredSize(new Dimension(1100, 50));
        south.setBackground(Color.lightGray);

        offerDraw.addActionListener(this);
        resign.addActionListener(this);
        rematch.addActionListener(this);

        south.add(offerDraw);
        south.add(resign);
        south.add(rematch);

        frame.add(south, BorderLayout.SOUTH);

        west = new JPanel();
        west.setPreferredSize(new Dimension(200, 600));
        frame.add(west, BorderLayout.WEST);

        JPanel est = new JPanel();
        est.setPreferredSize(new Dimension(300, 600));
        est.setBackground(Color.yellow);
        frame.add(est, BorderLayout.EAST);

        //set layout cho west
        west.setLayout(new BorderLayout());

        CenterLeft = new JPanel();

        //set size cho 2 panel

        CenterLeft.setPreferredSize(new Dimension(200, 100));

        CenterLeft.setBackground(Color.lightGray);
        //
        //add 2 panel vao west

        west.add(CenterLeft, BorderLayout.CENTER);

        //lich su cac nuoc di
        his = new textMoveHis();
        //khung chat online
        chatOnline = new ChatOnline(this);
        //tao nut gui tin nhan
        est.add(his);
        est.add(chatOnline);
    }

    @Override
    public void run() throws IOException {
//        MainMenu1 mainmenu = new MainMenu1();
//        clientName = JOptionPane.showInputDialog("Nhap ten cua ban: ");
        Request q = new Request(Request.TYPE.NAME, clientName);
        out.writeObject(q);

    }

    protected int timeOuuClient;
    protected int timeOutOpponent;
    protected int basetime;

    public void setTime(int time) {
        this.basetime = time;
        this.timeOuuClient = time;
        this.timeOutOpponent = time;
    }

    public int getBaseTime() {
        return basetime;
    }
    protected Timer tm;

    public void time() {
        tm = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTurn && isP) {
                    timeOuuClient -= 1;

                    //122/60 = 
                    //122%60 = 2
                    String minutes = String.format("%02d", timeOuuClient / 60);
                    String seconds = String.format("%02d", timeOuuClient % 60);
                    timeOutLabel.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                    timeOutLabel.setFont(new Font("Verdana", Font.PLAIN, 22));
                    timeOutLabel.setForeground(Color.red);
                    timeOutLabel.setText(String.valueOf(minutes + " : " + seconds));
                    timeOutLabelOpp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
                    timeOutLabelOpp.setForeground(Color.black);

                } else if (!isTurn && isP) {
                    timeOutOpponent -= 1;
                    String minutes = String.format("%02d", timeOutOpponent / 60);
                    String seconds = String.format("%02d", timeOutOpponent % 60);
                    timeOutLabelOpp.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                    timeOutLabelOpp.setFont(new Font("Verdana", Font.PLAIN, 22));
                    timeOutLabelOpp.setForeground(Color.red);
                    timeOutLabelOpp.setText(String.valueOf(minutes + " : " + seconds));
                    timeOutLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
                    timeOutLabel.setForeground(Color.black);
                }
            }

        });
    }

    //chia người chơi ra làm 2 phía, phía của người chơi luôn luôn nằm ở dưới! 
    public void setLabelClient() throws FileNotFoundException, IOException {
        BottomLeft = new JPanel();
        BottomLeft.setPreferredSize(new Dimension(200, 250));
        BottomLeft.setBackground(Color.lightGray);
        BottomLeft.setLayout(null);
        JLabel topleft1 = new JLabel();
        timeOutLabel = new JLabel();
        JLabel nameLB = new JLabel(getClientName());

        String minutes = String.format("%02d", timeOutOpponent / 60);
        String seconds = String.format("%02d", timeOutOpponent % 60);
        timeOutLabel.setFont(new Font("Verdana", Font.PLAIN, 22));
        timeOutLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        timeOutLabel.setForeground(Color.black);
        timeOutLabel.setText(minutes + " : " + seconds);
        ImageIcon img = null;
        if (this.player == Piece.Player.WHITE) {
            img = new ImageIcon("Images/guessWhite-01.png");
        } else if (this.player == Piece.Player.BLACK) {
            img = new ImageIcon("Images/guessBlack-01.png");
        } else {
            System.out.println("Load and loi.");
        }

//        topleft1.setBorder(BorderFactory.createLineBorder(Color.red));
//        topleft2.setBorder(BorderFactory.createLineBorder(Color.red));
        //get csdl
        //lay cai anh ra
        nameLB.setBounds(0, 20, 115, 15);

        topleft1.setBounds(36, 36, 115, 115);
        timeOutLabel.setBounds(45, 195, 110, 40);

        topleft1.setIcon(img);
        topleft1.setHorizontalTextPosition(JLabel.CENTER);
        topleft1.setVerticalTextPosition(JLabel.BOTTOM);
        timeOutLabel.setOpaque(true);

        timeOutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeOutLabel.setVerticalAlignment(SwingConstants.CENTER);

        BottomLeft.add(topleft1);
        BottomLeft.add(timeOutLabel);
        west.add(BottomLeft, BorderLayout.SOUTH);
        BottomLeft.add(nameLB);

        tm.start();
    }

    @Override
    public void setLabelOppoent() throws FileNotFoundException, IOException {
        TopLeft = new JPanel();
        TopLeft.setPreferredSize(new Dimension(200, 250));
        TopLeft.setBackground(Color.lightGray);
        TopLeft.setLayout(null);
        JLabel topleft1 = new JLabel();
        timeOutLabelOpp = new JLabel();
        JLabel nameLB = new JLabel(getOpponentName());

        String minutes = String.format("%02d", timeOutOpponent / 60);
        String seconds = String.format("%02d", timeOutOpponent % 60);
        timeOutLabelOpp.setFont(new Font("Verdana", Font.PLAIN, 22));
        timeOutLabelOpp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        timeOutLabelOpp.setForeground(Color.black);
        timeOutLabelOpp.setText(minutes + " : " + seconds);
        ImageIcon img = null;
        if (this.player == Piece.Player.WHITE) {
            img = new ImageIcon("Images/guessBlack-01.png");
        } else if (this.player == Piece.Player.BLACK) {

            img = new ImageIcon("Images/guessWhite-01.png");
        } else {
            System.out.println("Load and loi.");
        }

//        topleft1.setBorder(BorderFactory.createLineBorder(Color.red));
//        topleft2.setBorder(BorderFactory.createLineBorder(Color.red));
        nameLB.setBounds(0, 20, 115, 15);
        topleft1.setBounds(36, 36, 115, 115);
        timeOutLabelOpp.setBounds(45, 195, 110, 40);
        topleft1.setIcon(img);
        topleft1.setHorizontalTextPosition(JLabel.CENTER);
        topleft1.setVerticalTextPosition(JLabel.BOTTOM);
        timeOutLabelOpp.setOpaque(true);

        timeOutLabelOpp.setHorizontalAlignment(SwingConstants.CENTER);
        timeOutLabelOpp.setVerticalAlignment(SwingConstants.CENTER);

        TopLeft.add(topleft1);
        TopLeft.add(timeOutLabelOpp);
        TopLeft.add(nameLB);
        west.add(TopLeft, BorderLayout.NORTH);
    }

    public boolean isIsP() {
        return isP;
    }

    public Scanner getSc() {
        return sc;
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public boolean isIsTurn() {
        return isTurn;
    }

    public String getClientName() {
        return clientName;
    }

    //return client socket
    public Socket getClient() {
        return client;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIsP(boolean h) {
        this.isP = h;
    }

    public void setVi(boolean h) {
        frame.setVisible(h);
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public BufferedImage getClientImg() {
        return clientImg;
    }

    public void setClientImg(BufferedImage clientImg) {
        this.clientImg = clientImg;
    }

    public String getOpponentName() {
        return this.opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public boolean getTurn() {
        return this.isTurn;
    }

    static PhongHau phongH;
    static int chosePH = 1;

    public void setDisplayPhongHauLabel(int x, int y) {
        phongH = new PhongHau(this);
        boardView.setLayout(null);
        phongH.setBounds(100, 100, 100, 500);
        phongH.setVisible(true);
        phongH.setOpaque(true);
        boardView.add(phongH);
    }

    public void NoneDisplayPhongHau() {
        phongH.setVisible(true);
    }

    public int getChosePH() {
        chosePH = phongH.getChosePH();
        return chosePH;
    }

    public boolean dachonxong() {
        return phongH.isDachonxong();
    }

    public void setFalseDachonXong() {
        phongH.setDachonxong(false);
    }

    public void initPH(Piece p, ObjectOutputStream out, IChessController chessDelegate, BoardView b) {
        phongH.setTT(p, out, chessDelegate, b);
    }

    public textMoveHis getHis() {
        return his;
    }

    public void addChat(String chat) {
        chatOnline.addChat(chat);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == offerDraw && controller.getIsPlaying()) {
            //gui thong bao yeu cau hoa cho ben kia.
            //neu ben kia chap nhan => hoa => dung tro choi
            Request q = new Request(Request.TYPE.OFFERDRAW);
            try {
                this.getOut().writeObject(q);

            } catch (IOException ex) {
                Logger.getLogger(clientView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == resign && controller.getIsPlaying()) {
            int choose = JOptionPane.showConfirmDialog(this.getFrame(), "resign", "Are you sure?", JOptionPane.YES_NO_OPTION);

            if (choose == 0) {
                //dong y dau hang!
                controller.setIsPlaying(false);
                this.isP = false;
                controller.getMoveHist().removeAll(controller.getMoveHist());
                JOptionPane.showMessageDialog(this.getFrame(), "Ban da thua!");
                Request q = new Request(Request.TYPE.RESIGN);
                try {
                    this.getOut().writeObject(q);
                } catch (IOException ex) {
                    Logger.getLogger(clientView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else if (e.getSource() == rematch) {
            //gui yeu cau rematch qua ben doi thu! ben server response se tien hanh xu ly!
            Request q = new Request(Request.TYPE.OFFERREMACTH);

            try {
                this.getOut().writeObject(q);
            } catch (IOException ex) {
                Logger.getLogger(clientView.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this.getFrame(), "Waiting for opponent...", "rematch", JOptionPane.PLAIN_MESSAGE);

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Request q = new Request(Request.TYPE.QUIT);
        try {
            this.getOut().writeObject(q);
            controller.stopServerResponse();
            controller.setIsPlaying(false);
//            client.setIsP(false);
            this.getIn().close();
            this.getOut().close();
            this.client.close();
        } catch (IOException ex) {
            Logger.getLogger(clientView.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void setClientName(String name) {
        this.clientName = name;
    }

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller(true);
        controller.initBoardView();
        ClientViews client = new clientView(controller.getB(), controller);
//        MainMenu1 mm = new MainMenu1(controller, this);
//        mm.setVisible(true);

        client.displayMainMN(controller, client);

    }
//    public void runClientView() throws IOException{
//        Controller controller = new Controller(true);
//        controller.initBoardView();
//        ClientViews client = new clientView(controller.getB(), controller);
//        MainMenu1 mm = new MainMenu1(controller, this);
//    }

    public void displayMainMN(Controller c, ClientViews v) {
        MainMenu1 mm = new MainMenu1(c, v);
        mm.setVisible(true);

    }

    @Override
    public void connect() {
        try {
            try {
                client = new Socket("localhost", 1245);
            } catch (IOException ex) {
                Logger.getLogger(clientView.class.getName()).log(Level.SEVERE, null, ex);
            }
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream((client.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(clientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setIsplaying(boolean h) {
        this.isP = h;
    }

}
