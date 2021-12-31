package com.sydml.model.pojo;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 登录表
 */
@Data
@ToString
@Alias("Login")
public class Login {

    private Date time;//最后登录时间
    private String ip;//最后登录ip
    private User user;//用户


}
