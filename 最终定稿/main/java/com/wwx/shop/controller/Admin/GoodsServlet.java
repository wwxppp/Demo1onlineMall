package com.wwx.shop.controller.Admin;


import com.google.gson.*;
import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Result;
import com.wwx.shop.bean.Spec;
import com.wwx.shop.service.Admin.GoodsService;
import com.wwx.shop.service.impl.Admin.GoodsServiceImpl;
import com.wwx.shop.utils.FileUploadUtils;
import com.wwx.shop.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/api/admin/goods/*")
public class GoodsServlet extends HttpServlet {

GoodsService goodsService=new GoodsServiceImpl();

    Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/goods/", "");
        if("imgUpload".equals(action)){
            imgUpload(request, response);
        }else if("addGoods".equals(action)){
            addGoods(request, response);
        }else if ("updateGoods".equals(action))
        {
            updateGoods(request,response);
        }
        else if ("addSpec".equals(action))
        {
            addSpec(request,response);
        }
    }

    /**
     * 添加类目
     * @param request
     * @param response
     * @throws IOException
     */
    private void addSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
       Spec spec = gson.fromJson(requestBody,Spec.class);

     int a=  goodsService.addSpec(spec);
     Result b=new Result();
     if (a==20){
         b.setCode(0);
     }else
     {b.setCode(1);}
    response.getWriter().println(gson.toJson(b));
    }

    /**
     * 执行修改操作
     * 首先我们的规格是保存在sepc表格里的
     * 我们的其余信息保存在goods表格里。
     * 因为对规格的删除和增加是另外的serverlei实现，因此我们只需要关注修改就可以了
     * 言外之意，所有的数据在数据库中都存在 只需要用update来修改
     * @param request
     * @param response
     */
    private void updateGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Goods goods = gson.fromJson(requestBody, Goods.class);
        System.out.println(goods);
      int r=  goodsService.updateGoods(goods);
      Result result=new Result();
if (r==200){
    result.setCode(0);
}
else {
    result.setCode(10000);}
    response.getWriter().println(gson.toJson(result));
    }


    /**
     * 新增商品的具体逻辑
     * 思路同以前
     * 将请求体的json字符串封装到java对象中
     * @param request
     * @param response
     */
    private void addGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Goods goods = gson.fromJson(requestBody, Goods.class);
        int result = goodsService.addGoods(goods);
        Result rs = new Result();
        if(result == 200){
            rs.setCode(0);
        }
        if (result==300){
            rs.setCode(10000);
            rs.setMessage("商品已经存在");
        }
        if (result==500){
            rs.setCode(10000);
            rs.setMessage("系统维护中。。");
        }
        response.getWriter().println(gson.toJson(rs));
    }


    /** 商品上传
     * @param request
     * @param response
     */
    private void imgUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String domain = (String) request.getServletContext().getAttribute("domain");
        System.out.println(domain);
        Map<String, Object> map = FileUploadUtils.parseRequest(request);
        String file = (String) map.get("file");
        file = domain + file;
        Result result = new Result();
        result.setCode(0);
        result.setData(file);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * doget方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/goods/", "");
        if("getGoodsByType".equals(action)){
            getGoodsByType(request, response);
        }if ("getGoodsInfo".equals(action)){
            getGoodsInfo(request,response);
        }if("deleteGoods".equals(action)){
            deleteGoods(request,response);
        }
    }

    /**
     * 删除商品的方法
     * @param request
     * @param response
     */
    private void deleteGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id=request.getParameter("id");
    int res=goodsService.deleteGoods(id);
    Result result=new Result();
    if (res==200)
    {
        result.setCode(0);
    }else {
        result.setCode(1000);
    }
    response.getWriter().println(gson.toJson(result));
    }

    /**
     * 获取商品的详细信息
     * @param request
     * @param response
     * @throws IOException
     */

    private void getGoodsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id=request.getParameter("id");
        Goods goods=goodsService.getGoodsInfo(id);
        List<Spec> spec=goodsService.getSpec(id);
        Map res1=new HashMap();
        res1.put("goods",goods);
        res1.put("specs",spec);
        Result result=new Result();
        result.setCode(0);
        result.setData(res1);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 获取某个商品类目下所有商品的逻辑
     * 1.请求参数
     * 2.数据库处理
     * 3.返回结果
     * @param request
     * @param response
     */
    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) throws IOException {

         String id=request.getParameter("typeId");
         List<Goods> goods=goodsService.getGoodsByType(id);
         Result result=new Result();
        result.setCode(0);
        result.setData(goods);
        response.getWriter().println(gson.toJson(result));
    }
}
