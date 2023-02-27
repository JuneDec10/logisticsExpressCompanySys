package com.example.springboot.service.fore;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.common.ExpressMailOrderStatus;
import com.example.springboot.entity.fore.AgingQuery;
import com.example.springboot.entity.fore.AgingQueryInfo;
import com.example.springboot.entity.fore.ExpressMailOrder;
import com.example.springboot.entity.fore.ProgressInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpressMailOrderService extends IService<ExpressMailOrder> {
    ExpressMailOrder addOne(ExpressMailOrder expressMailOrder);
    List<ExpressMailOrder> listAll();
    List<ExpressMailOrder> listByStatus(Long id,int expressMailOrderStatus);
    Long saveExpressMailOrderAndBuyOff(Long userid,Long id);
    List<User>  findAllNextTransPoint(Long userid);
    List<ExpressMailOrder> selOrderState(Long idOrMobile,String status);
    List<ProgressInfo>  forDetail(Long itemId);
    ExpressMailOrder getItemId(String waybill);
    ExpressMailOrder findById(Long id);
    Long updateAbolished(Long id,Integer abolished);
    List<AgingQueryInfo> agingQueryList(String sendAddress, String recipientsAddress, Double itemWeight);
}
