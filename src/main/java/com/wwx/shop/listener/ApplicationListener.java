/**
 * User: zsquirrel
 * Date: 2020/3/31
 * Time: 11:32 上午
 */
package com.wwx.shop.listener;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = new Properties();
        InputStream inputStream = ApplicationListener.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
            String domain = (String) properties.get("domain");
            servletContextEvent.getServletContext().setAttribute("domain",domain);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
