/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessmodel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author tongcongminh
 */
public class Server {

    private ServerSocket serverSocket;
    private static final int PORT = 1245;

    private static ArrayList<ClientHandler> clientsList = new ArrayList<>();
    
    
    private static ExecutorService pool = Executors.newFixedThreadPool(20);

    static ArrayList<String> clientss = new ArrayList<>();
    JLabel listening = new JLabel("[SERVER]: Server is listening on port[" + PORT + "]");
    static JTextArea clientTextArea = new JTextArea();

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);

        JFrame frame = new JFrame("SERVER SOCKET");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        listening.setSize(new Dimension(1000, 100));
        clientTextArea.setPreferredSize(new Dimension(500, 300));

        frame.setLayout(null);

        listening.setBounds(10, 10, 1000, 100);
        clientTextArea.setBounds(10, 100, 500, 300);

        frame.add(listening);
        frame.add(clientTextArea);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        while (true) {

            System.out.println("[SERVER]: Server is listening on port[" + PORT + "]");

            //accept connect
            Socket client = server.serverSocket.accept();            
            ClientHandler clientThread = new ClientHandler(client, clientsList);
            clientsList.add(clientThread);
            clientThread.start();

        }
    }

    public static void displayClientConnect() {
        String all = "";
        for (String s : clientss) {
            all += s + "\n";
        }
        clientTextArea.setText(all);
    }

}
