package com.wwx.shop.controller.USer;

import com.google.gson.Gson;
import com.wwx.shop.bean.Admin;

import com.wwx.shop.bean.Result;
import com.wwx.shop.bean.User;
import com.wwx.shop.service.User.UserService;
import com.wwx.shop.service.impl.User.UserServiceImpl;
import com.wwx.shop.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/api/user/user/*")
public class UserServlet extends HttpServlet {
 private  UserService userService=new UserServiceImpl() ;
    Gson gson = new Gson();
    Result result=new Result();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/user/user/", "");
        if ("signup".equals(action)){
            signup(request,response);
        }
        if ("login".equals(action)){
            login(request,response);
        }
        if ("updatePwd".equals(action))
        {
            updatePwd(request,response);
        }
        if ("updateUserData".equals(action)){
            updateUserData(request,response);
        }
    }

    /**
     * 更正信息
     * @param request
     * @param response
     */
    private void updateUserData(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestbody=HttpUtils.getRequestBody(request);
        User user=gson.fromJson(requestbody,User.class);
        int res=userService.updateUserData(user);
        if (res==200)
        {
            result.setCode(0);
        }else
            result.setCode(10000);
        response.getWriter().println(gson.toJson(result));




    }

    /**
     * 修改密码
     * @param request
     * @param response
     */
    private void updatePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
String requestbody=HttpUtils.getRequestBody(request);
//因为admin中已经写好相关的属性了所以直接拿来用就好了
Admin admin=gson.fromJson(requestbody, Admin.class);
int res=userService.updatePwd(admin);
if (res==250){
    result.setCode(10000);
    result.setMessage("旧密码不正确");
}if (res==500){
    result.setCode(10000);
    result.setMessage("两次输入的密码不一致");
        }
if (res==200){
    result.setCode(0);
}
response.getWriter().println(gson.toJson(result));
    }


    /**
     * 用户登录
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //{"code":10000,"message":"账号或密码不正确!"}
        //{"code":0,"data":{"code":0,"name":"wewew","token":"wewew"}}
        String requestBody = HttpUtils.getRequestBody(request);
        User user =gson.fromJson(requestBody,User.class);
        User res=userService.login(user);
        if (res!=null){
            result.setCode(0);
            Map map  =new HashMap();
            map.put("code",0);
            map.put("name",res.getNickname());
            map.put("token",res.getNickname());
            result.setData(map);
            HttpSession session=request.getSession();
            session.setAttribute("name",res.getNickname());
        }
        else if (res==null)
        {
            result.setCode(10000);
            result.setMessage("帐号啊或密码不正确");
        }
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 用户注册
     * @param request
     * @param response
     */
    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
//"code":10000,"message":"该账号已存在!"
        String requestBody = HttpUtils.getRequestBody(request);
       User user =gson.fromJson(requestBody,User.class);
  int res=     userService.signup(user);
//{"code":0,"data":{"code":0,"name":"weqeqew","token":"weqeqew"}}
  if (res==200){
      result.setCode(0);
      Map map  =new HashMap();
      map.put("code",0);
      map.put("name",user.getNickname());
      map.put("token",user.getNickname());
      result.setData(map);
  }
  else if (res==500)
  {
      result.setCode(10000);
      result.setMessage("账号已经存在了");
  }
  else {
      result.setCode(0);
  }
  response.getWriter().println(gson.toJson(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/user/user/", "");
        if ("data".equals(action)){
            data(request,response);
        }if ("logoutClient".equals(action)){
            logoutClient(request,response);
        }
    }

    /**
     * 注销登录注销掉session
     * @param request
     * @param response
     */
    private void logoutClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        session.removeAttribute("name");
        session.invalidate();
        Result result=new Result();
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 显示当前用户的资料
     * @param request
     * @param response
     */
    private void data(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //token: guanliyuan
        String token=request.getParameter("token");
      Map map=  userService.data(token);
      result.setCode(0);
      result.setData(map);
      response.getWriter().println(gson.toJson(result));
    }
}
