/**
 * User: zsquirrel
 * Date: 2020/3/31
 * Time: 10:17 上午
 */
package com.wwx.shop.dao.impl.Admin;


import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Type;
import com.wwx.shop.dao.Admin.GoodsDao;
import com.wwx.shop.dao.Admin.TypeDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeDaoImpl implements TypeDao {
    QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
    /**
     * 得到类目
     * @return
     */
    @Override
    public List<Type> getType() {

        List<Type> types=new ArrayList<Type>();
        try {
            types=runner.query("select * from type",new  BeanListHandler<Type>(Type.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    /**
     * 添加类目
     * @param type
     * @return
     */
    @Override
    public int addType(Type type) {
        int result=-1;
        try
        {
            Type type1=runner.query("select * from type where name=?",new BeanHandler<Type>(Type.class),type.getName());
            if (type1!=null){
                return 300;
            }
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

    /**
     * 查询类目
     * @param type
     * @return
     */
    @Override
    public int queryType(Type type) {

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
        List<Goods> list=new ArrayList<Goods>();//获取该类目下的所有的商品
        try {
            runner.update("delete from type where id=?",id);
            list=runner.query("select * from goods where typeId=?",new BeanListHandler<Goods>(Goods.class),id);
            for (Goods good:list) {
                runner.update("delete from goods where typeId=?",id);
                runner.update("delete from spec where goodsid=?",good.getId());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
