/**
 * User: zsquirrel
 * Date: 2020/3/28
 * Time: 2:46 下午
 */
package com.wwx.shop.utils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpUtils {

    /**
     * 读取request请求报文的请求体，并将其以字符串的形式返回
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        //用这个来接收
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1){
            outputStream.write(bytes, 0, length);
        }
        //该字符串就是我们的请求体
        return outputStream.toString("utf-8");

    }
}
