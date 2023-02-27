package com.example.springboot.service.back.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Dao.back.ProcessRecordsDao;
import com.example.springboot.Dao.back.UserDao;
import com.example.springboot.Dao.fore.ExpressMailOrderDao;
import com.example.springboot.entity.back.ProcessRecords;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.fore.ExpressMailOrder;
import com.example.springboot.service.back.ProcessRecordsService;
import com.example.springboot.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessRecordsServiceImpl extends ServiceImpl<ProcessRecordsDao,ProcessRecords> implements ProcessRecordsService {

    @Autowired
    private ExpressMailOrderDao expressMailOrderDao;

    @Autowired
    private ProcessRecordsDao processRecordsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Override
    public Long ChangeStatusSaveProcess(Long recordId,Long preBranchId,Long expressMailOrderId,
                                        Long nextBranchId, Integer orderStatus, Integer travelPosition) {

        ProcessRecords oldProcess = new ProcessRecords();
        oldProcess.setId(recordId);
        oldProcess.setOperation(ProcessRecords.HAVE_HANDLED);
        processRecordsDao.update(oldProcess);

        ExpressMailOrder expressMailOrder = expressMailOrderDao.findById(expressMailOrderId);
        if(orderStatus!=null){
            expressMailOrder.setOrderStatus(orderStatus);
            expressMailOrderDao.updateOrderStatus(expressMailOrder);
        }

        ProcessRecords processRecords = new ProcessRecords();
        processRecords.setExpressMailOrderId(expressMailOrder);
        User user=new User();
        user.setId(preBranchId);
        processRecords.setReceivedBranchId(user);
        User nextUser = new User();
        nextUser.setId(nextBranchId);
        processRecords.setNextBranchId(nextUser);
        processRecords.setTravelPosition(travelPosition);
        return processRecordsDao.save(processRecords);
    }

    @Override
    public List<ProcessRecords> getList(Long userId,int processRecords,int expressMailOrderStatus) {
        return processRecordsDao.listAll(userId,processRecords,expressMailOrderStatus);
    }


    @Override
    public ProcessRecords find(ProcessRecords processRecords) {

        return processRecordsDao.find(processRecords);
    }

    @Override
    public List<User> findAllNextTransPoint(Long itemId,Long userId) {

        ExpressMailOrder expressMailOrder = expressMailOrderDao.findById(itemId);
        String exAddress = expressMailOrder.getRecipientsAddress();
        String[] strs = exAddress.split("-");
        exAddress = strs[0]+"-"+strs[1];

        User user = userDao.findById(userId);
        String userAddress = user.getAddress();
        strs = userAddress.split("-");
        userAddress = strs[0]+"-"+strs[1];

        if(exAddress.equals(userAddress)){
            return userService.findAllNextTransPoint(1,expressMailOrder.getRecipientsAddress());
        }else
        return userService.findAllNextTransPoint(2,exAddress);
    }

}
