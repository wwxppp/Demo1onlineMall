package com.wwx.shop.controller.USer;

import com.google.gson.Gson;
import com.sun.javafx.collections.ListListenerHelper;
import com.wwx.shop.bean.*;
import com.wwx.shop.service.User.MallUserService;
import com.wwx.shop.service.impl.User.MallUserServiceImpl;
import com.wwx.shop.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/mall/user/*")
public class MallUserServlet extends HttpServlet {
    Gson gson=new Gson();
    private MallUserService mallUserService=new MallUserServiceImpl();
    Result result=new Result();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/mall/user/", "");
        if ("askGoodsMsg".equals(action)){
            askGoodsMsg(request,response);
        }
        if ("addOrder".equals(action)){
            addOrder(request,response);
        }
        if ("settleAccounts".equals(action))
        {
            settleAccounts(request,response);
        }
        if ("sendComment".equals(action)){
//api/mall/user/sendComment
            sendComment(request,response);
        }
    }

    /**
     * 发送评价
     * @param request
     * @param response
     */
    private void sendComment(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestBody = HttpUtils.getRequestBody(request);
        CommentResult commentResult =gson.fromJson(requestBody,CommentResult.class);
        System.out.println(commentResult);
        int res=mallUserService.sendComment(commentResult);
        if (res==200)
        {
            result.setCode(0);
        }
        else {
            result.setCode(1000);
        }
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 购物车下单
     * @param request
     * @param response
     */
    private void settleAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        OrderResult order=gson.fromJson(requestBody,OrderResult.class);
        int res=mallUserService.settleAccounts(order);
        if (res==200)
        {
            result.setCode(0);
        }
        else {
            result.setCode(1000);
        }
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 下单
     * @param request
     * @param response
     */
    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestBody = HttpUtils.getRequestBody(request);
        Order order=gson.fromJson(requestBody,Order.class);
        int res=   mallUserService.addOrder(order);
        if (res==200)
        {
            result.setCode(0);
        }else if (res==300){
            result.setCode(10000);
            result.setMessage("库存不足啦");
        }
        else {
            result.setCode(1000);
        }
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 用户留言
     * @param request
     * @param response
     */
    private void askGoodsMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Msg msg=gson.fromJson(requestBody,Msg.class);
         int res=mallUserService.askGoodsMsg(msg);
         if (res==250)
         {
             result.setCode(0);
         }else {
             result.setCode(10000);
         }
response.getWriter().println(gson.toJson(result));


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/mall/user/", "");
        if ("getOrderByState".equals(action)){
            getOrderByState(request,response);
        }
        if ("deleteOrder".equals(action))
        {
            deleteOrder(request,response);
        }
        if ("confirmReceive".equals(action)){
            confirmReceive(request,response);
        }
        if("searchGoods".equals(action)){
            searchGoods(request,response);
        }
    }

    /**
     * 用户模糊搜索商品
     * @param request
     * @param response
     */
    private void searchGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String keyword=request.getParameter("keyword");
     List<Goods> list=mallUserService.searchGoods(keyword);
     result.setCode(0);
     result.setData(list);

        response.getWriter().println(gson.toJson(result));






    }

    /**
     * 确认收货
     * @param request
     * @param response
     */
    private void confirmReceive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id=request.getParameter("id");
        mallUserService.confirmReceive(id);
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 删除订单
     * @param request
     * @param response
     */
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id=request.getParameter("id");
        int res=mallUserService.deleteOrder(id);
        if (res==200)
        {
            result.setCode(0);
        }
        else
        {
            result.setCode(10000);
        }
response.getWriter().println(gson.toJson(result));
    }

    /**
     * 得到订单
     * @param request
     * @param response
     */
    private void getOrderByState(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String state=request.getParameter("state");
        String token=request.getParameter("token");
      List list= mallUserService.getOrderByState(state,token);
result.setCode(0);
result.setData(list);
response.getWriter().println(gson.toJson(result));





    }
}
