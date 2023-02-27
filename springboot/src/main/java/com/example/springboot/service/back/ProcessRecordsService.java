package com.example.springboot.service.back;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.Dao.back.ProcessRecordsDao;
import com.example.springboot.entity.back.ProcessRecords;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.fore.ExpressMailOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProcessRecordsService extends IService<ProcessRecords> {

    Long ChangeStatusSaveProcess(Long recordId,Long preBranchId,Long expressMailOrderId,Long nextBranchId,
                                    Integer orderStatus,Integer travelPosition);
List<ProcessRecords> getList(Long userId,int processRecords,int expressMailOrderStatus);
//Long takeDeliveryOfTheGoods(Long id);

ProcessRecords find(ProcessRecords processRecords);

List<User> findAllNextTransPoint(Long itemId,Long userId);


}
