package com.hanzhiwei.service;

import com.hanzhiwei.pojo.Role;

import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-10-02-21:45
 */
public interface RoleService {
    //得到用户角色表
    public List<Role> getRoleList() throws Exception;
}
