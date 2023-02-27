package com.example.springboot.controller.back;

import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.entity.back.User;
import com.example.springboot.service.back.UserService;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/system/transport")
@Controller
public class TransportController {
    @Autowired
    private UserService userService;
    @PostMapping("/list")
    @ResponseBody
    public Result<List<User>> findAll(@RequestBody Map<String,Object> map){
        String query = map.get("query").toString();
        System.out.println(query);
        if(query.equals("courier"))
        {
            return Result.success(userService.findByUserType(3));
        }else if(query.equals("transport")){
            return Result.success(userService.findByUserType(2));
        }
        else if(query.equals("branch")){
            return Result.success(userService.findByUserType(1));
        }else if(query.equals("all")){
            return Result.success(userService.findAllUserButCourier(3));
        }
        else return Result.error(CodeMsg.ADMIN_NO_RIGHT);
    }

    @PostMapping("/courierList")
    @ResponseBody
    public Result<List<User>> courierList(@RequestBody Map<String,Object> map ){
        Long userId =Long.valueOf(map.get("userId").toString());
        if(userService.findById(userId).getRoleId()==0){
            return Result.success(userService.findByUserType(3));
        }
        return Result.success(userService.findBranchCourier(userId));
    }


    @PostMapping("/update")
    @ResponseBody
    public Result<Boolean> update(@RequestBody User user){
        System.out.println("update:"+user);
        if(user.getAddress() == null){
            return Result.error(CodeMsg.ADDRESS_ERROR);
        }
        if(user.getRoleId() == null){
            return Result.error(CodeMsg.TRANSPORT_USER_ROLE_EMPTY);
        }
        if(user.getId() == null){
            return Result.error(CodeMsg.TRANSPORT_USE_NO_EXIST);
        }
        if(userService.findByUsername(user.getUsername()).getId()!=user.getId()){
            System.out.println(userService.findByUsername(user.getUsername()));
            return Result.error(CodeMsg.TRANSPORT_USERNAME_EXIST);
        }
      int affected =  userService.updateOne(user);
        if(affected>0){
            return Result.success(true);
        }else
            return Result.error(CodeMsg.TRANSPORT_USE_ADD_ERROR);

    }

    @PostMapping("/updateCourier")
    @ResponseBody
    public Result<Boolean> updateCourier(@RequestBody User user){
        int affected = userService.updateCourier(user);
        if(affected>0){
            return Result.success(true);
        }else
            return Result.error(CodeMsg.TRANSPORT_USE_ADD_ERROR);

    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<Boolean> logicDelete(@RequestBody User user){
        Integer affected =  userService.logicDeleteOne(user);
        if(affected>0){
            return Result.success(true);
        }
        return Result.error(CodeMsg.TRANSPORT_USE_DELETE_ERROR);
    }
    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(@RequestBody User user){

        System.out.println(user);
        if(user.getAddress()==null){
            return Result.error(CodeMsg.ADDRESS_ERROR);
        }
        if(userService.findByUsername(user.getUsername())!=null){
            return Result.error(CodeMsg.TRANSPORT_USERNAME_EXIST);
        }
      int affected =  userService.addOne(user);
        if(affected>0){
            return Result.success(true);
               }else
            return Result.error(CodeMsg.TRANSPORT_USE_ADD_ERROR);

    }

    @PostMapping("/search")
    @ResponseBody
    public Result<List<User>> search(@RequestBody Map<String,Object> map ){
        String address = map.get("address").toString();
        return Result.success(userService.findLikeAddress(address));
    }



}
