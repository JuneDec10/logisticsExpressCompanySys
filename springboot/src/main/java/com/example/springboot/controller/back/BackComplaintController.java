package com.example.springboot.controller.back;

import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.entity.fore.Complaint;
import com.example.springboot.service.fore.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/complaint")
public class BackComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/list")
    @ResponseBody
    public Result<List<Complaint>> complaintList(){
        return Result.success(complaintService.listAll());
    }

    @PostMapping("/handleTheComplaint")
    @ResponseBody
    public Result<Boolean> handleTheComplaint(@RequestBody Map<String,Object> map ){
        Long complaintId =Long.valueOf(map.get("complaintId").toString()) ;
        if(complaintService.updateStatus(complaintId,Complaint.ACCEPTING)>0)
        return Result.success(true);
        else
            return Result.error(CodeMsg.COMPLAINT_ACCEPTED_ERROR);
    }

    @PostMapping("/finishedTheComplaint")
    @ResponseBody
    public Result<Boolean> finishedTheComplaint(@RequestBody Map<String,Object> map ){
        Long complaintId = Long.valueOf(map.get("complaintId").toString());
        if(complaintService.updateStatus(complaintId,Complaint.ACCEPTED)!=null)
            return Result.success(true);
        else
            return Result.error(CodeMsg.COMPLAINT_ACCEPTED_ERROR);
    }
}
