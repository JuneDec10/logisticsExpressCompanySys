package com.example.springboot.entity.back;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot.entity.BaseEntity;
import com.example.springboot.entity.enumentity.UserRoleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("role")
public class Role {
    private static final long serialVersionUID = 1L;

    public static final int ADMIN_ROLE_STATUS_ENABLE = 1;//角色状态正常可用
    public static final int ADMIN_ROLE_STATUS_UNABLE = 0;//角色状态不可用

    private Integer id;//唯一id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GTM+8")
    @TableField(value="create_time")
    private Date createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GTM+8")
    @TableField(value="update_time")
    private Date updateTime;//更新时间

    @TableField(value = "name")
    private String name;//角色名称

    @TableField(exist = false)
    private List<MenuInfo> submenu;//角色所对应的权限（菜单）列表

    @TableField(value = "status")
    private Integer status = ADMIN_ROLE_STATUS_ENABLE;//角色状态,默认可用

    @TableField(value = "remark")
    private String remark;//角色备注



}
