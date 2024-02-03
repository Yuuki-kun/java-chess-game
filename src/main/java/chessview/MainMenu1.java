package chessview;

import chessController.Controller;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MainMenu1 extends javax.swing.JFrame {

    Controller c;
    ClientViews client;
    public MainMenu1(Controller c, ClientViews client) {
        this.c=c;
        this.client = client;
        this.setResizable(false);
        initComponents();

        this.setLocationRelativeTo(null);
//        Login.setBackground(new Color(255,0,0,0));
//        Register.setBackground(new Color(255,0,0,0));
//        PlayNow.setBackground(new Color(255,0,0,0));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Register = new javax.swing.JButton();
        Login = new javax.swing.JButton();
        PlayNow = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        backGround = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Register.setBackground(new java.awt.Color(204, 204, 204));
        Register.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Register.setText("Đăng Ký");
        Register.setFocusable(false);
        Register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegisterMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RegisterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RegisterMouseExited(evt);
            }
        });
        Register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterActionPerformed(evt);
            }
        });
        jPanel1.add(Register, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 140, 40));

        Login.setBackground(new java.awt.Color(204, 204, 204));
        Login.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Login.setText("Đăng Nhập");
        Login.setFocusable(false);
        Login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LoginMouseExited(evt);
            }
        });
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });
        jPanel1.add(Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 140, 40));

        PlayNow.setBackground(new java.awt.Color(204, 204, 204));
        PlayNow.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        PlayNow.setText("Chơi Ngay");
        PlayNow.setFocusable(false);
        PlayNow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PlayNowMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PlayNowMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PlayNowMouseExited(evt);
            }
        });
        PlayNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayNowActionPerformed(evt);
            }
        });
        jPanel1.add(PlayNow, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 140, 40));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Sitka Heading", 3, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chess Game");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 280, 70));

        backGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        jPanel1.add(backGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 340));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterActionPerformed
        Register RG = new Register(c, client);
//        this.dispose();
        this.setVisible(false);
        RG.setVisible(true);
    }//GEN-LAST:event_RegisterActionPerformed

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
        Login lg = new Login(c, client);
        this.dispose();
        lg.setVisible(true);
    }//GEN-LAST:event_LoginActionPerformed

    private void LoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginMouseEntered
        // TODO add your handling code here:
        Login.setForeground(Color.RED);
    }//GEN-LAST:event_LoginMouseEntered

    private void LoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginMouseExited
        // TODO add your handling code here:
        Login.setForeground(Color.BLACK);
    }//GEN-LAST:event_LoginMouseExited

    private void LoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginMouseClicked
        // TODO add your handling code here:
//        Login lg = new Login();
//        this.dispose();
//        lg.setVisible(true);
    }//GEN-LAST:event_LoginMouseClicked

    private void RegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegisterMouseClicked
        // TODO add your handling code here:
//        Register RG = new Register();
//        this.dispose();
//        RG.setVisible(true);
    }//GEN-LAST:event_RegisterMouseClicked

    private void RegisterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegisterMouseEntered
        // TODO add your handling code here:
        Register.setForeground(Color.RED);
    }//GEN-LAST:event_RegisterMouseEntered

    private void RegisterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegisterMouseExited
        // TODO add your handling code here:
        Register.setForeground(Color.BLACK);
    }//GEN-LAST:event_RegisterMouseExited

    private void PlayNowMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayNowMouseEntered
        // TODO add your handling code here:
        PlayNow.setForeground(Color.RED);

    }//GEN-LAST:event_PlayNowMouseEntered

    private void PlayNowMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayNowMouseExited
        // TODO add your handling code here:
        PlayNow.setForeground(Color.BLACK);
    }//GEN-LAST:event_PlayNowMouseExited

    private void PlayNowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayNowMouseClicked
//        // TODO add your handling code here:
//        GameMode Gm = new GameMode(false);
//        this.dispose();
//        Gm.setVisible();
    }//GEN-LAST:event_PlayNowMouseClicked

    private void PlayNowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayNowActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog(this, "enter name:", "play now");
        this.dispose();
        client.connect();
        client.setClientName(name);
        
        try {
            c.run(client);

//        Controller controller = null;
//        try {
//            controller = new Controller(false);
//        } catch (IOException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            controller.initBoardView();
//        } catch (IOException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ClientViews client = null;
//        try {
//            client = new clientView(controller.getB(), controller);
//            client.setClientName(name);
//        } catch (IOException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            c.run(client);
//        } catch (IOException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex)        } catch (IOException ex) {
        } catch (IOException ex) {
            Logger.getLogger(MainMenu1.class.getName()).log(Level.SEVERE, null, ex);
        }

//        }
    }//GEN-LAST:event_PlayNowActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainMenu1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainMenu1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainMenu1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainMenu1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainMenu1().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Login;
    private javax.swing.JButton PlayNow;
    private javax.swing.JButton Register;
    private javax.swing.JLabel backGround;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
