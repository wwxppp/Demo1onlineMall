package com.wwx.shop.dao.User;

import com.wwx.shop.bean.Admin;
import com.wwx.shop.bean.User;

public interface UserDao {
    int signup(User user);

    User login(User user);

    User data(String token);

    int updatePwd(Admin admin);

    int updateUserData(User user);
}
