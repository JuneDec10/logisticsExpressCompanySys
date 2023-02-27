package com.example.springboot.service.fore.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Dao.fore.ForeUserDao;
import com.example.springboot.entity.fore.ForeUser;
import com.example.springboot.service.fore.ForeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForeUserServiceImpl extends ServiceImpl<ForeUserDao,ForeUser> implements ForeUserService {

    @Autowired
    private ForeUserDao foreUserDao;

     //根据用户id查询
    public ForeUser findById(Long id){return foreUserDao.selectById(id);};

//    根据用户名查询
    public ForeUser findByName(String name){return foreUserDao.findByName(name);}

//    根据用户手机号查询
    public ForeUser findByMobile(String mobile){
        return foreUserDao.findByMobile(mobile);}

    public Long banUsr(Long UserId,Integer status,Integer deleted){
        System.out.println(foreUserDao.updateStatusOrDeleted(UserId,status,deleted));
        return  foreUserDao.updateStatusOrDeleted(UserId,status,deleted);
    }
}
