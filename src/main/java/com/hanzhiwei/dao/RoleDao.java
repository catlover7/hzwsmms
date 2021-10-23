package com.hanzhiwei.dao;

import com.hanzhiwei.pojo.Role;

import java.sql.Connection;
import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-10-02-21:43
 */
public interface RoleDao {
    //获取角色列表
    public List<Role> getRoleList(Connection conn)throws Exception;
}
