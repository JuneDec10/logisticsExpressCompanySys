package com.example.springboot.controller.back;

import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.entity.back.ProcessRecords;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.common.ExpressMailOrderStatus;
import com.example.springboot.service.back.ProcessRecordsService;
import com.example.springboot.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/ProcessRecords")
public class ProcessRecordsController {

    @Autowired
    private ProcessRecordsService processRecordsService;

    @Autowired
    private UserService userService;

//    运输点中途收货列表
    @PostMapping("/list")
    @ResponseBody
    public Result<List<ProcessRecords>> list(@RequestBody Map<String,Object> map ){
        Long id = Long.valueOf(map.get("id").toString());
        System.out.println(id);
        return Result.success(processRecordsService.getList(id,ProcessRecords.NO_OPERATIONS, ExpressMailOrderStatus.COMPLETED));
    }

//    运输点中途收货
@PostMapping("/takeDeliveryOfGoods")
    @ResponseBody
    public Result<Boolean> takeDeliverOfGoods(@RequestBody Map<String,Object> map ){
    Long itemId = Long.valueOf(map.get("itemId").toString());
    Long recordId = Long.valueOf(map.get("recordId").toString());
    Long userId = Long.valueOf(map.get("userId").toString());
    Long updatePro =  processRecordsService.ChangeStatusSaveProcess(recordId,userId,itemId,userId,null,ExpressMailOrderStatus.INTRANSIT);
   if(updatePro<=0){
       return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER1_RECEIVING_EDIT_ERROR);
   }
    return Result.success(true);
}

//运输点中途发货列表
@PostMapping("/receiveList")
    @ResponseBody
    public Result<List<ProcessRecords>> receiveList(@RequestBody Map<String,Object> map){
    Long id = Long.valueOf(map.get("id").toString());
    return Result.success(processRecordsService.getList(id,ProcessRecords.NO_OPERATIONS,ExpressMailOrderStatus.INTRANSIT));
}

//运输点中途发货前,获取下一个运输点或网点的信息
    @PostMapping("/PreDelivery")
    @ResponseBody
    public Result<List<User>> PreDelivery(@RequestBody Map<String,Object> map ) {
        Long itemId = Long.valueOf(map.get("itemId").toString());
        Long userId = Long.valueOf(map.get("userId").toString());
        return Result.success(processRecordsService.findAllNextTransPoint(itemId,userId));
    }

//    运输点中途发货到下一个运输点或者网点
    @PostMapping("/deliveryTheItem")
    @ResponseBody
    public Result<Boolean> deliveryTheItem(@RequestBody Map<String,Object> map ) {
        Long expressMailOrderId= Long.valueOf(map.get("expressMailOrderId").toString());
        Long preBranchId= Long.valueOf(map.get("preBranchId").toString());
        Long nextBranchId= Long.valueOf(map.get("nextBranchId").toString());
        Long recordId= Long.valueOf(map.get("recordId").toString());
        Long toNext =   processRecordsService.ChangeStatusSaveProcess(recordId,preBranchId,expressMailOrderId,nextBranchId,null,ExpressMailOrderStatus.COMPLETED);
        if (toNext == null) {
            return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER_SENDTHECARGO_ADD_EDIT_ERROR);
        } else return Result.success(true);
    }

//    网点确认收货
    @PostMapping("/confirmDelivery")
    @ResponseBody
    public Result<Boolean> getDelivery(@RequestBody Map<String,Object> map ){
        Long expressMailOrderId= Long.valueOf(map.get("expressMailOrderId").toString());
        Long userId= Long.valueOf(map.get("userId").toString());
        Long recordId= Long.valueOf(map.get("recordId").toString());
        Long id = processRecordsService.ChangeStatusSaveProcess(recordId,userId,expressMailOrderId,userId,
                null,ExpressMailOrderStatus.INTRANSIT);
    if(id>=0){
        return Result.success(true);
    }else
        return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER_SENDTHECARGO_ADD_EDIT_ERROR);

    }

//    网点指定派送列表
    @PostMapping("/findDeliveryList")
    @ResponseBody
    public Result<List<ProcessRecords>> findDeliveryList(@RequestBody Map<String,Object> map ){
        Long id= Long.valueOf(map.get("id").toString());
        return Result.success(processRecordsService.getList(id,ProcessRecords.NO_OPERATIONS,ExpressMailOrderStatus.INTRANSIT));
    }

//    网点快递员列表
    @PostMapping("/listCourier")
    @ResponseBody
    public Result<List<User>> listCourier(@RequestBody Map<String,Object> map ){
        Long userId= Long.valueOf(map.get("userId").toString());
        return Result.success(userService.findBranchCourier(userId));
    }

//    网点指定快递员派送
@PostMapping("/specifyTheDelivery")
@ResponseBody
public Result<Boolean> specifyTheDelivery(@RequestBody Map<String,Object> map ){
        Long itemId= Long.valueOf(map.get("itemId").toString());
        Long courierId= Long.valueOf(map.get("courierId").toString());
        Long userId= Long.valueOf(map.get("userId").toString());
        Long recordId= Long.valueOf(map.get("recordId").toString());
    Long result = processRecordsService.ChangeStatusSaveProcess(recordId,userId,itemId,courierId,
            ExpressMailOrderStatus.INTHEDELIVERY,ExpressMailOrderStatus.DESIGNATE_DISPATCHERS);
    if(result==null){
        return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER1_BUYOFF_EDIT_ERROR);
    }else
        return Result.success(true);
}

//    快递员派送列表
    @PostMapping("/signForList")
    @ResponseBody
    public Result<List<ProcessRecords>> signForList(@RequestBody Map<String,Object> map ){
        Long id= Long.valueOf(map.get("id").toString());
        System.out.println("sign:"+id);
        return  Result.success(processRecordsService.getList(id,ProcessRecords.NO_OPERATIONS,ExpressMailOrderStatus.DESIGNATE_DISPATCHERS));
    }


//    货物签收
    @PostMapping("/signFor")
    @ResponseBody
    public Result<Boolean> signForComplete(@RequestBody Map<String,Object> map ){
        Long userId= Long.valueOf(map.get("userId").toString());
        Long itemId= Long.valueOf(map.get("itemId").toString());
        Long recordId= Long.valueOf(map.get("recordId").toString());
        Long buyOff = processRecordsService.ChangeStatusSaveProcess(recordId,userId,itemId,
                null,ExpressMailOrderStatus.SIGNFOR,ExpressMailOrderStatus.SIGNFOR);
        if(buyOff<=0){
            return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER1_BUYOFF1_EDIT_ERROR);
        }else
            return Result.success(true);
    }
}
