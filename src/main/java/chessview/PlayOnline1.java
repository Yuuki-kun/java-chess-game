package chessview;

import chessController.Controller;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.Border;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class PlayOnline1 extends javax.swing.JFrame implements ActionListener{
    Controller controller;
    public PlayOnline1(Controller c) {
        this.controller =c;
        initComponents();
        
        this.setLocationRelativeTo(null);
//        Search.setLayout(new BorderLayout());
//        Search.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
//        Search.add(SearchIcon, BorderLayout.EAST);
    }
//    int numElements;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        PlayerOnline = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        PlayerOnline.setText("Player Online");

        jButton2.setText("Back");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(PlayerOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 99, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(PlayerOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 245, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
//        MainMenu1 MN = new MainMenu1();
//        this.dispose();
//        MN.setVisible(true);
    }//GEN-LAST:event_jButton2MouseClicked

//   public void showPlayer(String []array){
//       //copy array
//       
//        // Lấy số phần tử trong mảng
//    numElements = array.length;
//    // Tạo ra các label tương ứng với số phần tử trong mảng
//    JLabel[] labels = new JLabel[numElements];
//    JButton[] button = new JButton[numElements];
//    // Vị trí để hiển thị label (vị trí dưới label "Player Online")
//    int yPosition = PlayerOnline.getY() + PlayerOnline.getHeight() + 10;
//    // Vòng lặp để tạo các label và hiển thị giá trị mảng
//    for (int i = 0; i < numElements; i++) {
//        
//        // Tạo label mới và button mới
//        labels[i] = new JLabel();
//        button[i] = new JButton();
//        // Set vị trí và kích thước cho label và button
//        labels[i].setBounds(20, yPosition, 160, 30);
//        button[i].setBounds(200,yPosition,60,30);
////        button[i].addKeyListener(this);
//        // Set border cho label (viền màu đỏ)
//        Border border = BorderFactory.createLineBorder(Color.black, 2, true);
//       
//        labels[i].setBorder(border);
//        button[i].setBorder(border);
//        button[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        // Set giá trị của label là phần tử thứ i trong mảng
//        labels[i].setText(array[i]);
//        Font font = new Font("Arial", Font.BOLD, 8); // Tạo đối tượng Font mới với kiểu chữ Arial, độ đậm BOLD và cỡ 16
//        button[i].setText("CHALLENGE");
//        button[i].setFont(font);
//        // Thêm label vào jPanel1
//        jPanel1.add(labels[i]);
//        jPanel1.add(button[i]);
//        // Cập nhật vị trí yPosition để hiển thị label tiếp theo
//        yPosition += 40;
//    }
//    
//
//    // Cập nhật lại jPanel1 để hiển thị các label mới được thêm vào
//    jPanel1.revalidate();
//    jPanel1.repaint();
//    }
    JButton[] buttons;
    JLabel[] labels;
    public void showPlayer(String[] array) {
        // Gán giá trị cho numElements và globalArray
        // Tạo ra các label tương ứng với số phần tử trong mảng
         labels = new JLabel[array.length];
         buttons = new JButton[array.length];
        // Tạo một JPanel mới để chứa các label
        JPanel panel = new JPanel();
        // Thiết lập layout cho panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Vòng lặp để tạo các label và hiển thị giá trị mảng
        for (int i = 0; i < array.length; i++) {
            //thêm vào globalArray

            // Tạo label mới và button mới
            labels[i] = new JLabel(array[i]);
            labels[i].setFont(new Font("consolas", Font.BOLD, 18));

            buttons[i] = new JButton("CHALLENGE");
            buttons[i].setFont(new Font("consolas", Font.BOLD, 13));
            buttons[i].addActionListener(this);
            // Thêm label và button vào panel
            panel.add(labels[i]);
            panel.add(buttons[i]);

        }
        // Tạo một JScrollPane mới và đặt panel làm nội dung của nó
        JScrollPane scrollPane = new JScrollPane(panel);
        // Thiết lập kích thước và vị trí cho scrollPane
        scrollPane.setBounds(20, PlayerOnline.getY() + PlayerOnline.getHeight() + 10, 250, 200);
        // Thêm scrollPane vào jPanel1
        jPanel1.add(scrollPane);
        // Cập nhật lại jPanel1 để hiển thị scrollPane
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    public void visible(){
        this.setVisible(true);
    }

//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                String[] array = {"Player 1", "Player 2", "Player 3", "Player 1", "Player 2", "Player 3", "Player 1", "Player 2", "Player 3", "Player 1", "Player 2", "Player 3"};
//
//                PlayOnline1 pl = new PlayOnline1();
//                pl.showPlayer(array);
//                pl.setVisible(true);
//
//            }
//        });
//    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PlayerOnline;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
            String name = labels[i].getText();
                try {
                    String time = JOptionPane.showInputDialog(this, "thoi gian (phut): ", "time",JOptionPane.PLAIN_MESSAGE);
                    int rtime = Integer.parseInt(time);
                    controller.setTime(rtime*60);
                    controller.challenge(name, rtime*60);
                } catch (IOException ex) {
                    Logger.getLogger(PlayOnline1.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.dispose();
            }
        }
    }

}
