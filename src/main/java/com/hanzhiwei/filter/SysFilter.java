package com.hanzhiwei.filter;

import com.hanzhiwei.pojo.User;
import com.hanzhiwei.tools.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-09-30-15:55
 */
public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //过滤器 从session获取对象
        User user = (User)req.getSession().getAttribute(Constants.USER_SESSION);
        //已经被移除 或注销了 或者没有登录
        if(user == null){
            resp.sendRedirect("/error.jsp");
        }else {
          chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
