package com.wwx.shop.controller.Admin;

import com.google.gson.Gson;
import com.wwx.shop.bean.Result;
import com.wwx.shop.bean.User;
import com.wwx.shop.service.impl.Admin.AdminUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/user/*")
public class AdminUserServlet extends HttpServlet {
    private AdminUserServiceImpl adminUserService=new AdminUserServiceImpl();
    Gson gson=new Gson();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("userdoget");
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/user/", "");
        System.out.println(action);
        if ("allUser".equals(action))
        {
            allUser(request,response);
        }if("searchUser".equals(action)){
            searchUser(request,response);
        }if ("deleteUser".equals(action))
        {
            deleteUser(request,response);
        }
    }

    /**
     * 删除指定该用户
     * @param request
     * @param response
     */

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id=request.getParameter("id");
        int result=adminUserService.deletUser(id);
        Result result1=new Result();
        if (result==200)
        {
            result1.setCode(0);

        }else
        {
            result1.setCode(1);
        }

        response.getWriter().println(gson.toJson(result1));
    }

    /**
     * 搜索用户
     * @param request
     * @param response
     * @throws IOException
     */
    private void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String user=request.getParameter("word");
        List<User> admins = adminUserService.querySearchUser(user);
        Result result = new Result();
        result.setCode(0);
        result.setData(admins);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 展示所有的用户
     * @param request
     * @param response
     */
    private void allUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> userList=adminUserService.quaryallUser();
        Result result=new Result();
        result.setCode(0);
        result.setData(userList);
        response.getWriter().println(gson.toJson(result));
}
}
