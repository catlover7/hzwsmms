package com.hanzhiwei.service;

import com.hanzhiwei.pojo.Role;
import com.hanzhiwei.pojo.User;

import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-29-22:04
 */
public interface UserService {
    //用户登录
    public User userlogin(String userCode, String userPassword) throws Exception;

    //用户修改密码
    public boolean modifypassword(int id,String userPassword);
    //查询记录数
    public int getUsercount(String username,int userRole);
    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

}
