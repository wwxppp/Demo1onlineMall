package com.wwx.shop.service.User;

import com.wwx.shop.bean.Admin;
import com.wwx.shop.bean.User;

import java.util.Map;

public interface UserService {
    int signup(User user);

    User login(User user);

    Map data(String token);

    int updatePwd(Admin admin);

    int updateUserData(User user);
}
