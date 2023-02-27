package com.example.springboot.entity.fore;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@TableName("express_mail_order")
@Data
public class ExpressMailOrder extends BaseEntity {
    public static final int ABOLISH = 0;
    public static final int ABOLISHING = 1;
    public static final int ABOLISHED = 2;
@TableField(value="sender_name")
    private String  senderName;
    @TableField(value="sender_mobile")
    private String  senderMobile;
    @TableField(value="send_address")
    private String sendAddress;
    @TableField(value="payment_method")
    private String paymentMethod;
    @TableField(value="send_full_address")
    private String sendFullAddress;
    @TableField(value="recipients_name")
    private String recipientsName;
    @TableField(value="recipients_mobile")
    private String recipientsMobile;
    @TableField(value="recipients_address")
    private String recipientsAddress;
    @TableField(value="recipients_full_address")
    private String recipientsFullAddress;
    @TableField(value="item_type")
    private String itemType;
    @TableField(value="item_weight")
    private Double itemWeight;
    @TableField(value = "remark")
    private String remark;
    @TableField(value="price")
    private Double price;
    @TableField(value="waybill_number")
    private String waybillNumber;
    @TableField(value="fore_user_id")
    private Long foreUserId;
    @TableField(value="order_status")
    private Integer orderStatus;
    @TableField(value="abolished")
    private Integer abolished=0;
}
