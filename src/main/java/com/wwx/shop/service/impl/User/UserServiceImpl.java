package com.wwx.shop.service.impl.User;

import com.wwx.shop.bean.Admin;
import com.wwx.shop.bean.User;
import com.wwx.shop.dao.User.MallUserDao;
import com.wwx.shop.dao.User.UserDao;
import com.wwx.shop.dao.impl.User.UserDaoImpl;
import com.wwx.shop.service.User.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao=new UserDaoImpl();
    /**
     * 用户注册
     * @param user
     */
    @Override
    public int signup(User user) {

        return userDao.signup(user);
    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public Map data(String token) {
        //map
        Map map=new HashMap();
        User user=userDao.data(token);

        map.put("code",0);
        map.put("id",user.getId());
        map.put("email",user.getEmail());
        map.put("nickname",user.getNickname());
        map.put("recipient",user.getRecipient());
        map.put("address",user.getAddress());
        map.put("phone",user.getPhone());


        return map;
    }

    @Override
    public int updatePwd(Admin admin) {
        return userDao.updatePwd(admin);
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public int updateUserData(User user) {

        return userDao.updateUserData(user);
    }
}
