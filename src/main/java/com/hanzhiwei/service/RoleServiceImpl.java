package com.hanzhiwei.service;

import com.hanzhiwei.dao.BaseDao;
import com.hanzhiwei.dao.RoleDao;
import com.hanzhiwei.dao.RoleDaoImpl;
import com.hanzhiwei.pojo.Role;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-10-02-21:45
 */
public class RoleServiceImpl implements RoleService {
    //引入dao
private RoleDao roleDao;
public RoleServiceImpl(){
    roleDao=new RoleDaoImpl();
}
    @Override
    public List<Role> getRoleList() throws Exception {
    Connection conn=null;
    List<Role> roleList=null;
        try {
            conn = BaseDao.getConnection();
            roleList = roleDao.getRoleList(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeConnection(conn,null,null);
        }
        return roleList;

    }



    @Test
    public void test() throws Exception {
        RoleServiceImpl  roleService =new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        for (Role role : roleList) {
            System.out.println(role);
        }
    }
}
