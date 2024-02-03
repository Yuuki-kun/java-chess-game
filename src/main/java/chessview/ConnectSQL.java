/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessview;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectSQL {

    Connection con;

    public ConnectSQL() {

    }

    //;trustServerCertificate=true
    public void connection() {
        try {
//            String dbURL = "jdbc:sqlserver://localhost\\localhost:1433;databasename=Account;user=sa;password=reallyStrongPwd123;encrypt=true;trustServerCertificate=true";
            String dbURL = "jdbc:sqlserver://localhost\\testBS:1433;databaseName=Account;encrypt=true; trustServerCertificate=true;user=sa;password=reallyStrongPwd123";
            con = DriverManager.getConnection(dbURL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insertData(String InGame, String UserName, String PassWord) {
        try {
            if (getCon() == null || getCon().isClosed()) {
                return;
            }
            Statement s = con.createStatement();
            int r = s.executeUpdate("insert into AccountRG values('" + InGame + "','" + UserName + "','" + PassWord + "')");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public Connection getCon() {
        return con;
    }

//    PreparedStatement prepareStatement(String query) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//   public PreparedStatement prepareStatement(String query) throws SQLException {
//   String dbURL = "jdbc:sqlserver://LAPTOP-07\\SQLEXPRESS:1433;databasename=Account;user=sa;password=sa1234;encrypt=false;trustServerCertificate=true";
//            con = DriverManager.getConnection(dbURL);
//    PreparedStatement statement = con.prepareStatement(query);
//    return statement;
//}
    public PreparedStatement prepareStatement(String query) throws SQLException {
        if (con == null) {
            connection();
        }
        PreparedStatement statement = con.prepareStatement(query);
        return statement;
    }

}
