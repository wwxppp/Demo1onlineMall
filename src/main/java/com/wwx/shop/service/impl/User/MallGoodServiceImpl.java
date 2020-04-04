package com.wwx.shop.service.impl.User;

import com.wwx.shop.bean.Comments;
import com.wwx.shop.bean.Msg;
import com.wwx.shop.bean.User;
import com.wwx.shop.dao.User.MallGoodDao;
import com.wwx.shop.dao.impl.User.MallGoodDaoImpl;
import com.wwx.shop.service.User.MallGoodService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MallGoodServiceImpl implements MallGoodService {
    MallGoodDao mallGoodDao=new MallGoodDaoImpl();
    /**
     * 获取商品的回复
     * @param goodsid
     * @return
     */
    @Override
    public List getGoodsMsg(String goodsid) {
        //涉及到lilst的使用问题
        List<Map> list=new ArrayList<Map>();//总封装
        // 1查询msg表 获取到等信息
       List<Msg>  msg=mallGoodDao.getGoodsMsg(goodsid);
       for (Msg m:msg) {
           //2.查询user表 获取user名.
           User user = mallGoodDao.queryUser(m.getUserid());
           Map map=new HashMap();
           Map map1=new HashMap();//封装reply
           //先封装reply
           map1.put("content", m.getReplyContent());
           map1.put("time", m.getReplytime());
           //在封装散装数据
           map.put("asker",user.getNickname());
           map.put("time",m.getCreatetime());
           map.put("id",m.getId());
           map.put("content",m.getContent());
           //用map把数据封装起来
           map.put("reply",map1);
           System.out.println(m);
           System.out.println(map);
           //统一装到一个list里边
           list.add(map);
       }



        return list;

}

    /**
     * 获得商品评价
     * @param goodsid
     * @return
     */
    @Override
    public Map getGoodsComment(String goodsid) {
        //分析返回的数据 最后返回的是一个map
        //这个map里装着一个lis和一个rate 这个list装了n个map
        //每一个小map又都包含一个map
        //1.先查询 这个list交给dao层组装

        List<Map> list=mallGoodDao.getGoodsComments(goodsid);
        //组装map
        Map map=new HashMap();
        if (list.isEmpty())
        {
            map.put("rate"," NaN");
        }
        else {

            map.put("rate",list.get(0).get("rate"));//在dao层计算出后给每个map里都添加一个字段带到service层
             }
        map.put("commentList",list);
        return map;
    }
}
