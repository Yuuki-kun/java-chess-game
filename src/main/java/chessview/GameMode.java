/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package chessview;

import chessController.Controller;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameMode extends javax.swing.JFrame {

    public chessController.Controller controller;
    ClientViews client;
    protected boolean isLogin;
    protected String clientname = "";

    /**
     * Creates new form GameMode1
     */
    public GameMode(boolean islogiin, String name) {
        this.clientname = name;
        this.isLogin = islogiin;
        initComponents();
        if (islogiin) {
            jButton1.setText("Log out");
        } else {
            jButton1.setText("Back");

        }

        if (!name.equals("")) {
            jLabel2.setText(jLabel2.getText() + " " + name);
        }

        this.setLocationRelativeTo(null);
    }

    public void setController(Controller c) {
        this.controller = c;
    }
    public void setClientV(ClientViews v){
        this.client = v;
    }

    String[] ArrayName;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Computer = new javax.swing.JButton();
        TwoPlayer = new javax.swing.JButton();
        Online = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        backGround = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Sitka Heading", 3, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chess Game");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 500, 60));

        Computer.setBackground(new Color(255,0,0,0));
        Computer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Computer.setForeground(new java.awt.Color(102, 255, 102));
        Computer.setText("Computer");
        Computer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComputerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ComputerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ComputerMouseExited(evt);
            }
        });
        jPanel1.add(Computer, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 130, 40));

        TwoPlayer.setBackground(new Color(255,0,0,0));
        TwoPlayer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TwoPlayer.setForeground(new java.awt.Color(102, 255, 102));
        TwoPlayer.setText("TwoPlayer");
        TwoPlayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TwoPlayerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TwoPlayerMouseExited(evt);
            }
        });
        jPanel1.add(TwoPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 130, 40));

        Online.setBackground(new Color(255,0,0,0));
        Online.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Online.setForeground(new java.awt.Color(102, 255, 102));
        Online.setText("Online");
        Online.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OnlineMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                OnlineMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                OnlineMouseExited(evt);
            }
        });
        Online.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnlineActionPerformed(evt);
            }
        });
        jPanel1.add(Online, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 130, 40));

        jButton1.setText("Back");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 100, 30));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Xin chào");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 30));

        backGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        jPanel1.add(backGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 500, 350));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OnlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OnlineMouseClicked
//        // TODO add your handling code here:
//        
//        String playerName = JOptionPane.showInputDialog(this, "Enter player name:");
//            if (playerName != null && !playerName.trim().isEmpty()) {
//        // Nếu người dùng nhập tên người chơi và không bị rỗng thì có thể tiếp tục với chức năng ở đây
//        // Ví dụ: Mở màn hình trò chơi online và hiển thị tên người chơi này ở đó
//        // Ví dụ: Gửi thông tin tên người chơi này đến máy chủ để kết nối trò chơi online
//        PlayOnline PlO = new PlayOnline();
//        dispose();
//        PlO.setVisible(true);
//       
//
//       
//
//        PlO.showPlayer(playerNames);
//            } else {
//        // Nếu người dùng không nhập tên người chơi hoặc tên bị rỗng hiển thị thông báo lỗi 
//            JOptionPane.showMessageDialog(this, "Player name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
//         }

// Khởi tạo một ArrayList để lưu trữ tên người chơi
//        ArrayList<String> playerNames = new ArrayList<>();

// Xử lý sự kiện khi người dùng nhập tên người chơi
//        String playerName = JOptionPane.showInputDialog(this, "Enter player name:");
//        if (playerName != null && !playerName.trim().isEmpty()) {
//            // Nếu người dùng nhập tên người chơi và không bị rỗng thì lưu tên người chơi vào mảng
//            playerNames.add(playerName);
//
//            // Mở màn hình trò chơi và truyền mảng tên người chơi vào phương thức showPlayer
//            PlayOnline1 PlO = new PlayOnline1();
//            dispose();
//            PlO.setVisible(true);
//            String[] Array = playerNames.toArray(new String[playerNames.size()]);
//            for (int i = 0; i < Array.length; i++) {
//                ArrayName[i] = Array[i];
//            }
//
//        } else {
//            // Nếu người dùng không nhập tên người chơi hoặc tên bị rỗng hiển thị thông báo lỗi
//            JOptionPane.showMessageDialog(this, "Player name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
//        }

    }//GEN-LAST:event_OnlineMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
//        MainMenu1 MM = new MainMenu1(controller,v);
//        this.dispose();
//        MM.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    private void ComputerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComputerMouseEntered
        // TODO add your handling code here:
        Computer.setForeground(new Color(204, 255, 204, 255));
    }//GEN-LAST:event_ComputerMouseEntered

    private void ComputerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComputerMouseExited
        // TODO add your handling code here:
        Computer.setForeground(new Color(102, 255, 102, 255));
    }//GEN-LAST:event_ComputerMouseExited

    private void TwoPlayerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TwoPlayerMouseEntered
        // TODO add your handling code here:
        TwoPlayer.setForeground(new Color(204, 255, 204, 255));
    }//GEN-LAST:event_TwoPlayerMouseEntered

    private void TwoPlayerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TwoPlayerMouseExited
        // TODO add your handling code here:
        TwoPlayer.setForeground(new Color(102, 255, 102, 255));
    }//GEN-LAST:event_TwoPlayerMouseExited

    private void OnlineMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OnlineMouseEntered
        // TODO add your handling code here:
        Online.setForeground(new Color(204, 255, 204, 255));
    }//GEN-LAST:event_OnlineMouseEntered

    private void OnlineMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OnlineMouseExited
        // TODO add your handling code here:
        Online.setForeground(new Color(102, 255, 102, 255));
    }//GEN-LAST:event_OnlineMouseExited

    private void ComputerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComputerMouseClicked
        // TODO add your handling code here:
////        Computer CP = new Computer();
//        this.dispose();
//        CP.setVisible(true);
    }//GEN-LAST:event_ComputerMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void OnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnlineActionPerformed
        try {
            // TODO add your handling code here:
            //hien thi danh sach nguoi chơi
            this.controller.showAllRequest();
        } catch (IOException ex) {
            Logger.getLogger(GameMode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_OnlineActionPerformed

    public void setVisible() {
        this.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Computer;
    private javax.swing.JButton Online;
    private javax.swing.JButton TwoPlayer;
    private javax.swing.JLabel backGround;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
