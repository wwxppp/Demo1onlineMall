
package com.wwx.shop.controller.Admin;


import com.google.gson.Gson;
import com.wwx.shop.bean.Result;
import com.wwx.shop.bean.Type;
import com.wwx.shop.service.Admin.TypeService;
import com.wwx.shop.service.impl.Admin.TypeServiceImpl;
import com.wwx.shop.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/type/*")
public class TypeServlet extends HttpServlet {
TypeService typeService=new TypeServiceImpl();
    Gson gson = new Gson();



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String op = requestURI.replace("/api/admin/type/", "");
        System.out.println(op);
        if("getType".equals(op)){
            getType(request,response);
        }

        else  if ("/deleteType".equals(op)){
            deleteType(request,response);
        }
    }

    /**
     * 删除类目
     * 删除该类目的同时要把该类目下的所有的商品删除掉
     * @param request
     * @param response
     */
    private void deleteType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id=request.getParameter("typeId");
        typeService.deleteType(id);
        Result result=new Result();
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));

    }

    /**
     * 无需参数，获取所有商品的分类、类目
     * @param request
     * @param response
     */
    private void getType(HttpServletRequest request, HttpServletResponse response) throws IOException {
       List<Type> types=typeService.getType();
        Result result=new Result();
        result.setCode(0);
        result.setData(types);
        response.getWriter().println(gson.toJson(result));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String op = requestURI.replace("/api/admin/type/", "");
        System.out.println(op);
        if("addType".equals(op)){
          addType(req,resp);
        }
    }

    /**
     * 添加类目，传一个name的参数，返回一个code0
     * @param req
     * @param resp
     */
    private void addType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
         String requestBody = HttpUtils.getRequestBody(req);

        Type type=gson.fromJson(requestBody,Type.class);
        int res=typeService.addType(type);
       Result result=new Result();
        if (res==200){
            result.setCode(0);

        }else if (res==500)
        { result.setCode(1);}
        else  if (res==300)
        {
            result.setCode(10000);
            result.setMessage("该品类已经存在！");
        }
        resp.getWriter().println(gson.toJson(result));

    }
}
