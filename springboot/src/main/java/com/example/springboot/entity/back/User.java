package com.example.springboot.entity.back;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot.entity.BaseEntity;
import com.example.springboot.entity.common.Sex;
import com.example.springboot.entity.enumentity.UserRoleType;
import com.example.springboot.entity.enumentity.BranchType;
import lombok.Data;


@Data
@TableName("user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;


    public static final int ADMIN_USER_STATUS_ENABLE = 1;//用户状态正常可用
    public static final int ADMIN_USER_STATUS_UNABLE = 0;//用户状态不可用

    @TableField(exist = false)
    private Role role;
    private String username;//用户名
    private String password;//登录密码
    private Integer status = ADMIN_USER_STATUS_ENABLE;//用户状态,默认可用
    @TableField(value = "head_pic")
    private String headPic;//用户头像
    @TableField(exist = false)
    private Integer sex = Sex.UNKONW;//用户性别
    private String mobile;//用户手机号
    private String address;//所在省市区
    @TableField(value = "office_address")
    private String officeAddress;//办公地址,详细地址
@TableField(value="branch_type",exist = false)
    private Integer branchType;//网点类型
  @TableField(value="role_id")
    private Integer roleId; //后台用户类型

@TableField(value="longitude_and_latitude")
    private String longitudeAndIatitude;  // 百度地图经纬度
@TableField(value="branch_id",exist = false)
    private Long branchId;  //快递员所属网点

    private Integer deleted;

//    private Long countCourier;//带看次数
}
