package com.hanzhiwei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.PseudoColumnUsage;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-10-01-13:29
 */
public class UpdateQueryTest {
    public static void main(String[] args) throws Exception {
                Connection connection = JDBCUtils.getConnection();
        String sql="insert smbms_bill(billCode,productName) values (?,?)";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setObject(1,"BILL2022_001");
//        ps.setObject(2,"电脑");
//        ps.execute();
        int i = BaseDao.updateTransaction(connection, sql, "BILL2022_001", "电脑");
        System.out.println(i);
        BaseDao.closeConnection(connection, null);

    }
}
