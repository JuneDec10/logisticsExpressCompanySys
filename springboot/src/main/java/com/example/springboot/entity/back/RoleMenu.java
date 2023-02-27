package com.example.springboot.entity.back;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role_menu")
public class RoleMenu {
    @TableField("role_id")
    private Integer roleId;
    @TableField("menu_id")
    private Long menuId;
}
