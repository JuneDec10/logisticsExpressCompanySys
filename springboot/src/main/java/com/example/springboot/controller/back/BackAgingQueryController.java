package com.example.springboot.controller.back;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.entity.fore.AgingQuery;
import com.example.springboot.service.back.AgingQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/agingQuery")
public class BackAgingQueryController {

    @Autowired
    private AgingQueryService agingQueryService;

    @PostMapping("/list")
    @ResponseBody
    public Result<List<AgingQuery>> list(){
        List<AgingQuery> agingQueryList = agingQueryService.listAgingQuery();
        if(agingQueryList.isEmpty()){
            return Result.error(CodeMsg.ADMIN_AGINGQUERY_GET_NULL);
        }else
            return Result.success(agingQueryList);
    }

    @PostMapping("/edit")
    @ResponseBody
    public Result<Boolean> edit(AgingQuery agingQuery){
        Integer integer = agingQueryService.editAgingQuery(agingQuery);
        if(integer.equals(1)){
            return Result.error(CodeMsg.ADMIN_AGINGQUERY_UPDATE_REPEAT);
        }else if(integer.equals(2)){
            return Result.error(CodeMsg.ADMIN_AGINGQUERY_UPDATE_ERROR);
        }else
            return Result.success(true);
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(AgingQuery agingQuery){
        if(agingQueryService.addAgingQuery(agingQuery)){
            return Result.success(true);
        }else
            return Result.error(CodeMsg.ADMIN_AGINGQUERY_ADD_ERROR);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<Boolean> delete(Map<String,Object> map){
        Long id = Long.valueOf(map.get("id").toString());
        QueryWrapper<AgingQuery> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        if(agingQueryService.remove(queryWrapper)){
            return Result.success(true);
        }else
            return Result.error(CodeMsg.ADMIN_AGINGQUERY_DELETE_ERROR);
    }
}
