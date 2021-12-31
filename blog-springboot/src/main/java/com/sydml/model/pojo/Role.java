package com.sydml.model.pojo;


import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * 角色
 */
@Data
@ToString
@Alias("Role")
public class Role {

    private Integer id;//角色id
    private String name;//角色名

    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }


}
