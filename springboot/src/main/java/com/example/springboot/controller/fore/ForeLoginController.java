package com.example.springboot.controller.fore;


import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.fore.ForeUser;
import com.example.springboot.service.fore.ForeUserService;
import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fore")
public class ForeLoginController {
@Autowired
    private ForeUserService foreUserService;
@RequestMapping(value = "/user/login",method = RequestMethod.POST)
@ResponseBody
public Result<UserDTO> login(@RequestBody UserDTO poreForeUser){


    ForeUser foreUser = foreUserService.findByMobile(poreForeUser.getMobile());

    if (foreUser == null){
        return Result.error(CodeMsg.HOMEUSER_NO_EXIST);
    }
    if (foreUser.getStatus() == 0){
        return Result.error(CodeMsg.HOMEUSER_STATUS_ERROR);
    }
    if (!foreUser.getPassword().equals(poreForeUser.getPassword())){
        return Result.error(CodeMsg.HOMEUSER_PASSWORD_ERROR);
    }
//    SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_KEY, byMobile);

    poreForeUser.setId(foreUser.getId());
    poreForeUser.setUsername(foreUser.getName());
    String token = TokenUtils.genToken(poreForeUser.getId().toString(),poreForeUser.getPassword());
    poreForeUser.setToken(token);
    System.out.println(poreForeUser);
    return Result.success(poreForeUser);

}


}
