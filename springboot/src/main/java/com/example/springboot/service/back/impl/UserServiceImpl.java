package com.example.springboot.service.back.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Dao.back.UserDao;
import com.example.springboot.entity.back.User;
import com.example.springboot.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

@Autowired
private UserDao userDao;

    @Override
    public User findByUsername(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findById(Long id) {
      return userDao.findById(id);
    }

    @Override
    public User findByMobile(String mobile) {
        return userDao.findByMobile(mobile);
    }

    public List<User> findByUserType(int type){
        return userDao.findAllByUserType(type);
    }

    @Override
    public List<User> findAllUserButCourier(int type) {
return userDao.findBut(type);

    }

    @Override
    public int updateOne(User user) {
      int affected =  userDao.updateOne(user);
      if(affected>0){
          return 1;
      }else  return 0;
    }

    @Override
    public int updateCourier(User user) {
        return userDao.updateCourier(user);
    }

    @Override
    public int logicDeleteOne(User user) {
        user.setDeleted(1);
        int deleted = userDao.logicDeleteOne(user);
        if(deleted>0){
            return 1;
        }else return 0;
    }

    @Override
    public int addOne(User user) {
        user.setPassword("123456");
        int add = userDao.addOne(user);
        if(add>0){
            return 1;
        }else return 0;
    }

    @Override
    public List<User> findAllNextTransPoint(int role,String address) {

        return userDao.findAllNextTransPoint(address,role);

    }

    @Override
    public List<User> findBranchCourier(Long branchId) {

        return userDao.findBranchCourier(branchId);
    }

    @Override
    public List<User> findLikeAddress(String address) {
        return userDao.findByLikeAddress(address);
    }


}
