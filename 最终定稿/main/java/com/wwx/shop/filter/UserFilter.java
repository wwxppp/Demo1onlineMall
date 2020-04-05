package com.wwx.shop.filter;
import com.google.gson.Gson;
import com.wwx.shop.bean.Result;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/mall/*")
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");//允许跨域传输session
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials","true");
        if(!method.equalsIgnoreCase("OPTIONS")){
            if(auth(requestURI)){
                //通过是否又值来判断是否登录，登录方法里设置一个共享对象
                String email = (String) request.getSession().getAttribute("name");
                if(email == null){
                    Result result = new Result();
                    result.setCode(10000);
                    result.setMessage("给爷爬");
                    response.getWriter().println(new Gson().toJson(result));
                    return;
                }
            }
        }
        chain.doFilter(request, response);

    }

    /**
     * 判断是否允许通行
     * @param requestURI
     * @return
     */
    private boolean auth(String requestURI) {

        if (requestURI.contains("/api/mall/mall")){
            return  false;
        }
        if (requestURI.contains("/api/user")){
            return  false;
        }
        if (requestURI.contains("/api/mall/goods")){
            return  false;
        }
        return true;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

