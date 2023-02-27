package com.example.springboot.controller.back;

import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.entity.back.ProcessRecords;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.common.ExpressMailOrderStatus;
import com.example.springboot.entity.fore.ExpressMailOrder;
import com.example.springboot.service.back.ProcessRecordsService;
import com.example.springboot.service.fore.ExpressMailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/expressMailOrder")
public class BackExpressMailOrderController {

    @Autowired
    private ExpressMailOrderService expressMailOrderService;

    @Autowired
    private ProcessRecordsService processRecordsService;

// 网点得到用户下单列表
    @PostMapping("/list")
    @ResponseBody
    public Result<List<ExpressMailOrder>> list(@RequestBody Map<String,Object> map ) {
        Long id = Long.valueOf(map.get("id").toString());
        List<ExpressMailOrder> expressMailOrders = expressMailOrderService.listByStatus(id, ExpressMailOrderStatus.HAVEORDER);
        if (expressMailOrders != null) {
            return Result.success(expressMailOrders);
        } else return Result.success(null);
    }

//    网点揽收货物
    @PostMapping("/pickUp")
    @ResponseBody
    public Result<Boolean> pickUp(@RequestBody Map<String,Object> map) {
        Long userId = Long.valueOf(map.get("userId").toString());
        Long itemId = Long.valueOf(map.get("itemId").toString());
        Long isTrue = expressMailOrderService.saveExpressMailOrderAndBuyOff(userId, itemId);
        if (isTrue != null) {
            return Result.success(true);
        } else return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER_RECEIVING_EDIT_ERROR);

    }

//    网点发货列表
    @PostMapping("/deliveryList")
    @ResponseBody
    public Result<List<ProcessRecords>> deliveryList(@RequestBody Map<String,Object> map){
          Long id = Long.valueOf(map.get("id").toString());
          return Result.success(processRecordsService.getList(id, ProcessRecords.NO_OPERATIONS,
                  ExpressMailOrderStatus.TO_COMPLETE_THE_GOODS));
    }

    @PostMapping("/SearchIsBack")
    @ResponseBody
    public Result<Boolean> SearchIsBack(@RequestBody Map<String,Object> map){
        Long recordId = Long.valueOf(map.get("recordId").toString());
        Long itemId = Long.valueOf(map.get("itemId").toString());
        Long userId = Long.valueOf(map.get("userId").toString());
        expressMailOrderService.updateAbolished(itemId,2);
        Long id = processRecordsService.ChangeStatusSaveProcess(recordId,userId,itemId,userId,ExpressMailOrderStatus.INTHEDELIVERY,ExpressMailOrderStatus.INTRANSIT);
         if(id>0) return Result.success(true);
         else return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER1_BUYOFF_EDIT_ERROR);
    }

//    网点发货前准备
    @PostMapping("/PreDelivery")
    @ResponseBody
    public Result<List<User>> PreDelivery(@RequestBody Map<String,Object> map) {
        Long userId = Long.valueOf(map.get("userId").toString());
        return Result.success(expressMailOrderService.findAllNextTransPoint(userId));
    }

//    网点发货
    @PostMapping("/deliveryTheItem")
    @ResponseBody
    public Result<Boolean> deliveryTheItem(@RequestBody Map<String,Object> map) {
        Long preBranchId = Long.valueOf(map.get("preBranchId").toString());
        Long expressMailOrderId = Long.valueOf(map.get("expressMailOrderId").toString());
        Long nextBranchId = Long.valueOf(map.get("nextBranchId").toString());
        Long recordId = Long.valueOf(map.get("recordId").toString());
        Long id = processRecordsService.ChangeStatusSaveProcess(recordId,preBranchId, expressMailOrderId,
                nextBranchId, ExpressMailOrderStatus.THEGOODS, ExpressMailOrderStatus.COMPLETED);
        if (id == 0) {
            return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER_SENDTHECARGO_ADD_EDIT_ERROR);
        } else return Result.success(true);
    }


}