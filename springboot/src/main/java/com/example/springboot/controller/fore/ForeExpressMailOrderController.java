package com.example.springboot.controller.fore;

import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.entity.back.ProcessRecords;
import com.example.springboot.entity.common.ExpressMailOrderStatus;
import com.example.springboot.entity.fore.AgingQuery;
import com.example.springboot.entity.fore.AgingQueryInfo;
import com.example.springboot.entity.fore.ExpressMailOrder;
import com.example.springboot.entity.fore.ProgressInfo;
import com.example.springboot.service.fore.ExpressMailOrderService;
import com.example.springboot.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fore/expressMailOrder")
public class ForeExpressMailOrderController {
    @Autowired
    private ExpressMailOrderService expressMailOrderService;

    @PostMapping("/add")
    @ResponseBody
    public Result<ExpressMailOrder> add(@RequestBody ExpressMailOrder expressMailOrder) {
        expressMailOrder.setOrderStatus(8);
        expressMailOrder.setWaybillNumber(StringUtil.GenerateAUniqueNumber());
        ExpressMailOrder records = expressMailOrderService.addOne(expressMailOrder);
        if ( records == null) {
            return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER_ADD_ERROR);
        }else{
            return Result.success(records);
        }


    }

    @GetMapping("/listOrder")
    @ResponseBody
    public List<ExpressMailOrder> listOrder(@RequestParam Long idOrMobile,@RequestParam String status){
        return expressMailOrderService.selOrderState(idOrMobile,status);
    }

    @PostMapping("/forDetail")
    @ResponseBody
    public Result<List<ProgressInfo>> forDetail(@RequestBody Map<String,Object> map ){
        Long itemId = Long.valueOf(map.get("itemId").toString());
        return Result.success(expressMailOrderService.forDetail(itemId));
    }

    @PostMapping("/searchItem")
    @ResponseBody
    public Result<List<ProgressInfo>> searchItem(@RequestBody Map<String,Object> map ){
        String itemId = map.get("itemId").toString();
        ExpressMailOrder expressId = expressMailOrderService.getItemId(itemId);
        if(expressId==null){
            return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER1_SEARCH_ERROR);
        }
        Long id = expressId.getId();
    return Result.success(expressMailOrderService.forDetail(id));
    }

    @GetMapping("/consertItem")
    @ResponseBody
    public Result<Boolean> consertItem(@RequestParam Long itemId){
       ExpressMailOrder expressMailOrder = expressMailOrderService.findById(itemId);
        System.out.println(expressMailOrder);
       if(expressMailOrder.getOrderStatus()== ExpressMailOrderStatus.TO_COMPLETE_THE_GOODS){
           expressMailOrderService.updateAbolished(itemId,ExpressMailOrder.ABOLISHING);
       }else if(expressMailOrder.getOrderStatus()== ExpressMailOrderStatus.HAVEORDER){
           expressMailOrderService.updateAbolished(itemId,ExpressMailOrder.ABOLISHED);
       }else if(expressMailOrder.getOrderStatus()== ExpressMailOrderStatus.NOTORDER){
           expressMailOrderService.updateAbolished(itemId,ExpressMailOrder.ABOLISHED);
       } else
           return Result.error(CodeMsg.ADMIN_EXPRESSMAILORDER_DELETE_ERROR);
       return Result.success(true);
    }

    @GetMapping("/agingQuery")
    @ResponseBody
    public List<AgingQueryInfo> agingQueryList(@RequestParam String sendAddress, String recipientsAddress, Double itemWeight){
         return expressMailOrderService.agingQueryList(sendAddress, recipientsAddress, itemWeight);
    }

}