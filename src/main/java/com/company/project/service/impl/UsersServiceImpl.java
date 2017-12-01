package com.company.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.company.project.core.JsonWebToken;
import com.company.project.core.ServiceException;
import com.company.project.dao.UsersMapper;
import com.company.project.model.Users;
import com.company.project.service.UsersService;
import com.company.project.core.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * Created by CodeGenerator on 2017/11/30.
 */
@Service
@Transactional
public class UsersServiceImpl extends AbstractService<Users> implements UsersService {
    @Resource
    private UsersMapper usersMapper;
    @Autowired
    private JsonWebToken jsonWebToken;

    @Override
    public void login(HttpServletResponse response, String loginName, String encryptedPassword) {
        int count = usersMapper.selectCountByNameAndPwd(loginName, encryptedPassword);
        if (count == 1) {
            // 成功并返回token
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login_name", loginName);
            String subject = jsonObject.toJSONString();
            try {
                String token = jsonWebToken.createJWT(subject);
                response.setHeader("token", token);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("获取token失败");
            }
        } else {
            throw new ServiceException("用户名或密码错误");
        }
    }

    @Override
    public void regist(Users users) {

        int count = usersMapper.selectCountByLoginName(users.getLoginName());
        if (count >= 1) {
            throw new ServiceException("用户名已存在");
        } else {
            // 保存用户信息
            users.setCreateTime(new Date());
            save(users);
        }
    }
}
