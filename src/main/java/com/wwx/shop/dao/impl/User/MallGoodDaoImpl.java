package com.wwx.shop.dao.impl.User;

import com.sun.xml.internal.ws.api.message.HeaderList;
import com.wwx.shop.bean.Comments;
import com.wwx.shop.bean.Msg;
import com.wwx.shop.bean.User;
import com.wwx.shop.dao.User.MallGoodDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MallGoodDaoImpl implements MallGoodDao {
    QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
    /**
     * 把所有的消息查询出来
     * @param goodsid
     * @return
     */
    @Override
    public List<Msg> getGoodsMsg(String goodsid) {
       // QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        List<Msg> msg=null;
        try {
            msg=runner.query("select * from msg where goodsid=?",new BeanListHandler<Msg>(Msg.class),goodsid);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 查询用户
     * @param userid
     * @return
     */
    @Override
    public User queryUser(int userid) {
        User user=new User();
        try {
            user=runner.query("select * from user where id=?",new BeanHandler<User>(User.class),userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 得到评论
     * @param goodsid
     * @return
     */
    @Override
    public List<Map> getGoodsComments(String goodsid) {
        List res=new ArrayList();
        int i=0;//记录好评的个数

int b=0;
        try {

            List<Comments> list = runner.query("select * from COMMENT where goodsid=?", new BeanListHandler<Comments>(Comments.class), goodsid);
            //计算tate
            //获取好评的个数，不去数据库里查询 节省开销
            for (Comments comments1 : list) {
                if (comments1.getScore() == 100) {
                    i++;
                }
            }
            if (list.size() == 0) {
             //l"commentList": [],

            } else {
                b = i / list.size();//计算出rate
                for (Comments comments : list) {
                    Map map = new HashMap();
                    Map map1 = new HashMap();
                    //先组装user
                    map1.put("nickname", comments.getUsername());
                    //在组长大的map
                    map.put("user", map1);
                    map.put("score", comments.getScore());
                    map.put("id", comments.getCommentsid());
                    map.put("specName", comments.getSpecName());
                    map.put("time", comments.getTime());
                    map.put("userId", comments.getUserid());
                    map.put("rate", b);
                    map.put("comment",comments.getContent());
                    res.add(map);//最后装到list里，每一次进入循环都重新进入类
                }
            }   } catch(SQLException e){
                e.printStackTrace();
            }

        return res;
    }
}
