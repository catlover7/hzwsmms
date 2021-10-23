package com.hanzhiwei.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.hanzhiwei.pojo.Role;
import com.hanzhiwei.pojo.User;
import com.hanzhiwei.service.RoleService;
import com.hanzhiwei.service.RoleServiceImpl;
import com.hanzhiwei.service.UserServiceImpl;
import com.hanzhiwei.tools.Constants;
import com.mysql.cj.util.StringUtils;
import com.hanzhiwei.tools.PageSupport;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author 韩志伟
 * @Description
 * @create 2021-10-01-14:04
 */
public class UserServlet extends HttpServlet {
    public void init() throws ServletException {
        // Put your code here
    }
    public UserServlet() {
        super();
    }
    public void destroy() {
        super.destroy();
    }
    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method=req.getParameter("method");
        if(method.equals("savepwd")&&method!=null){
            this.UpdatePwd(req, resp);
        }else if(method.equals("query")){
            try {
                this.query(req,resp);
            } catch (Exception e) {
e.printStackTrace();
            }
        }else if(method != null && method.equals("getrolelist")){
            //查询用户角色表
            try {
                this.getRoleList(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
        //同一个用户只有一个session 那么可以从session这里获取id
        //o就是存放到session中的user
        //修改密码
        public void UpdatePwd(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
            //password从前端pwdmodify.jsp中获得
            Object o = req.getSession().getAttribute(Constants.USER_SESSION);
            User user = (User) o;
            int id = user.getId();
            String newpassword = req.getParameter("newpassword");
            System.out.println("用户新密码" + newpassword);
            boolean flag = false;
            if (user != null && !StringUtils.isNullOrEmpty(newpassword)) {

                UserServiceImpl userService = new UserServiceImpl();
                flag = userService.modifypassword(id, newpassword);
                if (flag) {
                    req.setAttribute("message", "密码修改成功请退出 使用新密码登录");
                    //免密修改成功 移除当前session
                    req.getSession().removeAttribute(Constants.USER_SESSION);
                    //这里没有重定向回首页 是因为已经实现了过滤器

                } else {
                    req.setAttribute("message", "密码修改失败");

                }
            } else {
                req.setAttribute("message", "新密码有问题");
            }
            //修改完成 无论是否修改密码成功都 重新登录这个页面
            req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
        }
        //重点 难点
        public void query(HttpServletRequest req,HttpServletResponse resp) throws Exception {
            //接收前端传来的参数
            String queryUserName = req.getParameter("queryname");
            String temp = req.getParameter("queryUserRole");//从前端传回来的用户角色码不知是否为空或者是有效角色码，所以暂存起来
            String pageIndex = req.getParameter("pageIndex");
            int queryUserRole=0;
            //先设置一个默认的用户角色码，若temp为空，则将这个传进sql语句中，这是真正放进sql语句中的角色码
        /*
            if(userRole > 0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            这句便不会被执行
         */
            //通过UserServiceImpl得到用户列表,用户数
            UserServiceImpl userService = new UserServiceImpl();
            //通过RoleServiceImpl得到角色表
            RoleService roleService = new RoleServiceImpl();
            List<User> userList=null;//用来存储用户列表
            List<Role> roleList=null;//用来存储角色表
            //设置每页显示的页面容量
            int pageSize=Constants.pageSize;
            //设置当前的默认页码
            int currentPageNo=1;
            //输出控制台，显示参数的当前值
            System.out.println("queryUserName servlet--------"+queryUserName);
            System.out.println("queryUserRole servlet--------"+queryUserRole);
            System.out.println("query pageIndex--------- > " + pageIndex);

            //前端传来的参数若不符合查询sql语句，即如果用户不进行设置，值为空会影响sql查询，需要给它们进行一些约束
            if(queryUserName==null){//这里为空，说明用户没有输入要查询的用户名，则sql语句传值为""，%%，会查询所有记录
                queryUserName="";
            }
            if(temp!=null && !temp.equals("")){
                //不为空，说明前端有传来的用户所设置的userCode，更新真正的角色码
                queryUserRole=Integer.parseInt(temp);//强制转换，前端传递的参数都是默认字符串，要转成int类型
            }

            if(pageIndex!=null){//说明当前用户有进行设置跳转页面
                currentPageNo=Integer.valueOf(pageIndex);
            }

            //有了用户名和用户角色后可以开始查询了，所以需要显示当前查询到的总记录条数
            int totalCount = userService.getUsercount(queryUserName, queryUserRole);
            //根据总记录条数以及当前每页的页面容量可以算出，一共有几页，以及最后一页的显示条数
            PageSupport pageSupport = new PageSupport();
            pageSupport.setCurrentPageNo(currentPageNo);
            pageSupport.setPageSize(pageSize);
            pageSupport.setTotalCount(totalCount);
            //可显示的总页数
            int totalPageCount=pageSupport.getTotalPageCount();

            //约束首位页，即防止用户输入的页面索引小于1或者大于总页数
            if(currentPageNo<1){
                currentPageNo=1;
            }else if(currentPageNo>totalPageCount){
                currentPageNo=totalPageCount;
            }
            //有了，待查询条件，当前页码，以及每页的页面容量后，就可以给出每页的具体显示情况了
            userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
            roleList = roleService.getRoleList();
            //得到了用户表与角色表以及各种经过处理后的参数，都存进req中
            req.setAttribute("userList",userList);
            req.setAttribute("roleList",roleList);
            req.setAttribute("queryUserName", queryUserName);
            req.setAttribute("queryUserRole", queryUserRole);
            req.setAttribute("totalPageCount", totalPageCount);
            req.setAttribute("totalCount", totalCount);
            req.setAttribute("currentPageNo", currentPageNo);
            //将所得到的的所有req参数送回给前端
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);

        }
    //得到用户角色表
    private void getRoleList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Role> roleList = null;
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        //把roleList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(roleList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    }

