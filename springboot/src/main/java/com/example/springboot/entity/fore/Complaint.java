package com.example.springboot.entity.fore;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot.entity.BaseEntity;
import lombok.Data;

@TableName("complaint")
@Data
public class Complaint extends BaseEntity {

    public static final Integer NOTACCEPTED = 0;
    public static final Integer ACCEPTING = 1;
    public static final Integer ACCEPTED = 2;

    @TableField(value="fore_user_id")
    private Long foreUser;  //投诉人

    @TableField(value="complaint_name")
    private String complaintName; //投诉人名字

    @TableField(value="mobile")
    private String mobile; //投诉人电话

    @TableField(value="fore_user_type")
    private Integer foreUserType; //寄件人还是收件人

    @TableField(value="waybill_number")
    private String waybillNumber;  //运单编号

    @TableField(value="content")
    private String content; //投诉内容

    @TableField(value="status")
    private Integer status = NOTACCEPTED; //投诉状态 0未受理，1已受理

    @TableField(value="deleted")
    private Integer deleted;
}
