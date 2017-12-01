package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UsersMapper extends Mapper<Users> {

    @Select("SELECT count(*) FROM users WHERE login_name = #{loginName} and encrypted_password = #{encryptedPassword}")
    public int selectCountByNameAndPwd(@Param("loginName") String loginName, @Param("encryptedPassword") String encryptedPassword);

    @Select("SELECT count(*) FROM users WHERE login_name = #{loginName}")
    public int selectCountByLoginName(@Param("loginName") String loginName);

}