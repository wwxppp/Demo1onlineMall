package com.wwx.shop.dao.impl.User;

import com.wwx.shop.bean.*;
import com.wwx.shop.dao.User.MallUserDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MallUserDaoImpl implements MallUserDao {
QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
    /**
     * 提交评价
     * @param msg
     * @return
     */
    @Override
    public int askGoodsMsg(Msg msg) {
        int res=0;
        //token: "guanliyuan"
        //msg: "号码a"
      //goodsId: "14"
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        try {
            User user=runner.query("select * from user where nickname=?",new BeanHandler<User>(User.class),msg.getToken());
            res=runner.update("insert into msg values(null,?,?,1,?,?,null,null)",
                    user.getId(),
                    msg.getGoodsId(),
                    df.format(new Date()),
                    msg.getMsg()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
if (res!=0)
{
    return 250;
}
        return 0;
    }

    /**
     * 新建一个订单 包括下单和添加购物车
     * 添加购物车即为未付款
     * @param order
     * @return
     */
    @Override
    public int addOrder(Order order) {
        int res=-1;
        int s=-1;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
           // System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            User user=runner.query("select * from user where nickname=?",new BeanHandler<User>(User.class),order.getToken());
            Spec spec=runner.query("select * from spec where id=?",new BeanHandler<Spec>(Spec.class),order.getGoodsDetailId());
            //判断该种类库存是否充足
            if (Integer.valueOf(order.getState())==1&&spec.getStockNum()-order.getNum()<0)
            {
                return 300;
            }
            Goods goods=runner.query("select * from goods where id=?",new BeanHandler<Goods>(Goods.class),spec.getGoodsId());
            order.setStateId(Integer.valueOf(order.getState()));
            res=runner.update("insert into orders values(null,?,?,?,?,?,?,?,?,1,?)",
                    user.getId(),
                    order.getGoodsDetailId(),
                    goods.getName(),
                    spec.getSpecName(),
                    order.getNum(),
                    order.getAmount(),
                    order.getStateId(),
                    order.getState(),
                    df.format(new Date())
                    );
            //状态码为1 即付款下单了的时候我们要将对应的库存减一
            if (order.getStateId()==1){
            runner.update("update spec set stocknum=? where id=?",spec.getStockNum()-1,spec.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res!=-1){
            return  200;
        }
        return 0;
    }

    @Override
    public List getOrderByState(String state, String token) {
        List<Order> order=new ArrayList<Order>();
        List list=new ArrayList();
        try {
            User user=runner.query("select * from user where nickname=?",new BeanHandler<User>(User.class),token);
          if (Integer.valueOf(state)==-1){

                  order=runner.query("select * from orders where userId=? and history=1 ",
                         new BeanListHandler<Order>(Order.class),user.getId());
              }
          else {
               order = runner.query("select * from orders where userId=? and history =1 and stateId=?",
                       new BeanListHandler<Order>(Order.class), user.getId(),state);
          }
          for (Order order1:order) {
              Goods goods = runner.query("select * from goods where name=?", new BeanHandler<Goods>(Goods.class), order1.getGoods());
              Comments comments = runner.query("select * from comment where orderid=?", new BeanHandler<Comments>(Comments.class), order1.getId());
              Map map = new HashMap();
              Map map1=new HashMap();
              map.put("id", order1.getId());
              map.put("state", order1.getStateId());
              map.put("goodsNum", order1.getGoodsNum());
              map.put("amount",order1.getAmount());
              map.put("goodsDetailId",order1.getGoodsDetailId());
              map.put("createtime",order1.getCreatetime());
              if (comments!=null)
              { map.put("hasComment",true);}
              else
              {  map.put("hasComment",false);}
              map1.put("id",goods.getId());
              map1.put("img",goods.getImg());
              map1.put("name",goods.getName());
              map1.put("goodsDetailId",order1.getGoodsDetailId());
              map1.put("spec",order1.getSpec());
              map1.put("unitPrice",order1.getAmount());
              map.put("goods",map1);
              list.add(map);

          }}
 catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int deleteOrder(String id) {
        int res=-1;
        try {
            res=runner.update("update orders set history=0 where id=?",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res!=-1)
        {
            return 200;
        }
        return 0;
    }

    /**
     * 购物车下单
     * @param order
     * @return
     */
    @Override
    public int settleAccounts(OrderResult order) {
        List<Order> list=order.getCartList();
        int res=-1;
        for (Order orders:list) {
            try {
                res=runner.update("update orders set stateId=1,goodsNum=?,amount=? where id=?",
                        orders.getGoodsNum(),
                        orders.getAmount(),
                        orders.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (res!=-1)
        {
        return 200;
        }
        return 0;
    }

    @Override
    public void confirmReceive(String id) {
        try {
            runner.update("update orders set stateId=3,state='已到货'where id=?",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送评价
     * @param commentResult
     * @return
     */
    @Override
    public int sendComment(CommentResult commentResult) {
        int res=-1;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            // System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            User user=runner.query("select * from user where nickname=?",new BeanHandler<User>(User.class),commentResult.getToken());
            Goods goods=runner.query("select * from goods where id=?",new BeanHandler<Goods>(Goods.class),
                    commentResult.getGoodsId());
            Spec spec=runner.query("select * from spec where id=?",new BeanHandler<Spec>(Spec.class),commentResult.getGoodsDetailId());
            res=runner.update("insert into comment values(null,?,?,?,?,?,?,?,?,?,null)",
                    user.getId(),
                    commentResult.getToken(),
                    goods.getId(),
                    goods.getName(),
                    commentResult.getOrderId(),
                    commentResult.getContent(),
                    commentResult.getScore(),
                    spec.getSpecName(),
                    df.format(new Date()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
if (res!=-1){
    return  200;
}
        return 0;
    }

    /**
     * 搜索商品
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> searchGoods(String keyword) {
        List<Goods> list=new ArrayList<Goods>();

        try {
            String sql="select id,img,name,price,typeId from goods where name like"+"'%"+keyword+"%';";
            System.out.println(sql);
            list=runner.query(sql ,
                    new BeanListHandler<Goods>(Goods.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
