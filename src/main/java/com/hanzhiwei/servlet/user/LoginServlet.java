package com.hanzhiwei.servlet.user;

import com.hanzhiwei.pojo.User;
import com.hanzhiwei.service.UserServiceImpl;
import com.hanzhiwei.tools.Constants;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-30-14:36
 */

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从前端获取 用户键盘输入的usercode 和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        UserServiceImpl userService = new UserServiceImpl();
        User userlogin = null;
        try {
            //把用户输入的用户密码 作为 形参传递到业务层中 业务层再向下调用 如果存在这样的usercode 返回用户 如若不存在用户 则返回null
            userlogin = userService.userlogin(userCode, userPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(userlogin!=null){
            //查到此人 可以登录
            //将 此人的信息 存放到session中 用于登出 和 页面拦截
            System.out.println("登录成功");
            req.getSession().setAttribute(Constants.USER_SESSION,userlogin);
            resp.sendRedirect("jsp/frame.jsp");
        }else {
            //查无此人，无法登录
            req.setAttribute("error","用户名或密码不正确");
      req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }
}
