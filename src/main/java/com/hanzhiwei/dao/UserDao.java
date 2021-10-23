package com.hanzhiwei.dao;

import com.hanzhiwei.pojo.Role;
import com.hanzhiwei.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-29-21:51
 */
public interface UserDao {
    //从数据库中取出对象只需要一个参数就够了 通过 sql语句 实现登录操作
    public User getUser(Connection conn,String userCode) throws Exception;

    //修改当前用户密码 通过从前端获得的id 对数据库中的密码实现修改 修改后变为从前端传入的password
    public int updatePassword(Connection conn,int id,String password) throws Exception;

    //根据用户名或角色查询用户总数
    public int getUsercount(Connection conn,String username,int userRole) throws Exception;

    //通过条件查询userlist
    public List<User> getuserList(Connection conn,String userName,int userRole,int currentPageNo,int pageSize) throws SQLException;
}
