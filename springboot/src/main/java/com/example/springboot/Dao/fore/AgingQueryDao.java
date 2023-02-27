package com.example.springboot.Dao.fore;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.fore.AgingQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgingQueryDao extends BaseMapper<AgingQuery> {
    Boolean addOne(AgingQuery agingQuery);

    AgingQuery listById(Long id);

    Boolean updateAll(AgingQuery agingQuery);

    List<AgingQuery> listAll();

    List<AgingQuery> agingQueryList(@Param("sendAddress") String sendAddress,@Param("recipientsAddress") String recipientsAddress);

    AgingQuery getAgingQuery(@Param("sendAddress") String sendAddress,@Param("recipientsAddress") String recipientsAddress);
}
