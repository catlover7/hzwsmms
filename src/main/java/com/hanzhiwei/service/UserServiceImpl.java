package com.hanzhiwei.service;

import com.hanzhiwei.dao.BaseDao;
import com.hanzhiwei.dao.UserDaoImpl;
import com.hanzhiwei.pojo.User;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-29-22:10
 */
public class UserServiceImpl implements UserService{

    UserDaoImpl userDao = new UserDaoImpl();
    Connection conn = null;

    @Override
    public  User userlogin(String userCode, String userPassword) throws Exception {
        User user=null;

      try {
          conn = BaseDao.getConnection();

        user = userDao.getUser(conn, userCode);
       if(user.getUserPassword().equals(userPassword)){
            System.out.println("匹配成功 成功登录");
        }else {
           user=null;
           System.out.println("密码错误");
       }


      }

      catch (Exception e){
          e.printStackTrace();
      }finally {
          BaseDao.closeConnection(conn,null,null);

      }
      return user;
    }

    @Override
    public boolean modifypassword(int id, String userPassword) {
        boolean flag=false;
        try {

            conn=BaseDao.getConnection();
            int i = userDao.updatePassword(conn, id, userPassword);
            if(i>0){
                System.out.println("更改密码成功");
                flag=true;
            }else {
                System.out.println("failed to modify password");
                flag=false;

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
BaseDao.closeConnection(conn,null);
        }
        return flag;
    }

    @Override
    public int getUsercount(String username, int userRole) {
        //这里出现问题 一直没有跑出客户端页面语句
//        Connection connection = null;
//        int count=0;
//        try {
//            connection=BaseDao.getConnection();
//            count = userDao.getUsercount(conn, username, userRole);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeConnection(conn,null,null);
//        }
//        return count;
        int count=0;
        Connection connection=null;
        try {
            connection = BaseDao.getConnection();
            count = userDao.getUsercount(connection, username, userRole);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeConnection(connection, null, null);
        }
        return count;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
          // TODO Auto-generated method stub
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getuserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeConnection(connection, null, null);
        }
        return userList;
    }



    @Test
    public void test1() throws Exception {
        UserServiceImpl userService = new UserServiceImpl();
//用户调用 userlogin方法 实际上就是拿 usercode：admin放入参数列表中 如果 存在这个usercode 那么才能调用dao层的方法与数据库中的usercode一致
        //就找到了 只要实现到userdao层 那么肯定存在这个用户了
        User admin = userService.userlogin("admin", "111111");
        System.out.println(admin.getUserPassword());
    }

    @Test
    public void test2() {
        UserServiceImpl userService = new UserServiceImpl();
        boolean xjs = userService.modifypassword(2, "咸家胜");
        System.out.println(xjs);
    }
    @Test
    public void test3(){
        UserServiceImpl userService = new UserServiceImpl();
      //     int usercount = userService.getUsercount(null, 2);
        int usercount = userService.getUsercount("孙兴", 0);
        System.out.println(usercount);
    }

    @Test
    public void  test4(){
        UserServiceImpl userService = new UserServiceImpl();
       // userService.getUserList()
    }
}
