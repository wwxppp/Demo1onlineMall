/**
 * User: zsquirrel
 * Date: 2020/3/31
 * Time: 10:17 上午
 */
package com.wwx.shop.dao.impl.Admin;


import com.wwx.shop.bean.Type;
import com.wwx.shop.dao.Admin.TypeDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeDaoImpl implements TypeDao {

    @Override
    public List<Type> getType() {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        List<Type> types=new ArrayList<Type>();
        try {
            types=runner.query("select * from type",new  BeanListHandler<Type>(Type.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    @Override
    public int addType(Type type) {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        int result=-1;
        try
        {
        result=runner.update("insert into type values(null,?)",type.getName());}
        catch (SQLException e){
            e.printStackTrace();

        }
        if (result!=-1)
        {
            return 200;
        }
        return 500;
    }

    @Override
    public int queryType(Type type) {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        Type type1=null;
        try{
        type1=runner.query("select * from type where name=?",new BeanHandler<Type>(Type.class),type.getName());}
        catch (SQLException e) {
            e.printStackTrace();
        }
        if (type1!=null)
        {return 0;}
        else {
            return 1;
        }

    }

    /**
     * 删除类目和该类目下的所有商品
     * @param id
     */
    @Override
    public void deletType(String id) {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("delete from type where id=?",id);
            runner.update("delete from goods where typeId=?",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
