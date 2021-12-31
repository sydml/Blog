package com.sydml.model.pojo;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * 邀请码
 */
@Data
@ToString
@Alias("Code")
public class Code {
    private String id;//id
    private Integer state;//状态 0 未使用 1已使用 2 已删除
    private User user;



}
