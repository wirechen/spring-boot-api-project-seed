package com.company.project.service;
import com.company.project.model.Users;
import com.company.project.core.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created by CodeGenerator on 2017/11/30.
 */
public interface UsersService extends Service<Users> {

    void login(HttpServletResponse responsem, String loginName, String encryptedPassword);

    void regist(Users users);
}
