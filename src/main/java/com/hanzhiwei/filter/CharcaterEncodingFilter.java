package com.hanzhiwei.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 韩志伟
 * @Description 对项目所有信息添加过滤器 使得可以不出现乱码
 * @create 2021-09-30-15:12
 */
public class CharcaterEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
    chain.doFilter(request,response);}

    @Override
    public void destroy() {

    }
}
