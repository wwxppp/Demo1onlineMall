/**
 * User: zsquirrel
 * Date: 2020/3/20
 * Time: 2:42 下午
 */
package com.wwx.shop.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class FileUploadUtils {

    public static Map<String, Object> parseRequest(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = request.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        //创建一个文件上传的处理器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //如果文件名中存在中文，如果乱码，可以这么解决
        upload.setHeaderEncoding("UTF-8");
        Map<String, Object> params = new HashMap<>();
        //最核心的一步
        try {
            //设置单个文件的上传大小限制 bytes  5M
            //upload.setFileSizeMax(100);
            //设置完了之后才解析
            List<FileItem> items = upload.parseRequest(request);
            //允许提交文件的最大容量
            Iterator<FileItem> iterator = items.iterator();
            //核心代码
            while (iterator.hasNext()){
                FileItem fileItem = iterator.next();
                //就要判断它究竟是一个普通的form表单数据还是上传的文件
                if(fileItem.isFormField()){
                    //表示该item是一个普通的form表单数据
                    //假设处理form表单以及上传的文件逻辑还是比较复杂
                    //如果一直在这里面接着往下写，会显得代码很长，很复杂
                    //而且也不容易看
                    //可以将这部分代码写到一个方法中
                    //如何优化
                    processFormField(fileItem, params);
                } else{
                    processUploadedFile(fileItem, request, params);
                    //是一个上传的文件
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 处理上传的文件具体代码逻辑
     * @param fileItem
     * @param request
     * @param map
     */
    private static void processUploadedFile(FileItem fileItem, HttpServletRequest request, Map<String, Object> map) throws Exception {
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid:" + uuid);
        //fieldName代表前端form表单提交的input的name属性
        String fieldName = fileItem.getFieldName();
        String fileName = fileItem.getName();
        // c3c87f85-ed2f-400e-8bcf-07aff3bc70cb-1.jpg
        fileName = uuid + "-" + fileName;
        String contentType = fileItem.getContentType();
        System.out.println("UploadedFile:" + fieldName + "=" + fileName + "," + contentType);
        int hashCode = fileName.hashCode();
        //8位数字  每位数字当做一个目录  8 9 4 5 e a f d / filename   10亿 16^8
        //                           8 9 3 ....
        String hexString = Integer.toHexString(hashCode);
        String basePath = "/upload";
        char[] charArray = hexString.toCharArray();
        for (char c : charArray) {
            basePath = basePath + "/" + c;
        }
        //需求，将上传的文件保存到web目录下的upload目录里  upload/2.jpg
        //这个relativePath就是需要保存到数据库中的路径
        String relativePath = basePath + "/" + fileName;
        String realPath = request.getServletContext().getRealPath(relativePath);
        File file = new File(realPath);
        //如果upload目录不存在，则会爆出FileNotFoundException
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        fileItem.write(file);
        map.put(fieldName, relativePath);
        //user.setImage(relativePath);
    }

    /**
     * 处理常规的form表单数据的代码逻辑
     * @param fileItem
     * @param map
     */
    private static void processFormField(FileItem fileItem, Map<String, Object> map) throws UnsupportedEncodingException {
        //获取的是input的name属性
        String fieldName = fileItem.getFieldName();
        //获取的是input的具体内容
        //这个时候我们发现获取的普通form表单数据中文存在乱码问题
        String value = fileItem.getString("UTF-8");
        System.out.println("formField:" + fieldName + "=" + value);
        //封装到对象中 反射 BeanUtils
        map.put(fieldName, value);
    }
}
