/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessmodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author tongcongminh
 */
public class ClientHandler extends Thread {

    private Request q;
    private String clientName;
    private String enemyName;
    private Socket client;
    private ClientHandler enemy;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean isTurn;
    private ArrayList<ClientHandler> clientList;

    ClientHandler(Socket client, ArrayList<ClientHandler> clientsList) throws IOException {
        this.client = client;
        this.clientList = clientsList;
        this.in = new ObjectInputStream((this.client.getInputStream()));
        this.out = new ObjectOutputStream(this.client.getOutputStream());
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {

        try {
            //        try {
//            askClientName();
//            String name = (String) in.readObject();
//            out.writeObject("[SERVER]: Hello " + name + ", good day!");
//        } catch (IOException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
            askClientName();
            //gan vo key(name), socket
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("inputtttt");
        Object name = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (q != null) {
                    q.init();
                }

                q = (Request) in.readObject();
                if (q.getType() == Request.TYPE.NAME) {

                    this.clientName = q.getName();
                    q.init();
                    q.setType(Request.TYPE.MESSAGE);
                    out.writeObject(q);

                    Server.clientss.add(clientName);
                    Server.displayClientConnect();

                } else if (q.getType() == Request.TYPE.MOVE) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.CHALLENGE) {
                    //A thach dau B
                    String enemyName = q.getEnemyName();
                    //get(key)
                    for (int i = 0; i < this.clientList.size(); i++) {
                        System.out.println(clientList.get(i).getClientName());
                        if (clientList.get(i).getClientName().equals(enemyName)) {
                            this.enemyName = enemyName;
                            this.enemy = clientList.get(i).getClient();

//                            q.setName(this.enemyName);
                            this.enemy.getOut().writeObject(q);
                        }
                    }
                } else if (q.getType() == Request.TYPE.ACCEPT) {
                    String enemyName = q.getName();

                    for (int i = 0; i < this.clientList.size(); i++) {
                        if (clientList.get(i).getClientName().equals(enemyName)) {
                            this.enemyName = enemyName;
                            this.enemy = clientList.get(i).getClient();
                        }
                    }
//                this.out.println("[SERVER]: Accepted challenge from " + this.enemyName);
//                    this.enemy.out.println("[SERVER]: " + this.getClientName() + " accepted your challenge!");
//                    q.init();
                    q.setType(Request.TYPE.PLAY);
                    q.setPlayer(Piece.Player.WHITE);
                    this.out.writeObject(q);
                    
                    
                    q.setPlayer(Piece.Player.BLACK);
                    this.enemy.getOut().writeObject(q);

                } else if (q.getType() == Request.TYPE.REFUSE) {

                    for (int i = 0; i < this.clientList.size(); i++) {
                        if (clientList.get(i).getClientName().equals(q.getName())) {
                            q.setType(Request.TYPE.DIRECT);

                            clientList.get(i).getOut().writeObject(q);
                        }
                    }

//                    this.enemyName = "";
//                    this.enemy = null;
                } else if (q.getType() == Request.TYPE.SHOWALL) {
                    q.init();
                    q.setType(Request.TYPE.SHOWALL);
                    q.setListName(this.getListOfClient());
                    this.out.writeObject(q);
                } else if (q.getType() == Request.TYPE.CHECKMATE || q.getType() == Request.TYPE.RESIGN) {
                    //
                    this.enemy.getOut().writeObject(q);

//                    this.enemy.setEnemy(null);
//                    this.enemy.setEnemy(null);
//                    this.setEnemy(null);
//                    this.setEnemyName(null);
                } else if (q.getType() == Request.TYPE.PHONGHAU) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.CHAT) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.OFFERDRAW) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.REFUSEDRAW) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.DRAW) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.OFFERREMACTH) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.REFUSEREMATCH) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.REMATCH) {
                    this.enemy.getOut().writeObject(q);
                } else if (q.getType() == Request.TYPE.QUIT) {

//                    this.enemy.getOut().writeObject(q);
//                    if (this.client.isClosed()) {
                        System.out.println("BEN KIA DA DONG SOCKET!");
                        this.in.close();
                        this.out.close();
                        this.client.close();
                        
//                    }
                    if (this.enemy != null) {
                        this.enemy.getOut().writeObject(q);
                        this.enemy.setEnemyName(null);
                        this.enemy.setEnemy(null);
                    }
                    this.setEnemy(null);
                    this.setEnemyName(null);
                    Server.clientss.remove(this.clientName);
                    clientList.remove(this);
//                    this.in.close();
//                    this.out.close();
//                    this.client.close();
                    Server.displayClientConnect();
                    this.interrupt();
                }
            } catch (IOException ex) {
//                try {
//                    //                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//                    in.close();
//                } catch (IOException ex1) {
//                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex1);
//                }
            } catch (ClassNotFoundException ex) {
//                try {
//                    in.close();
//                } catch (IOException ex1) {
//                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex1);
//                }
//
//                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

//        this.clientName = (String) name;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public void setEnemy(ClientHandler enemy) {
        this.enemy = enemy;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public void setClientList(ArrayList<ClientHandler> clientList) {
        this.clientList = clientList;
    }

    public void askClientName() throws IOException, ClassNotFoundException {
//        String askName = "NAME enter name:";
        Request askName = new Request(Request.TYPE.ASKNAME);
        out.writeObject(askName);
//        System.out.println("hoi ten duoc roi ne");
//        this.in = new ObjectInputStream((this.client.getInputStream()));

    }

    public String getClientName() {
        return clientName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public ClientHandler getClient() {
        return this;
    }

    public Socket getClientSocket() {
        return this.client;
    }

    public ClientHandler getEnemy() {
        return enemy;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public String[] getListOfClient() {
        String[] name = new String[this.clientList.size() - 1];
        int size = this.clientList.size();
        int index = 0;
        for (int i = 0; i < size; i++) {
            String namePlayer = this.clientList.get(i).getClientName();

            if (namePlayer != this.clientName) {
                if (namePlayer != null) {
                    name[index] = new String(namePlayer);
                    index++;
                }
            }

        }
        return name;
    }

}
