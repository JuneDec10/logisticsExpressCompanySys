package com.example.springboot.controller.fore;

import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.entity.fore.Complaint;
import com.example.springboot.service.fore.ComplaintService;
import com.example.springboot.service.fore.ExpressMailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/fore/complaint")
public class ForeComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private ExpressMailOrderService expressMailOrderService;

    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(@RequestBody Complaint complaint){
        System.out.println(complaint);
        if(expressMailOrderService.getItemId(complaint.getWaybillNumber())==null){
            return Result.error(CodeMsg.WAYBILLNUMBER_NO_EXIST);
        }
        if(complaintService.findByHomeUser_IdAndWaybillNumber(complaint.getWaybillNumber())!=null){
            return Result.error(CodeMsg.COMPLAINT_WAYBILLNUMBER_EXIST);
        }
        complaint.setStatus(Complaint.NOTACCEPTED);
        if(complaintService.saveComplaint(complaint)==null){
            return Result.error(CodeMsg.COMPLAINT_ERROR);
        }
        return Result.success(true);
    }

    @GetMapping("/complaintList")
    @ResponseBody
    public List<Complaint> complaintList(@RequestParam Long userId){
        System.out.println(userId);
        return complaintService.listComplaint(userId);
    }

    @PostMapping("/logicDelete")
    @ResponseBody
    public Result<Boolean> logicDelete(@RequestBody Complaint complaint){
        System.out.println(complaint);
        if(complaintService.updateComplaint(complaint)!=null){
            return Result.success(true);
        }else
            return Result.error(CodeMsg.COMPLAINT_DELETED_ERROR);
    }
}
