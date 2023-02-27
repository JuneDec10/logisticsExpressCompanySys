package com.example.springboot.common;

public class CodeMsg extends RuntimeException{

    private int code;//错误码

    private String msg;//错误信息

    /**
     * 构造函数私有化即单例模式
     * @param code
     * @param msg
     */
    private CodeMsg(int code,String msg){
        this.msg=msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }



    public void setCode(int code) {
        this.code = code;
    }



    public String getMsg() {
        return msg;
    }



    public void setMsg(String msg) {
        this.msg = msg;
    }

    //通用错误码定义
    //处理成功消息码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");

    //后台管理类错误码
    //用户管理类错误
    public static CodeMsg ADMIN_USERNAME_EMPTY = new CodeMsg(-2000, "用户名不能为空！");
    public static CodeMsg ADMIN_PASSWORD_EMPTY = new CodeMsg(-2001, "密码不能为空！");
    public static CodeMsg ADMIN_NO_RIGHT = new CodeMsg(-2002, "您所属的角色没有该权限！");

    //登录类错误码
    public static CodeMsg ADMIN_USERNAME_NO_EXIST = new CodeMsg(-3000, "该用户名不存在！");
    public static CodeMsg ADMIN_PASSWORD_ERROR = new CodeMsg(-3001, "密码错误！");
    public static CodeMsg ADMIN_USER_UNABLE = new CodeMsg(-3002, "该用户已被冻结，请联系管理员！");
    public static CodeMsg ADMIN_USER_ROLE_UNABLE = new CodeMsg(-3003, "该用户所属角色状态不可用，请联系管理员！");
    public static CodeMsg ADMIN_USER_ROLE_AUTHORITES_EMPTY = new CodeMsg(-3004, "该用户所属角色无可用权限，请联系管理员！");

    //后台菜单管理类错误码
    public static CodeMsg ADMIN_MENU_ADD_ERROR = new CodeMsg(-4000, "菜单分配失败，请稍后再试！");
    public static CodeMsg ADMIN_MENU_EDIT_ERROR = new CodeMsg(-4001, "菜单编辑失败，请联系管理员！");
    public static CodeMsg ADMIN_MENU_ID_EMPTY = new CodeMsg(-4002, "菜单ID不能为空！");
    public static CodeMsg ADMIN_MENU_ID_ERROR = new CodeMsg(-4003, "菜单ID错误！");
    public static CodeMsg ADMIN_MENU_DELETE_ERROR = new CodeMsg(-4004, "菜单删除失败！");
    //后台角色管理类错误码
    public static CodeMsg ADMIN_ROLE_ADD_ERROR = new CodeMsg(-5000, "角色添加失败，请联系管理员！");
    public static CodeMsg ADMIN_ROLE_NO_EXIST = new CodeMsg(-5001, "该角色不存在！");
    public static CodeMsg ADMIN_ROLE_EDIT_ERROR = new CodeMsg(-5002, "角色编辑失败，请稍后再试！");
    public static CodeMsg ADMIN_ROLE_DELETE_ERROR = new CodeMsg(-5003, "该角色下存在用户信息，不可删除！");
    public static CodeMsg ADMIN_ROLE_EXIST = new CodeMsg(-5003, "该角色类型下只能有一个角色！");
    //后台用户管理类错误码
    public static CodeMsg ADMIN_USER_ROLE_EMPTY = new CodeMsg(-6000, "请选择用户所属角色！");
    public static CodeMsg ADMIN_USERNAME_EXIST = new CodeMsg(-6001, "该用户名已存在，请换一个试试！");
    public static CodeMsg ADMIN_USE_ADD_ERROR = new CodeMsg(-6002, "用户添加失败，请联系管理员！");
    public static CodeMsg ADMIN_USE_NO_EXIST = new CodeMsg(-6003, "用户不存在！");
    public static CodeMsg ADMIN_USE_EDIT_ERROR = new CodeMsg(-6004, "用户编辑失败，请联系管理员！");
    public static CodeMsg ADMIN_USE_DELETE_ERROR = new CodeMsg(-6005, "该用户存在关联数据，不允许删除！");

    //后台用户修改密码类错误码
    public static CodeMsg ADMIN_USER_UPDATE_PWD_ERROR = new CodeMsg(-7000, "旧密码错误！");
    public static CodeMsg ADMIN_USER_UPDATE_PWD_EMPTY = new CodeMsg(-7001, "新密码不能为空！");

    //后台用户修改密码类错误码
    public static CodeMsg ADMIN_DATABASE_BACKUP_NO_EXIST = new CodeMsg(-8000, "备份记录不存在！");

