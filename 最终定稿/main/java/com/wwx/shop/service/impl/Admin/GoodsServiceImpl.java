/**
 * User: zsquirrel
 * Date: 2020/3/31
 * Time: 10:32 上午
 */
package com.wwx.shop.service.impl.Admin;



import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Spec;
import com.wwx.shop.dao.Admin.GoodsDao;
import com.wwx.shop.dao.impl.Admin.GoodsDaoImpl;
import com.wwx.shop.service.Admin.GoodsService;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {
GoodsDao goodsDao=new GoodsDaoImpl();

    @Override
    public int addGoods(Goods goods) {
        List<Spec> specList = goods.getSpecList();

      Double price = specList.get(0).getUnitPrice();
   int stconum=specList.get(0).getStockNum();
        for (int i = 1; i < specList.size(); i++) {
            stconum+=specList.get(i).getStockNum();
            if(specList.get(i).getUnitPrice() < price){
                price = specList.get(i).getUnitPrice();
            }
        }
        goods.setPrice(price);
        goods.setStockNum(stconum);

        int result = goodsDao.addGoods(goods);
        if(result == 200){

            int goodsId = goodsDao.queryLastId();

            goodsDao.addSpeces(goods.getSpecList(), goodsId);
            return 200;
        }

        return 500;

    }

    @Override
    public List<Goods> getGoodsByType(String id) {
        return goodsDao.getGoodByType(id);
    }

    @Override
    public Goods getGoodsInfo(String id) {
        return goodsDao.getGoodsInfo(id);
    }

    @Override
    public List<Spec> getSpec(String id) {
        return goodsDao.getSpec(id);
    }

    @Override
    public int updateGoods(Goods goods) {
        return goodsDao.updateGoods(goods);
    }

    @Override
    public int addSpec(Spec spec) {
        return goodsDao.addSpec(spec);
    }

    @Override
    public int deleteGoods(String id) {
        return goodsDao.deleteGoods(id);
    }
}
