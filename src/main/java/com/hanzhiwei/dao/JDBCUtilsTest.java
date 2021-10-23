package com.hanzhiwei.dao;

import com.hanzhiwei.pojo.Role;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.security.PublicKey;
import java.sql.*;
import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-29-17:45
 */
public class JDBCUtilsTest {

    //upadate操作
//        Connection connection = JDBCUtils.getConnection();
//        String sql="insert smbms_bill(billCode,productName) values (?,?)";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setObject(1,"BILL2021_001");
//        ps.setObject(2,"笔记本");
//        ps.execute();
//        JDBCUtils.closeConnection(connection,ps);
//
//query

    public static Role QueryRole(String sql, Object... args) throws Exception {
        sql = "select id,creationDate,modifyDate,roleCode,roleName from smbms_role where id=?";
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {

            ps.setObject(i + 1, args[i]);
        }
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (rs.next()) {
            Role role = new Role();
            for (int i = 1; i < columnCount + 1; i++) {
                Object columnvalue = rs.getObject(i);
                String columnName = metaData.getColumnName(i);
                Field declaredField = Role.class.getDeclaredField(columnName);
                declaredField.setAccessible(true);
                declaredField.set(role, columnvalue);
            }
            return role;
        }
        JDBCUtils.closeConnection(conn, ps, rs);
        return null;
    }

    @Test
    public void test() throws Exception {
        String sql = "select id,createdBy,creationDate,modifyDate,roleCode,roleName from smbms_role where id=?";
//    Role role = QueryRole(sql, 2);
//    System.out.println(role.toString());
        Connection connection = BaseDao.getConnection();
//        List list = BaseDao.QueryInstance1(connection, Role.class, sql, 2);
//        for(Object l:list){
//            System.out.println(l);
//        }

        Role role = (Role) BaseDao.QueryInstance(connection, Role.class, sql, 2);
        System.out.println(role.toString());


    }
}

