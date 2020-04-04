package com.wwx.shop.controller.Admin;

import com.google.gson.Gson;
import com.wwx.shop.bean.Order;
import com.wwx.shop.bean.OrderParam;
import com.wwx.shop.bean.OrderResult;
import com.wwx.shop.bean.Result;
import com.wwx.shop.service.Admin.OrderService;
import com.wwx.shop.service.impl.Admin.OrderServiceImpl;
import com.wwx.shop.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/api/admin/order/*")
public class OrderServlet extends HttpServlet {

    Gson gson = new Gson();

    OrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/order/", "");
        if("ordersByPage".equals(action)){
            ordersByPage(request, response);
        }if ("changeOrder".equals(action))
        {
            changeOrder(request,response);
        }
    }

    /**
     * 保存修改其实就是保存状态id
     * @param request
     * @param response
     */
    private void changeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Order orderParam = gson.fromJson(requestBody, Order.class);
       int res= orderService.changeOrder(orderParam);
       Result result=new Result();
       if (res==200){
           result.setCode(0);
       }
       else {
           result.setCode(10000);
       }
       response.getWriter().println(gson.toJson(result));
    }

    /**
     * orderParam 来封装请求体的数据
     * order是数据库的映射
     * order result用来封装结果
     * @param request
     * @param response
     * @throws IOException
     */
    private void ordersByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        OrderParam orderParam = gson.fromJson(requestBody, OrderParam.class);
        OrderResult orderResult = orderService.ordersByPage(orderParam);
        Result result = new Result();
        result.setCode(0);
        result.setData(orderResult);
        response.getWriter().println(gson.toJson(result));
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//order
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/order/", "");
        System.out.println(action);
        if("order".equals(action)){
            System.out.println("11111111111");
            order(request, response);

        }if("deleteOrder".equals(action))
        {
            deleteOrder(request,response);
        }

    }

    /**
     * 删除（隐藏订单）
     * @param request
     * @param response
     */
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id=request.getParameter("id");
        orderService.deleteOrder(id);
        Result result=new Result();
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 显示想要修改的订单
     *通过观察返回的数据 修改orderresult表
     * @param request
     * @param response
     */

    private void order(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id=request.getParameter("id");
        OrderResult res=orderService.order(id);

        Result result=new Result();
        result.setCode(0);
        result.setData(res);
        response.getWriter().println(gson.toJson(result));

    }


}

