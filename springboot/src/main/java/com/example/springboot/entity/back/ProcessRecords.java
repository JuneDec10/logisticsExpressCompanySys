package com.example.springboot.entity.back;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot.entity.BaseEntity;
import com.example.springboot.entity.fore.ExpressMailOrder;
import lombok.Data;

@TableName("process_records")
@Data
public class ProcessRecords extends BaseEntity {
    public static final int HAVE_HANDLED = 1;//操作过

    public static final int NO_OPERATIONS = 2;//没有操作再次运输过

    @TableField(value="express_mail_order_id")
      private ExpressMailOrder expressMailOrderId;  //订单

    @TableField(value="received_branch_id")
    private User receivedBranchId;  //发出的网点

    @TableField(value="next_branch_id")
    private User nextBranchId; //下一个前往的网点

    @TableField(value="travel_position")
    private Integer travelPosition; //运输接受状态

    @TableField(value= "operation")
    private Integer operation = NO_OPERATIONS;
}
