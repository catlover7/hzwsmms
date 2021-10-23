package com.hanzhiwei.dao;


import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-29-17:28
 */
public class JDBCUtils {
    public static void main(String[] args) {

    }
    public static Connection getConnection() throws Exception{
        InputStream is = ConnectDB.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties ps = new Properties();
        ps.load(is);
        String url = ps.getProperty("url");
        String user = ps.getProperty("user");
        String password = ps.getProperty("password");
        String driver = ps.getProperty("driver");


        Class.forName(driver);

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;

    }
    public static void closeConnection(java.sql.Connection conn, PreparedStatement ps){
        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void closeConnection(java.sql.Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }try {
            if(rs!=null)
                rs.close();
        }catch (SQLException e){
            e.printStackTrace();

        }
    }

}
