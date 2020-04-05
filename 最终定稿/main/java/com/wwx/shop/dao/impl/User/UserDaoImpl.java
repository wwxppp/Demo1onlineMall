package com.wwx.shop.dao.impl.User;

import com.alibaba.druid.DruidRuntimeException;
import com.wwx.shop.bean.Admin;
import com.wwx.shop.bean.User;
import com.wwx.shop.dao.User.UserDao;
import com.wwx.shop.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    QueryRunner runner=new QueryRunner(DruidUtils.getDataSource());

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public int signup(User user) {
        int i=-1;
        User user1=new User();//判断账号是否存在
        try {//邮箱和昵称不能重复，收件人可以重复（同名的情况）
            user1=runner.query("select * from user where email=? or nickname=?",new BeanHandler<User>(User.class),user.getEmail(),user.getNickname());
            if (user1!=null){
                return 500;
            }
            i=runner.update("insert into user values(null,?,?,?,?,?,?)",
                    user.getEmail(),
                    user.getNickname(),
                    user.getPwd(),
                    user.getRecipient(),
                    user.getAddress(),
                    user.getPhone()
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (i!=-1){
            return  200;
        }
        return 0;
    }

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User user1=new User();
        try {
            user1=runner.query("select * from user where email=? and pwd=?",new BeanHandler<User>(User.class),user.getEmail(),
                    user.getPwd());
        }catch (SQLException e) {
            e.printStackTrace();

        }
  return  user1;
    }

    @Override
    public User data(String token) {
        User user=new User();
        try {
            user=runner.query("select * from user where nickname=?",new BeanHandler<User>(User.class),token);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user ;
    }

    @Override
    public int updatePwd(Admin admin) {
        int res=-1;
        if (!admin.getConfirmPwd().equals(admin.getNewPwd())){
            return  500;//两次密码不一致
        }
        try {
            User user=runner.query("select * from user where id=?",new BeanHandler<User>(User.class),admin.getId());
            if (! admin.getOldPwd().equals(user.getPwd())){
                return  250;//旧密码不对
            }
            res=runner.update("update user set pwd=? where id=?",admin.getNewPwd(),admin.getId());




        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res!=-1){
            return 200;
        }
        return 0;
    }

    @Override
    public int updateUserData(User user) {
       /* id: "482"
        nickname: "wewew"
        recipient: "周i形成"
        address: "啊啊啊啊"
        phone: "13543245678"*/
       int res=-1;
        try {
            res=runner.update("update user set nickname=?,recipient=?,address=?,phone=? where id=?", user.getNickname(),
                    user.getRecipient(),user.getAddress(),user.getPhone(),user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res!=-1)
        {
            return 200;
        }
        return 0;
    }
}
