/**
 * User: zsquirrel
 * Date: 2020/3/26
 * Time: 10:09 上午
 */
package com.wwx.shop.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidUtils {

    private static DataSource dataSource;

    static {
        Properties properties = new Properties();
        InputStream inputStream = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
}
