package com.hanzhiwei.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-29-17:21
 */
public class ConnectDB {
    public static void main(String[] args) throws Exception {
        InputStream is = ConnectDB.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties ps = new Properties();
        ps.load(is);
        String url = ps.getProperty("url");
        String user = ps.getProperty("user");
        String password = ps.getProperty("password");
        String driver = ps.getProperty("driver");


        Class.forName(driver);

        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);


    }
}
