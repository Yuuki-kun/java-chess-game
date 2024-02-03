package chessview;

import chessController.Controller;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends javax.swing.JFrame {

    Controller c;
    ClientViews v;

    public Register(Controller c, ClientViews v) {
        this.c = c;
        this.v = v;
        initComponents();
        this.getContentPane().setBackground(new Color(223, 233, 237));
        this.setLocationRelativeTo(null);
        Register.setBackground(new Color(51, 153, 255));
    }
//    ConnectSQL con;
//
//    public void connection() {
//        try {
//            String dbURL = "jdbc:sqlserver://LAPTOP-07\\SQLEXPRESS:1433;databasename=Account;user=sa;password=sa1234;encrypt=true;trustServerCertificate=false;sslProtocol=TLSv1.2";
//            con = (ConnectSQL) DriverManager.getConnection(dbURL);
//            if (con !=null){
//                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Account");
//            } 
//            else {
//    // Handle the error
//            throw new SQLException("Failed to establish a database connection.");
//
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

// lấy dự liệu trên sql sever check nếu dữ liệu tên game hoặc tên người chơi đang đăng ký đã có rồi trả về true
    ConnectSQL con = new ConnectSQL();

    public boolean checkAccountExists(String username, String ingame) {
        try {
            String query = "SELECT COUNT(*) AS count FROM AccountRG WHERE UserName = ? OR InGame = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, ingame);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int count = rs.getInt("count");
            return count > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

//    private boolean checkSpecialCharacters(String str) {
//        String specialChars = "!@#$%^&*()_+{}[]|\\;:'\",./<>?";
//        for (int i = 0; i < str.length(); i++) {
//            if (specialChars.contains(Character.toString(str.charAt(i)))) {
//                return true;
//            }
//        }
//        return false;
//    }
    boolean checkname(String name, String Ingame) {
        if (name.equals("") || Ingame.equals("")) {
            return true;
        }

        return false;
    }
// kiểm tra mật khẫu xem có rỗng không và khớp không

    boolean checkpass(String pass, String confirmpass) {
        if (pass.equals("") || pass.length() < 8) {
            return true;
        } else if (!pass.equals(confirmpass)) {
            return true;
        }
        return false;
    }
// kiểm tra all

    boolean checkall(String name, String Ingame, String pass, String confirmpass) {

        if (!checkname(name, Ingame)) {
//        if (!checkSpecialCharacters(name) || !checkSpecialCharacters(Ingame)) {
            if (!checkname(pass, confirmpass)) {
                if (!checkpass(pass, confirmpass)) {
                    if (!checkAccountExists(name, Ingame)) {
                        JOptionPane.showMessageDialog(rootPane, "Đăng ký thành công");
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Tên tài khoản hoặc tên người chơi đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Mật khẩu không khớp", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, " vui lòng nhập Mật khẩu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tên tài khoản hoặc mật khẩu chứa ký tự đặc biệt", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
//    } else {
//        JOptionPane.showMessageDialog(null, "Tên người chơi hoặc tên tài khoản rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        return false;
//    }

    } // Thêm dòng này để đóng hàm checkall()

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ingame = new javax.swing.JTextField();
        userName = new javax.swing.JTextField();
        PassWd = new javax.swing.JPasswordField();
        ConfirmPass = new javax.swing.JPasswordField();
        Register = new javax.swing.JButton();
        Back = new javax.swing.JButton();
        Clear = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));
        setResizable(false);

        jLabel3.setFont(new java.awt.Font("Menlo", 0, 13)); // NOI18N
        jLabel3.setText("Tên Người Chơi");

        jLabel4.setFont(new java.awt.Font("Menlo", 0, 13)); // NOI18N
        jLabel4.setText("Tài Khoản");

        jLabel5.setFont(new java.awt.Font("Menlo", 0, 13)); // NOI18N
        jLabel5.setText("Mật Khẩu");

        jLabel6.setFont(new java.awt.Font("Menlo", 0, 13)); // NOI18N
        jLabel6.setText("Xác Nhận Mật Khẩu");

        Register.setBackground(new java.awt.Color(51, 153, 255));
        Register.setFont(new java.awt.Font("Chalkboard", 1, 18)); // NOI18N
        Register.setForeground(new java.awt.Color(255, 255, 255));
        Register.setText("Register");
        Register.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(102, 102, 0), new java.awt.Color(255, 0, 51), new java.awt.Color(255, 0, 153), new java.awt.Color(51, 255, 102)));
        Register.setBorderPainted(false);
        Register.setOpaque(true);
        Register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegisterMouseClicked(evt);
            }
        });
        Register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterActionPerformed(evt);
            }
        });

        Back.setFont(new java.awt.Font("Mukta Mahee", 1, 13)); // NOI18N
        Back.setForeground(new java.awt.Color(0, 102, 204));
        Back.setText("Back");
        Back.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Back.setOpaque(true);
        Back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackMouseClicked(evt);
            }
        });

        Clear.setFont(new java.awt.Font("Menlo", 1, 13)); // NOI18N
        Clear.setForeground(new java.awt.Color(0, 102, 204));
        Clear.setText("Clear");
        Clear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Clear.setOpaque(true);
        Clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Chalkboard", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Chess");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(Back, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Clear, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ingame, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PassWd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Register, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ConfirmPass, PassWd, ingame, userName});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ingame, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PassWd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(42, 42, 42)
                .addComponent(Register, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Back, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(Clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ConfirmPass, PassWd, ingame, userName});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegisterMouseClicked
        String UserName = String.valueOf(userName.getText());
        String Ingame = String.valueOf(ingame.getText());
        String matkhau = new String(PassWd.getPassword());
        String confirmMk = new String(ConfirmPass.getPassword());
        if (checkall(UserName, Ingame, matkhau, confirmMk)) {
            dispose();
            Login lg = new Login(c, v);
            lg.setVisible(true);
        }
        ConnectSQL cn = new ConnectSQL();
        cn.connection();
        cn.insertData(Ingame, UserName, matkhau);
    }//GEN-LAST:event_RegisterMouseClicked

    private void ClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearMouseClicked

        userName.setText("");
        ingame.setText("");
        PassWd.setText("");
        ConfirmPass.setText("");
    }//GEN-LAST:event_ClearMouseClicked

    private void BackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackMouseClicked
//
        MainMenu1 MM = new MainMenu1(c, v);
        MM.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackMouseClicked

    private void RegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RegisterActionPerformed
//    public static void main(String args[]) {
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                Register RT = new Register();
//                RT.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JButton Clear;
    private javax.swing.JPasswordField ConfirmPass;
    private javax.swing.JPasswordField PassWd;
    private javax.swing.JButton Register;
    private javax.swing.JTextField ingame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField userName;
    // End of variables declaration//GEN-END:variables
}
