package com.example.springboot.entity.fore;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("fore_user")
public class ForeUser extends BaseEntity {
    public static final Integer BANNED = 0;
    public static final Integer NORMAL = 1;
    private String mobile;//手机号
    @TableField(select=false)
    private String password; //密码
    private String name;//用户名
    private String email;//用户邮箱
    private Date birthday; //生日
    private Integer sex; //性别
    private String address; //地址（省市区）
    private String detailedAddress; //详细地址
    private Integer status;//状态
}