    //快递管理类错误码
    public static CodeMsg ADMIN_EXPRESSMAILORDER_ADD_ERROR = new CodeMsg(-60001, "快递订单添加失败，收货地址或发货地址暂无网点！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_RECEIVING_EDIT_ERROR = new CodeMsg(-60004, "快递订单揽收失败，请联系管理员！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_SENDTHECARGO_ADD_EDIT_ERROR = new CodeMsg(-60004, "快递订单发货失败，订单已取消！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER1_RECEIVING_EDIT_ERROR = new CodeMsg(-60003, "快递订单收货失败，请联系管理员！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER1_BUYOFF_EDIT_ERROR = new CodeMsg(-60003, "指定派送快递员失败，请联系管理员！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_USER_NULL = new CodeMsg(-60004, "请选择网点|派送点");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_ID_NULL = new CodeMsg(-60004, "订单不能为空");
    public static CodeMsg ADMIN_EXPRESSMAILORDER1_BUYOFF1_EDIT_ERROR = new CodeMsg(-60003, "快递签收失败，请联系管理员！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER1_SEARCH_ERROR = new CodeMsg(-60006, "物流查询失败，请检查快递单号是否正确！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_ISMOBILE_EDIT_ERROR = new CodeMsg(-6004, "手机号格式不对,请重新输入！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_ISADDRESS_EDIT_ERROR = new CodeMsg(-6004, "请填写地址!");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_ISADDRESSLENGTH_EDIT_ERROR = new CodeMsg(-6004, "地址长度不能超过50!");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_DELETE_ERROR = new CodeMsg(-6005, "该订单已发货，无法取消订单！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_SETPICKUPTIME_ERROR = new CodeMsg(-60001, "快递订单设置上门送件时间失败，请联系管理员！");
    public static CodeMsg ADMIN_EXPRESSMAILORDER_SETPICKUPTIME_ERROR1 = new CodeMsg(-60001, "快递订单已签收,无法设置上门送件时间！");
    public static CodeMsg ADMIN_AGINGQUERY_GET_NULL = new CodeMsg(-60005,"运费时效列表获取失败，请稍后再试");
    public static CodeMsg ADMIN_AGINGQUERY_UPDATE_REPEAT = new CodeMsg(-60006,"所编辑城市之间已经存在运费时效，请不要重复添加");
    public static CodeMsg ADMIN_AGINGQUERY_UPDATE_ERROR = new CodeMsg(-60005,"运费时效修改失败，请稍后再试");
    public static CodeMsg ADMIN_AGINGQUERY_ADD_ERROR = new CodeMsg(-60005,"运费时效添加失败，请稍后再试");
    public static CodeMsg ADMIN_AGINGQUERY_DELETE_ERROR = new CodeMsg(-60005,"运费时效添加失败，请稍后再试");




    //前台登录错误码
    public static CodeMsg HOMEUSER_MOBILE_ERROR = new CodeMsg(-20000,"手机号格式错误！");
    public static CodeMsg HOMEUSER_TOKEN_ERROR = new CodeMsg(-20001,"无token，请重新登录！");
    public static CodeMsg HOMEUSER_TOKEN_FAILURE_ERROR = new CodeMsg(-20002,"taken验证失败，请重新登录");
    public static CodeMsg HOMEUSER_MOBILE_EXIST = new CodeMsg(-20003,"该手机号已存在！");
    public static CodeMsg HOMEUSER_ADD_ERROR = new CodeMsg(-20004, "注册失败！请稍后重试");
    public static CodeMsg HOMEUSER_NO_EXIST = new CodeMsg(-20007,"用户不存在！");
    public static CodeMsg HOMEUSER_PASSWORD_ERROR = new CodeMsg(-20008,"密码错误！");
    public static CodeMsg COMMON_EMAIL_FORMAET_ERROR = new CodeMsg(-20014, "邮箱格式不正确！");
    public static CodeMsg CODE_NOT_EMPTY = new CodeMsg(-20015, "验证码为空，请发送验证码后重试！");
    public static CodeMsg CODE_NOT_ERROR = new CodeMsg(-20016, "邮箱错误！");
    public static CodeMsg HOMEUSER_FORGET_ERROR = new CodeMsg(-20017, "修改失败！");
    public static CodeMsg HOMEUSER_STATUS_ERROR = new CodeMsg(-20018, "您的账号已被冻结！");
    public static CodeMsg HOMEUSER_USER_EDIT_ERROR = new CodeMsg(-20019, "前台用户编辑失败");



    //后台网点管理类错误码
    public static CodeMsg BRANCHES_USER_ROLE_EMPTY = new CodeMsg(-27000, "请选择网点所属角色！");
    public static CodeMsg BRANCHES_USERNAME_EXIST = new CodeMsg(-27001, "该网点名已存在，请换一个试试！");
    public static CodeMsg BRANCHES_USE_NO_EXIST = new CodeMsg(-27003, "网点不存在！");
    public static CodeMsg BRANCHES_USE_EDIT_ERROR = new CodeMsg(-27004, "网点编辑失败，请联系管理员！");


    //后台运输点管理类错误码
    public static CodeMsg TRANSPORT_USER_ROLE_EMPTY = new CodeMsg(-28000, "请选择运输点所属角色！");
    public static CodeMsg TRANSPORT_USERNAME_EXIST = new CodeMsg(-28001, "该用户名已存在，请换一个试试！");
    public static CodeMsg TRANSPORT_USE_ADD_ERROR = new CodeMsg(-28002, "运输点添加失败，请联系管理员！");
    public static CodeMsg TRANSPORT_USE_NO_EXIST = new CodeMsg(-28003, "运输点不存在！");
    public static CodeMsg TRANSPORT_USE_EDIT_ERROR = new CodeMsg(-28004, "运输点编辑失败，请联系管理员！");
    public static CodeMsg TRANSPORT_USE_DELETE_ERROR = new CodeMsg(-28005, "该运输点删除失败，请联系管理员！");
    public static CodeMsg ADDRESS_ERROR = new CodeMsg(-28006, "请选择省市区！");



    //后台网点管理类错误码
    public static CodeMsg COMPLAINT_WAYBILLNUMBER_EXIST = new CodeMsg(-40001, "该订单你已投诉过，不可重复投诉！");
    public static CodeMsg WAYBILLNUMBER_NO_EXIST = new CodeMsg(-40002, "该订单不存在！");
    public static CodeMsg COMPLAINT_ERROR = new CodeMsg(-40003, "投诉失败！");
    public static CodeMsg COMPLAINT_ACCEPTED_ERROR = new CodeMsg(-40004, "投诉受理失败！");
    public static CodeMsg COMPLAINT_DELETED_ERROR = new CodeMsg(-40005, "投诉取消失败！");


}
