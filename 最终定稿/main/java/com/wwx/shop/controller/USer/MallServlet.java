package com.wwx.shop.controller.USer;

import com.google.gson.Gson;
import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Result;
import com.wwx.shop.bean.Type;
import com.wwx.shop.service.Admin.GoodsService;
import com.wwx.shop.service.Admin.TypeService;
import com.wwx.shop.service.impl.Admin.GoodsServiceImpl;
import com.wwx.shop.service.impl.Admin.TypeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/mall/mall/*")
public class MallServlet extends HttpServlet {
    Gson gson=new Gson();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/mall/mall/", "");
        if ("getType".equals(action)){
            getType(request,response);
        }if ("getGoodsByType".equals(action)){
            getGoodsByType(request,response);
        }
    }

    /**
     * 老方法了。。
     * @param request
     * @param response
     */
    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GoodsService goodsService=new GoodsServiceImpl();
        String id=request.getParameter("typeId");
        List<Goods> goods=goodsService.getGoodsByType(id);
        Result result=new Result();
        result.setCode(0);
        result.setData(goods);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 查找type 直接调用admin写好的方法
     * @param request
     * @param response
     */
    private void getType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TypeService typeService=new TypeServiceImpl();
        List<Type> types=typeService.getType();
        Result result=new Result();
        result.setCode(0);
        result.setData(types);
        response.getWriter().println(gson.toJson(result));
    }
}
