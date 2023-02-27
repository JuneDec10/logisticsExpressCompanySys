package com.example.springboot.service.back;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.fore.AgingQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgingQueryService extends IService<AgingQuery> {
 List<AgingQuery> listAgingQuery();
 Integer editAgingQuery(AgingQuery agingQuery);
 Boolean addAgingQuery(AgingQuery agingQuery);
}
