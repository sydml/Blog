package com.sydml.dao;

import com.sydml.model.pojo.Login;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginDao {


    /**
     * 根据用户id删除登录记录
     * @param id
     */
    void deleteLoginByUserId(Integer id);


    /**
     * 保存登录信息
     */
    void saveLogin(Login login);


    /**
     * 根据用户id修改登录操作表
     * @param login
     */
    void updateLogin(Login login);

    /**
     * 根据用户id找登录操作表
     * @param userId
     */
    Login findLoginByUserId(Integer userId);
}
