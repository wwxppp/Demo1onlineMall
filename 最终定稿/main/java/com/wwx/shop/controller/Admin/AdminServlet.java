package com.wwx.shop.controller.Admin;

import com.google.gson.Gson;
import com.wwx.shop.bean.Admin;
import com.wwx.shop.bean.Msg;
import com.wwx.shop.bean.Result;
import com.wwx.shop.service.Admin.AdminService;
import com.wwx.shop.service.impl.Admin.AdminServiceImpl;
import com.wwx.shop.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/admin/*")
public class AdminServlet extends HttpServlet {
    private AdminService adminService = new AdminServiceImpl();
    Gson gson = new Gson();
    /**
     * 登录和新增 修改
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("dopost");
        String uri=request.getRequestURI();
        String action=  uri.replace("/api/admin/admin/","");
        System.out.println(action);
        if("login".equals(action)){
            login(request, response);
        }else if("addAdminss".equals(action)){
            System.out.println("addAdminss");
            addAdminss(request, response);
        }else if ("updateAdminss".equals(action))
        {
            System.out.println(action);
            updateAdminss(request,response);
        }
        else if ("getSearchAdmins".equals(action))
        {
            getSearchAdmins(request, response);
        }
        //changePwd
        else if ("changePwd".equals(action))
        {
            changePwd(request,response);
        }else if ("reply".equals(action)){

            reply(request,response);
        }else if ("logoutAdmin".equals(action)){
            logoutAdmin(request,response);
        }
    }

    /**
     * 注销登录
     * @param request
     * @param response
     */

    private void logoutAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        session.removeAttribute("email");
        session.invalidate();
        Result result=new Result();
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 回复留言
     * @param request
     * @param response
     */
    private void reply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Msg msg=gson.fromJson(requestBody,Msg.class);
        int res=adminService.reply(msg);
        Result result=new Result();
        if (res==200){
            result.setCode(0);
        }
        else {
            result.setCode(1);
        }
        response.getWriter().println(gson.toJson(result));
    }


    /**
     * 修改密码，需要先看旧密码对不对
     * 在serice层做一些逻辑操作
     * @param request
     * @param response
     * @throws IOException
     */
    private void changePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);

        int result=adminService.changePwd(admin);
        Result result1=new Result();
        if (result==200)
        {
            result1.setCode(0);
            System.out.println("code0");
        }else
        {
            result1.setCode(10000);
            result1.setMessage("出现异常");


        }

        response.getWriter().println(gson.toJson(result1));


    }

    private void getSearchAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        List<Admin> admins = adminService.querySearchAdmins(admin);
        Result result = new Result();
        result.setCode(0);
        result.setData(admins);
        response.getWriter().println(gson.toJson(result));
    }

    private void updateAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        int result=adminService.updateAdminss(admin);
        Result result1=new Result();
        if (result==200)
        {
            result1.setCode(0);
            System.out.println("code0");
        }else
        {
            result1.setCode(1);
        }

        response.getWriter().println(gson.toJson(result1));
    }

    /**
     * 添加新的管理员
     * @param request
     * @param response
     * @throws IOException
     */
    private void addAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        int result =adminService.addAdminss(admin);
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
     * 登录的业务逻辑实现
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        //调用service层
        int result = adminService.login(admin);
        Result res = new Result();
        if(result == 200){
            res.setCode(0);
            Map<String, String> map = new HashMap<>();
            map.put("name", admin.getEmail());
            map.put("token", admin.getPwd());
            res.setData(map);
            HttpSession session = request.getSession();
            session.setAttribute("email",admin.getEmail());
        }else if(result == 404){
            res.setCode(10000);
            res.setMessage("用户名或者密码错误");
        }else{
            res.setCode(10000);
            res.setMessage("当前访问异常，稍后重试");
        }
        response.getWriter().println(gson.toJson(res));
    }

    /**
     * 响应get方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/admin/", "");
        //getAdminsInfo
        if("allAdmins".equals(action)){

            allAdmins(request, response);
        }else if (action.contains("getAdminsInfo")){
            System.out.println(action);
            getAdminsInfo(request,response);
        }else  if ("deleteAdmins".equals(action))
        {
            deleteAdmins(request,response);

        }
        else  if ("noReplyMsg".equals(action)){
            noReplyMsg(request,response);
        }else if ("repliedMsg".equals(action)){
            repliedMsg(request,response);
        }
    }

    /**
     * 显示已经回复的消息
     * @param request
     * @param response
     */
    private void repliedMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Msg> msg=adminService.repliedMsg();
        Result result=new Result();
        result.setCode(0);
        result.setData(msg);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 处理未回复的消息
     * @param request
     * @param response
     */
    private void noReplyMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
       List<Msg> msgs=adminService.noReplyMsg();
       Result result=new Result();
       result.setCode(0);
       result.setData(msgs);
       response.getWriter().println(gson.toJson(result));
    }

    private void deleteAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id=request.getParameter("id");
        int result=adminService.deleteAdmins(id);
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

    private void getAdminsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

       String id=request.getParameter("id");
        List<Admin> admins=  adminService.getAdminsInfo(id);
        Result result = new Result();
        result.setCode(0);
        result.setData(admins.get(0));
        response.getWriter().println(gson.toJson(result));
    }

    private void allAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Admin> adminList = adminService.queryAllAdmins();
        Result result = new Result();
        result.setCode(0);
        result.setData(adminList);
        response.getWriter().println(gson.toJson(result));
    }
}
