package com.wwx.shop.dao.impl.Admin;

import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Order;
import com.wwx.shop.bean.Spec;
import com.wwx.shop.bean.User;
import com.wwx.shop.dao.Admin.OrderDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
    /**
     * 查询total
     * @param sql
     * @param params
     * @return
     */
    @Override
    public Integer queryTotalCount(String sql, List<Object> params) {

        Long count = null;
        try {
            count = runner.query(sql, new ScalarHandler<>(), params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    @Override
    public List<Order> queryPageOrders(String sql, List<Object> params) {

        User user=new User();
        List<Order> orders = null;
        try {
          //  user=runner.query("select * from user where nickname=?", new BeanHandler<User>(User.class),);
            orders = runner.query(sql, new BeanListHandler<Order>(Order.class), params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //要对order里的user赋值 通过给的userid 去user表里查出来 同时前端需要的收件人为“name”，数据库中保存的是“recipient”
        for (int i=0;i<orders.size();i++){
            try {
                user=runner.query("select * from user where id=?",new BeanHandler<User>(User.class),orders.get(i).getUserId());
                user.setName(user.getRecipient());
                orders.get(i).setStateId(orders.get(i).getStateId());
                orders.get(i).setUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    @Override
    public Order order(String id) {

        Order order1=new Order();
        try {
            order1=runner.query("select * from orders where id =?",new BeanHandler<Order>(Order.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order1;
    }

    /**
     * 查找对应的商品id
     * @param goods
     * @return
     */
    @Override
    public int queryGoodsId(String goods) {

        Goods hw=new Goods();
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        try {
            hw=runner.query("select * from goods where name=?",new BeanHandler<Goods>(Goods.class),goods);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hw.getId();
    }

    /**
     * 查询当前订单的spec id 根据规格名字和商品id
     * @param spec
     * @param a
     * @return
     */
    @Override
    public int quertSpecId(String spec, Integer a) {

        Spec sp=new Spec();

        try {
            sp=runner.query("select * from spec where specname=? and goodsid=?",new BeanHandler<Spec>(Spec.class),spec,a);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sp.getId();
    }

    /**
     * 通过商品的id来查询这个商品所有的spec
     * @param id
     * @return
     */
    @Override
    public List<Spec> getSpec(int id) {

        List<Spec> goods=null;
        try {

            goods=runner.query("select  id,specName,stockNum,unitPrice from spec where goodsid = ?",new BeanListHandler<Spec>(Spec.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    @Override
    public int changeOrder(Order orderParam) {

        int res=-1;
        try {
            orderParam.setStateId(Integer.valueOf(orderParam.getState()));
            res=runner.update("update orders set stateId=?,state=? where id=?",orderParam.getStateId(),orderParam.getState(),orderParam.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res!=-1)
            return  200;
        return 0;
    }

    /**
     * shanchu
     * @param id
     */
    @Override
    public void deleteOrder(String id) {

        try {
            runner.update("update orders set history=0 where id=?",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

