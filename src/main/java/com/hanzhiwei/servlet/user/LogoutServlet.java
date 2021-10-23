package com.hanzhiwei.servlet.user;

import com.hanzhiwei.tools.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-30-15:43
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //想实现登出功能 实际上只是移除session中的user对象
        req.getSession().removeAttribute(Constants.USER_SESSION);
        resp.sendRedirect("/login.do");
    }
}
