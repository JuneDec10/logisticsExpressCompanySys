package com.example.springboot.service.fore.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Dao.back.ProcessRecordsDao;
import com.example.springboot.Dao.back.UserDao;
import com.example.springboot.Dao.fore.AgingQueryDao;
import com.example.springboot.Dao.fore.ExpressMailOrderDao;
import com.example.springboot.Dao.fore.ForeUserDao;
import com.example.springboot.entity.back.ProcessRecords;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.common.ExpressMailOrderStatus;
import com.example.springboot.entity.fore.*;
import com.example.springboot.service.back.UserService;
import com.example.springboot.service.fore.ExpressMailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ExpressMailOrderServiceImpl extends ServiceImpl<ExpressMailOrderDao, ExpressMailOrder> implements ExpressMailOrderService {

    @Autowired
    private ExpressMailOrderDao expressMailOrderDao;

@Autowired
private ForeUserDao foreUserDao;

@Autowired
private UserDao userDao;

@Autowired
private UserService userService;

@Autowired
private ProcessRecordsDao processRecordsDao;

@Autowired
private AgingQueryDao agingQueryDao;

    @Override
    public ExpressMailOrder addOne(ExpressMailOrder expressMailOrder) {
        AgingQuery agingQueryList = agingQueryDao.getAgingQuery(expressMailOrder.getSendAddress(),expressMailOrder.getRecipientsAddress());
        String sendAddress = expressMailOrder.getSendAddress();
        String recipientsAddress = expressMailOrder.getRecipientsAddress();
        if(agingQueryList!=null){
        }else{
            String[] origin = sendAddress.split("-");
            String[] destination = recipientsAddress.split("-");
            if(origin[0].equals(destination[0])&&origin[1].equals(destination[1])){
                agingQueryList =agingQueryDao.getAgingQuery("区1","区2");
            }else if(origin[0].equals(destination[0])){
                agingQueryList = agingQueryDao.getAgingQuery("城市1","城市2");
            }else{
                agingQueryList = agingQueryDao.getAgingQuery("省1","省2");
            }
        }
        Double weight = expressMailOrder.getItemWeight();
        Double price = 12.00;
        if(weight<=1){
            price = agingQueryList.getOriginPrice();
        }else{
            price = agingQueryList.getOriginPrice()+(weight-1)*agingQueryList.getPricePerKilogram();
        }
       expressMailOrder.setPrice(price);
      if((userDao.findByAddress(1, expressMailOrder.getSendAddress()).isEmpty())||
              (userDao.findByAddress(1, expressMailOrder.getRecipientsAddress()).isEmpty()))  {
          return null;
      }
      expressMailOrderDao.addOne(expressMailOrder);
      Long id = expressMailOrder.getId();
      ExpressMailOrder waybillNumber = expressMailOrderDao.findById(id);
       return  waybillNumber;

    }

    @Override
    public List<ExpressMailOrder> listAll() {

        return expressMailOrderDao.listAll();

    }

    @Override
    public List<ExpressMailOrder> listByStatus(Long sender_id, int expressMailOrderStatus) {
        return expressMailOrderDao.listByStatus(sender_id,expressMailOrderStatus);
    }

    @Override
    public Long saveExpressMailOrderAndBuyOff(Long userid,Long id) {
        ExpressMailOrder expressMailOrder =expressMailOrderDao.findById(id);
        expressMailOrder.setOrderStatus(ExpressMailOrderStatus.TO_COMPLETE_THE_GOODS);
        ProcessRecords processRecords = new ProcessRecords();
        processRecords.setExpressMailOrderId(expressMailOrder);
        User user = new User();
        user.setId(userid);
        processRecords.setNextBranchId(user);
        processRecords.setTravelPosition(ExpressMailOrderStatus.TO_COMPLETE_THE_GOODS);
        expressMailOrderDao.updateOrderStatus(expressMailOrder);
        Long saveBuyOff = processRecordsDao.save(processRecords);
        if(saveBuyOff>=0)
    return  saveBuyOff;
        else return null;
    }

    @Override
    public List<User> findAllNextTransPoint(Long userid) {
        User user = userDao.findById(userid);
        String address = user.getAddress();
        String[] strs = address.split("-");
        address = strs[0]+"-"+strs[1];
        return userService.findAllNextTransPoint(2,address);
    }

    @Override
    public List<ExpressMailOrder> selOrderState(Long idOrMobile, String status) {
        if(idOrMobile==null){
            return null;
        }
        int setOrderState = 2;
        ExpressMailOrder express = new ExpressMailOrder();
        if ("1".equals(status)||"100".equals(status)||"10".equals(status)||"11".equals(status)||"12".equals(status)){
            express.setForeUserId(idOrMobile);
        }else {
            ForeUser foreUser = foreUserDao.findById(idOrMobile);
            express.setRecipientsMobile(foreUser.getMobile());
        }
        if("10".equals(status)||"20".equals(status)){
            express.setOrderStatus(ExpressMailOrderStatus.HAVEORDER);
        }else if("12".equals(status)||"22".equals(status)){
            express.setOrderStatus(ExpressMailOrderStatus.SIGNFOR);
        }else if("100".equals(status)){
            express.setOrderStatus(ExpressMailOrderStatus.NOTORDER);
        }
        else if("11".equals(status)||"21".equals(status)){
            setOrderState = 1;
        }
        return expressMailOrderDao.listByIdOrMobile(express,setOrderState);
    }

    @Override
    public List<ProgressInfo> forDetail(Long itemId) {
        List<ProcessRecords> records = processRecordsDao.listAllById(itemId);
        List<ProgressInfo> infos = new ArrayList<ProgressInfo>();
        ProgressInfo progressInfo1 = new ProgressInfo();
        ExpressMailOrder expressMailOrder = expressMailOrderDao.findById(itemId);
        progressInfo1.setCreateTime(expressMailOrder.getCreateTime());
        progressInfo1.setContext("货物已下单");
        infos.add(progressInfo1);
        String context = "";
        String address = "";
        String[] strs = new String[3];
        for (ProcessRecords re:records) {
            ProgressInfo progressInfo = new ProgressInfo();
            if(re.getReceivedBranchId().getId()!=null&&re.getReceivedBranchId().getRoleId()!=3) {
                address = re.getReceivedBranchId().getAddress();
                strs = address.split("-");
            }
            if(re.getTravelPosition()==ExpressMailOrderStatus.TO_COMPLETE_THE_GOODS){
                progressInfo.setCreateTime(re.getCreateTime());
                context ="您的快递已被"+"["+ re.getNextBranchId().getUsername()+"] 揽收";
                progressInfo.setContext(context);
            }else if(re.getTravelPosition()==ExpressMailOrderStatus.SIGNFOR){
                progressInfo.setCreateTime(re.getCreateTime());
                context ="快件已签收,感谢您的使用,期待再次为您服务";
                progressInfo.setContext(context);
            }else if(re.getTravelPosition()==ExpressMailOrderStatus.DESIGNATE_DISPATCHERS){
                progressInfo.setCreateTime(re.getCreateTime());
                context ="派件员:"+re.getNextBranchId().getUsername()+" 电话:"+re.getNextBranchId().getMobile()+" 正在为您派件";
                progressInfo.setContext(context);
            }else if(re.getTravelPosition()==ExpressMailOrderStatus.INTRANSIT){
                progressInfo.setCreateTime(re.getCreateTime());
                context ="["+strs[1]+"] 快件到达 ["+re.getNextBranchId().getUsername()+"]";
                progressInfo.setContext(context);
            }else if(re.getTravelPosition()==ExpressMailOrderStatus.COMPLETED){
                progressInfo.setCreateTime(re.getCreateTime());
                context ="["+strs[1]+"] 快件已在 ["+re.getReceivedBranchId().getUsername()+"]装车,准备发往 ["+re.getNextBranchId().getUsername()+"]";
                progressInfo.setContext(context);
            }
          infos.add(progressInfo);
        }
return infos;
    }

    @Override
    public ExpressMailOrder getItemId(String waybillNumber) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("waybill_number",waybillNumber);
        ExpressMailOrder expressMailOrder = expressMailOrderDao.selectOne(wrapper);
        return expressMailOrder;
    }

    @Override
    public ExpressMailOrder findById(Long id) {
        return expressMailOrderDao.findById(id);
    }

    @Override
    public Long updateAbolished(Long id, Integer abolished) {
        return expressMailOrderDao.updateAbolished(abolished,id);
    }

    @Override
    public List<AgingQueryInfo> agingQueryList(String sendAddress, String recipientsAddress, Double itemWeight) {
        List<AgingQuery> agingQueryList = agingQueryDao.agingQueryList(sendAddress,recipientsAddress);
       if(!agingQueryList.isEmpty()){
       }else{
           String[] origin = sendAddress.split("-");
           String[] destination = recipientsAddress.split("-");
           if(origin[0].equals(destination[0])&&origin[1].equals(destination[1])){
               agingQueryList =agingQueryDao.agingQueryList("区1","区2");
           }else if(origin[0].equals(destination[0])){
               agingQueryList = agingQueryDao.agingQueryList("城市1","城市2");
           }else{
               agingQueryList = agingQueryDao.agingQueryList("省1","省2");
           }
       }
       List<AgingQueryInfo> agingQueryInfoList = new ArrayList<>();
        for (AgingQuery aq:agingQueryList) {
            AgingQueryInfo agingQueryInfo = new AgingQueryInfo();
            agingQueryInfo.setTitle("筋斗云快递");
            agingQueryInfo.setWeight(itemWeight+"kg");
            agingQueryInfo.setEta(aq.getEta()+"天");
            agingQueryInfo.setPriceRule("首重（1.0kg）"+aq.getOriginPrice()+"元，续重"+aq.getPricePerKilogram()+"元/kg");
            if(itemWeight<1){
                agingQueryInfo.setPrice(aq.getOriginPrice()+"元");
            }else{
                agingQueryInfo.setPrice(aq.getOriginPrice()+aq.getPricePerKilogram()*(itemWeight-1)+"元");
            }
            agingQueryInfoList.add(agingQueryInfo);
        }
       return agingQueryInfoList;

    }


}
