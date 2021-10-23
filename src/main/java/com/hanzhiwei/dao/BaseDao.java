package com.hanzhiwei.dao;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-29-21:42
 */
public class BaseDao {
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
    // 通用的增删改操作---version 2.0 （考虑上事务）
    public static int updateTransaction(Connection conn, String sql, Object... args) throws Exception {
        int j=0;
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            j = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            conn.setAutoCommit(true);
            BaseDao.closeConnection(null, ps);

        }
        return j;

    }

    public static List QueryInstance2(Connection conn, Class clazz, String sql, Object...args) throws Exception {
        ResultSet rs=null;
        PreparedStatement ps=null;
        try {
            ArrayList list = new ArrayList();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (rs.next()) {
                Object A = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i + 1);
                    Object value = rs.getObject(i + 1);
                    Field columnname = clazz.getDeclaredField(columnName);
                    columnname.setAccessible(true);
                    columnname.set(A, value);

                }
                list.add(A);


            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }  finally {

            BaseDao.closeConnection(null,ps,rs);

        }
        return null;
    }

    //查询通用表一条信息（考虑事务）
    public static Object QueryInstance(Connection conn, Class clazz, String sql, Object...args) throws Exception {
        ResultSet rs=null;
        PreparedStatement ps=null;
        try {

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (rs.next()) {
                Object A = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Object value = rs.getObject(i + 1);
                    Field columnname = clazz.getDeclaredField(columnName);
                    columnname.setAccessible(true);
                    columnname.set(A, value);

                }
                return A;


            }
        }catch (Exception e){
            e.printStackTrace();
        }  finally {

            BaseDao.closeConnection(null,ps,rs);

        }
        return null;
    }

    //编写查询公共方法 狂老师写的用于解决用户管理参数存放的问题 可以把数组给传进去
    public static ResultSet execute(Connection connection, String sql, Object[] params, ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        //预编译的sql，在后面直接执行就可以了
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            //setObject,占位符从1开始，但是我们的数组是从0开始！
            preparedStatement.setObject(i+1,params[i]);
        }

        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
