package com.example.springboot.service.back.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Dao.fore.AgingQueryDao;
import com.example.springboot.common.CodeMsg;
import com.example.springboot.entity.fore.AgingQuery;
import com.example.springboot.service.back.AgingQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgingQueryServiceImpl extends ServiceImpl<AgingQueryDao, AgingQuery> implements AgingQueryService {

    @Autowired
    private AgingQueryDao agingQueryDao;

    @Override
    public List<AgingQuery> listAgingQuery() {
        return agingQueryDao.listAll();
    }

    @Override
    public Integer editAgingQuery(AgingQuery agingQuery) {
       List<AgingQuery> agingQueryList = agingQueryDao.agingQueryList(agingQuery.getOrigin(),agingQuery.getDestination());
       for(AgingQuery aq:agingQueryList){
           if(aq.getId()!=agingQuery.getId()){
               return 1;
           }
       }
      Boolean result = agingQueryDao.updateAll(agingQuery);
if(result==false){
    return 2;
}else
        return 0;
    }

    @Override
    public Boolean addAgingQuery(AgingQuery agingQuery) {
        if(!agingQueryDao.agingQueryList(agingQuery.getOrigin(),agingQuery.getDestination()).isEmpty())
            return false;
        return agingQueryDao.addOne(agingQuery);
    }


}
