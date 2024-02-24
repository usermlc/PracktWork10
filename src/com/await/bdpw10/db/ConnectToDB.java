package com.await.bdpw10.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToDB {
    public static Connection connect() {
        String url = "jdbc:sqlite:F:/Java/Java BD/data.sqlite/";

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
