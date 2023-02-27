package com.example.springboot.service.fore;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.fore.ForeUser;
import org.springframework.stereotype.Service;

@Service
public interface ForeUserService extends IService<ForeUser> {
    ForeUser findById(Long id);
    ForeUser findByName(String name);
    ForeUser findByMobile(String mobile);
    public Long banUsr(Long UserId,Integer status,Integer deleted);
}
