package com.wwx.shop.dao.impl.Admin;

import com.alibaba.druid.util.StringUtils;
import com.wwx.shop.bean.Admin;
import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Msg;
import com.wwx.shop.bean.User;
import com.wwx.shop.dao.Admin.AdminDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminDaoImpl  implements AdminDao {
    QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
    /**
     * 登录
     * @param admin
     * @return
     */
    @Override
    public int login(Admin admin) {
        //sql
        Admin query = null;
        try {
            query = runner.query("select * from admin where email = ? and pwd = ?",
                    new BeanHandler<>(Admin.class),
                    admin.getEmail(),
                    admin.getPwd());
        } catch (SQLException e) {
            e.printStackTrace();
            return 500;
        }
        if(query != null){
            return 200;
        }
        return 404;
    }

    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<Admin> queryAllAdmins() {
        List<Admin> adminList = null;
        try {
            adminList = runner.query("select * from admin", new BeanListHandler<Admin>(Admin.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @Override
    public int addAdminss(Admin admin) {
        int query =-1;
        try {
            new BeanHandler<>(Admin.class);
            query=runner.update("insert into admin(name,email,pwd) values(?,?,?)",
                    admin.getNickname(),
                    admin.getEmail(),
                    admin.getPwd());
        } catch (SQLException e) {
            e.printStackTrace();

             }
        if(query != -1){
            return 200;
        }
       return 500;
}

    /**
     * 更新管理员
     * @param admin
     * @return
     */
    @Override
    public int updateAdminss(Admin admin) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int query =-1;
        try {
            query=runner.update("UPDATE admin SET NICKNAME=?,email=?,pwd=? WHERE id=?",
                    admin.getNickname(),
                    admin.getEmail(),
                    admin.getPwd(),
                    admin.getId()

            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(query != -1){
            return 200;
        }
        return 500;
    }

    /**
     * 得到管理员信息
     * @param id
     * @return
     */
    @Override
    public List<Admin> getAdminsInfo(String id) {
        Integer it = Integer.valueOf(id);//转换成integer类型
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Admin> adminList = null;
        try {
            adminList = runner.query("select * from admin where id=?", new BeanListHandler<Admin>(Admin.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @Override
    public int deleteAdmins(String id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int query =-1;
        try {
            query=runner.update("DELETE FROM admin WHERE id=?",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }   if(query != -1){
            return 200;
        }
        return 500;


    }

    /**
     * 搜索管理员
     * @param admin
     * @return
     */
    @Override
    public List<Admin> querySearchAdmins(Admin admin) {
        List<Admin> adminList= null;
        String baseUrl="select * from admin where 1=1";
        List<Object> list=new ArrayList<Object>();
        if (!StringUtils.isEmpty(admin.getEmail()))
        {
            baseUrl=baseUrl+" and email like ?";
            list.add("%" + admin.getEmail() + "%");
        }
        if (!StringUtils.isEmpty(admin.getNickname()))
        {
            baseUrl=baseUrl+" and nickname like ?";
            list.add("%" + admin.getNickname() + "%");
        }

        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        try {

                  adminList=  runner.query(baseUrl,new BeanListHandler<Admin>(Admin.class),list.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return  adminList;
    }

    /**
     * 修改密码
     * @param admin
     * @return
     */
    @Override
    public int changePwd(Admin admin) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin query = null;
        try {
            query = runner.query("select * from admin where nickname = ? and pwd = ?",
                    new BeanHandler<>(Admin.class),
                    admin.getAdminToken(),
                    admin.getOldPwd());
        } catch (SQLException e) {
            e.printStackTrace();

        }
        if(query != null){
            if (! admin.getNewPwd().equals(admin.getConfirmPwd()))
            {
                return 404;
            }
            try {
                runner.update("UPDATE admin SET pwd=? WHERE nickname=?",
                       admin.getNewPwd(),
                        admin.getAdminToken());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 200;
        }
        return 404;
    }

    /**
     * 未回复的消息
     * @return
     */
    @Override
    public List<Msg> noReplyMsg() {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
       List<Msg> msgs=null;
        try {
            msgs=runner.query("select * from msg where state = 1",new BeanListHandler<Msg>(Msg.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Msg msg:msgs){
            try {
               Goods  goods=runner.query("select * from goods where id=?",new BeanHandler<Goods>(Goods.class),msg.getGoodsId());
                System.out.println(msg.getGoodsId());
                User  user=runner.query("select * from user where id=?",new BeanHandler<User>(User.class),msg.getUserId());
                user.setName(user.getNickname());
                msg.setUser(user);
                msg.setGoods(goods);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return msgs;
    }

    /**
     * 回复的消息
     * @return
     */
    @Override
    public List<Msg> repliedMsg() {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        List<Msg> msgs=null;
        try {
            msgs=runner.query("select * from msg where state = 0",new BeanListHandler<Msg>(Msg.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i=0;i<msgs.size();i++){
            try {
                User user=new User();
                Goods goods=runner.query("select * from goods where id=?",new BeanHandler<Goods>(Goods.class),msgs.get(i).getGoodsId());
                msgs.get(i).setGoods(goods);
                user=runner.query("select * from user where id=?",new BeanHandler<User>(User.class),msgs.get(i).getUserId());
                user.setName(user.getNickname());
                // msgs.get(i).setUser(runner.query("select nickname from user where id=?",new BeanHandler<User>(User.class),msgs.get(i).getUserid()));
                msgs.get(i).setUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return msgs;
    }

    /**
     * 回复
     * @param msg
     * @return
     */
    @Override
    public int reply(Msg msg) {
        QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());
        int res=-1;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        try {
            res=runner.update("update msg set state =0,replyContent=? ,replytime=? where id=?",
                    msg.getContent(),
                    df.format(new Date()),
                    msg.getId()
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res!=-1)
            return 200;
        return 500;
    }
}
