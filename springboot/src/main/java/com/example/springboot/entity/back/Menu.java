package com.example.springboot.entity.back;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("menu")
public class Menu extends BaseEntity {

    public static final int IS_BUTTON = 1;//角色状态正常可用
    public static final int IS_NOT_BUTTON = 0;//角色状态不可用

    public static final int IS_DISABLED = 1;//角色状态正常可用
    public static final int IS_NOT_DISABLED = 0;//角色状态不可用

    public static final int IS_DELETED = 1;//角色状态正常可用
    public static final int IS_NOT_DELETED = 0;//角色状态不可用


    @TableField(value = "name")
    private String name;

    @TableField(value = "routerName")
    private String routerName;

    @TableField(value = "iconName")
    private String iconName;
    @TableField(value = "sort")
    private Integer sort;

    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(value = "is_button")
    private Integer isButton=IS_NOT_BUTTON;

    @TableField(value="disabled")
    private Integer disabled=IS_NOT_DISABLED;

    @TableField(value = "deleted")
    private Integer deleted = IS_NOT_DELETED;
}
