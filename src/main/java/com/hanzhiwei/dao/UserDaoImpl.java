package com.hanzhiwei.dao;

import com.hanzhiwei.pojo.Role;
import com.hanzhiwei.pojo.User;
import com.mysql.cj.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.INTERNAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-29-21:54
 */
public class UserDaoImpl implements UserDao {
    @Override

    public User getUser(Connection conn, String UserCode) throws Exception {
        String sql = "select * from smbms_user where UserCode=? ";
        User user = (User) BaseDao.QueryInstance(conn, User.class, sql, UserCode);
        return user;
    }

    @Override
    public int updatePassword(Connection conn, int id, String password) throws Exception {
        String sql = "update smbms_user set userPassword=? where id=?";
        int i = BaseDao.updateTransaction(conn, sql, password, id);
        return i;
    }

    @Override
    public int getUsercount(Connection connection, String username, int userRole) throws Exception {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        int count = 0;
//        if (conn != null) {
//            StringBuffer sql = new StringBuffer();
//            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole=r.id");
//            ArrayList<Object> list = new ArrayList<>();//存放参数
//            if (!StringUtils.isNullOrEmpty(username)) {
//                //将传进来的Username作为u.userName 的别名这样的话就会取出符合前端username的信息
//                sql.append(" and u.userName like ?");
//                list.add("%" + username + "%");//%模糊查询
//
//            }
//            if (userRole > 0) {
//                sql.append(" and u.userRole like ?");
//                list.add(userRole);
//            }
//            //list -》array
//            Object[] params = list.toArray();
//            //输出完整的sql语句
//            System.out.println("userdao->getUsercount:" + sql.toString());
//       //     rs = (ResultSet) BaseDao.QueryInstance(conn, User.class, sql.toString(), params); 参数存放到数组中了 必须采用狂老师的方法
//             rs = BaseDao.execute(conn, sql.toString(), params, rs, ps);
//            if (rs.next()) {
//                count = rs.getInt("count");
//            }
//            BaseDao.closeConnection(null, ps, rs);
//        }
//        return count;
//    }
        int count = 0;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COUNT(*) AS count FROM `smbms_user` u,`smbms_role` r WHERE u.`userRole`=r.`id`");
            ArrayList<Object> list = new ArrayList<Object>();//存放可能会放进sql里的参数，就是用来替代?的params
            if (!StringUtils.isNullOrEmpty(username)) {
                sql.append(" and u.username like ?");
                list.add("%" + username + "%");//模糊查询，index:0
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);//index:1
            }
            Object[] params = list.toArray();//转换成数组
            System.out.println("当前的sql语句为------------>" + sql);
            rs = BaseDao.execute(connection, sql.toString(), params, rs, pstm);
            if (rs.next()) {
                count = rs.getInt("count");

            }
            BaseDao.closeConnection(null, pstm, rs);

        }
        return count;
    }

    @Override
    public List<User> getuserList(Connection conn, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
        List<User> userList = new ArrayList<User>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if (conn != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(conn, sql.toString(), params, rs, pstm);
            while (rs.next()) {
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeConnection(null, pstm, rs);
        }
        return userList;
    }


}

