package com.example.springboot.controller.back;

import cn.hutool.core.bean.BeanUtil;
import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.fore.ExpressMailOrder;
import com.example.springboot.entity.fore.ForeUser;
import com.example.springboot.service.back.UserService;
import com.example.springboot.service.fore.ExpressMailOrderService;
import com.example.springboot.service.fore.ForeUserService;
import com.example.springboot.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private UserService userService;

    @Autowired
    private ForeUserService foreUserService;

    @Autowired
    private ExpressMailOrderService expressMailOrderService;

    @PostMapping("/login")
    @ResponseBody
    public Result<UserDTO> login(@RequestBody UserDTO user){
        User findByUserName;
        if(user.getUsername()!=null){
            findByUserName = userService.findByUsername(user.getUsername());
        }else{
            String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
            String mobile = user.getMobile();
            if(mobile.length()!=11){
                return Result.error(CodeMsg.HOMEUSER_MOBILE_ERROR);
            }else{
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(mobile);
                boolean isMatch = m.matches();
                if(isMatch){
                    findByUserName = userService.findByMobile(user.getMobile());
                }else{
                    return Result.error(CodeMsg.HOMEUSER_MOBILE_ERROR);
                }
            }
               }
        //判断是否为空
        if(findByUserName == null){
            return Result.error(CodeMsg.ADMIN_USERNAME_NO_EXIST);
        }
        //表示用户存在，进一步对比密码是否正确
        if(!findByUserName.getPassword().equals(user.getPassword())){
            return Result.error(CodeMsg.ADMIN_PASSWORD_ERROR);
        }
        //表示密码正确，接下来判断用户状态是否可用
        if(findByUserName.getStatus() == User.ADMIN_USER_STATUS_UNABLE){
            return Result.error(CodeMsg.ADMIN_USER_UNABLE);
        }
        //创建用户对应token，返回给前台
        String token = TokenUtils.genToken(findByUserName.getId().toString(),findByUserName.getPassword());
        BeanUtil.copyProperties(findByUserName,user,true);
        user.setToken(token);
        return Result.success(user);
}

    @PostMapping("/foreUserList")
    @ResponseBody
    public Result<List<ForeUser>> foreUserList(){
        return Result.success(foreUserService.list());
    }

    @PostMapping("/banUser")
    @ResponseBody
    public Result<Boolean> banUser(@RequestBody Map<String,Object> map ){
        Long id = Long.valueOf(map.get("id").toString());
        Integer status = (Integer) map.get("status");
        Long record = null;
        if(status==1){
          record =  foreUserService.banUsr(id,ForeUser.BANNED,null);
        }else{
            record = foreUserService.banUsr(id,ForeUser.NORMAL,null);
        }
        System.out.println(record);
        if(record!=null){
            return Result.success(true);
        } else
            return Result.error(CodeMsg.ADMIN_ROLE_EDIT_ERROR);
    }

    @PostMapping("/listAllPackage")
    @ResponseBody
    public Result<List<ExpressMailOrder>> listAllPackage(){
      return  Result.success(expressMailOrderService.listAll());
    }
}
