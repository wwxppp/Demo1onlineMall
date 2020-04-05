package com.wwx.shop.controller.USer;

import com.google.gson.Gson;
import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Result;
import com.wwx.shop.bean.Spec;
import com.wwx.shop.service.Admin.GoodsService;
import com.wwx.shop.service.User.MallGoodService;
import com.wwx.shop.service.impl.Admin.GoodsServiceImpl;
import com.wwx.shop.service.impl.User.MallGoodServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/mall/goods/*")
public class MallGoodsServlet extends HttpServlet {
    MallGoodService mallGoodService=new MallGoodServiceImpl();
    Gson gson=new Gson();
    Result result=new Result();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//getGoodsInfo
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/mall/goods/", "");
        if ("getGoodsInfo".equals(action)){
            getGoodsInfo(request,response);
        }
        if("getGoodsMsg".equals(action)){
            getGoodsMsg(request,response);
        }if("getGoodsComment".equals(action))
        {
            getGoodsComment(request,response);
        }
    }

    /**
     * 获取商品的评价
     * @param request
     * @param response
     */

    private void getGoodsComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String goodsid=request.getParameter("goodsId");
        //封装都在serevice层
        Map map=mallGoodService.getGoodsComment(goodsid);
        result.setCode(0);
        result.setData(map);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 获取商品留言信息
     * @param request
     * @param response
     */

    private void getGoodsMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String goodsid=request.getParameter("id");
        List res= mallGoodService.getGoodsMsg(goodsid);
        result.setCode(0);
        result.setData(res);
        response.getWriter().println(gson.toJson(result));
    }

    /**获取商品详情
     * @param request
     * @param response
     */
    private void getGoodsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GoodsService goodsService=new GoodsServiceImpl();
        String id=request.getParameter("id");
        Goods goods=goodsService.getGoodsInfo(id);
        //应该在service层封装
        List<Spec> spec=goodsService.getSpec(id);
        System.out.println(goods);
        Map res1=new HashMap();
        res1.put("img",goods.getImg());
        res1.put("specs",spec);
        res1.put("name",goods.getName());
        res1.put("desc",goods.getDesc());
        res1.put("typeId",goods.getTypeId());
        result.setCode(0);
        result.setData(res1);
        response.getWriter().println(gson.toJson(result));
    }


}
