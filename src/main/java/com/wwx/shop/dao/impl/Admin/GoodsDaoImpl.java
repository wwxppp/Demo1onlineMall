/**
 * User: zsquirrel
 * Date: 2020/3/31
 * Time: 10:35 上午
 */
package com.wwx.shop.dao.impl.Admin;


import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Spec;
import com.wwx.shop.dao.Admin.GoodsDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;


public class GoodsDaoImpl implements GoodsDao {

    @Override
    public int addGoods(Goods goods) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("insert into goods values (null,?,?,?,?,?,?)",
                    goods.getName(),
                    goods.getImg(),
                    goods.getDesc(),
                    goods.getTypeId(),
                      goods.getStockNum(),
                      goods.getPrice());

        } catch (SQLException e) {
            e.printStackTrace();
            return 500;
        }
        return 200;
    }

    @Override
    public int queryLastId() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        BigInteger query = null;
        try {
            query = runner.query("select last_insert_id()", new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query.intValue();
    }

    @Override
    public void addSpeces(List<Spec> specList, int goodsId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        for (Spec spec : specList) {
            try {
                runner.update("insert into spec values (null,?,?,?,?)",
                        spec.getSpecName(),
                        spec.getStockNum(),
                        spec.getUnitPrice(),
                        goodsId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 这个方法前台和后台共同使用
     *
     * 当typeid=-1的时候返回所有的商品
     * @param id
     * @return
     */
    @Override
    public List<Goods> getGoodByType(String id) {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        List<Goods> goods= null;


        try {
            //"id":509,"img":,"name":"半身裙","price":1.0,"typeId":1,"stockNum":1}
  if (id.equals("-1")){
      goods=runner.query("select * from goods",new BeanListHandler<Goods>(Goods.class));
  }else
      goods=runner.query("select * from goods where typeId= ?",new BeanListHandler<Goods>(Goods.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  goods;
    }

    /**
     * 获取商品的goods表里的信息
     * @param id
     * @return
     */
    @Override
    public Goods getGoodsInfo(String id) {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
     Goods goods= null;
        try {
            //id":521,"img":""米奇","desc":"大啊的","typeId":190,"unitPrice":0.0
            goods=runner.query("select  id,img,`desc`,typeId,price from goods where id = ?",new BeanHandler<Goods>(Goods.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    /**
     * 获取spec表里的信息
     * @param id
     * @return
     */
    @Override
    public List<Spec> getSpec(String id) {
        //"id":1340,"specName":"大","stockNum":1,"unitPrice":1.0
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
      List<Spec> goods=null;
        try {

            goods=runner.query("select  id,specName,stockNum,unitPrice from spec where goodsid = ?",new BeanListHandler<Spec>(Spec.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    @Override
    public int updateGoods(Goods goods) {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        int a=-1;
      int b=-1;
        try {
            a=runner.update("update goods set name=? ,typeId=? ,img=?,`desc`=? where id=?",
                    goods.getName(),
                    goods.getTypeId(),
                    goods.getImg(),
                    goods.getDesc(),
                    goods.getId());
            //id: 6, specName: "大132", stockNum: 122, unitPrice: 122
            List<Spec> specs=goods.getSpecList();
            for (int i=0;i<specs.size();i++){
            b=runner.update("update spec set specName=?,stockNum=?,unitPrice=? where id=?",
                    specs.get(i).getSpecName(),
                    specs.get(i).getStockNum(),
                    specs.get(i).getUnitPrice(),
                    specs.get(i).getId());}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (a!=-1&&b!=-1){
            return 200;
        }return 500;


    }

    @Override
    public int addSpec(Spec spec) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int a=-1;
        try {
            a=    runner.update("insert into spec values (null,?,?,?,?)",
                        spec.getSpecName(),
                        spec.getStockNum(),
                        spec.getUnitPrice(),
                       spec.getGoodsId());
            } catch (SQLException e) {
                e.printStackTrace();

        }
        if (a!=-1){
            return 20;
        }
        return 1;
    }

    @Override
    public int deleteGoods(String id) {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("DELETE FROM goods WHERE id=?",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 200;
    }
}
