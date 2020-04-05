package com.wwx.shop.dao.impl.Admin;

import com.wwx.shop.bean.User;
import com.wwx.shop.dao.Admin.AdminUserDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class AdminUserDaoImpl implements AdminUserDao {

    @Override
    public List<User> quaryallUser() {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());

        List<User> users=null;
        try {
            users=runner.query("select * from user",new BeanListHandler<User>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<User> querySearchUser(String user) {

        List<User> adminList= null;
        String baseUrl="select * from user where 1=1";
        List<Object> list=new ArrayList<Object>();

        if (user!=null)
        {
            baseUrl=baseUrl+" and nickname like ?";
            list.add("%" + user + "%");
        }

        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        try {

            adminList=  runner.query(baseUrl,new BeanListHandler<User>(User.class),list.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  adminList;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */

    @Override
    public int deletUser(String id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int query =-1;
        try {
            query=runner.update("DELETE FROM user WHERE id=?",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }   if(query != -1){
            return 200;
    }
        return 500;

    }

}
