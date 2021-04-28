package com.xj.demo.dao;

import com.xj.demo.entity.RedRacketEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 红包信息表
 * 
 * @author yu
 * @email 
 * @date 2021-04-27 17:18:30
 */
public interface RedRacketDao extends BaseMapper<RedRacketEntity> {


    Integer getBalance(@Param("redPacketId") String redPacketId);
}
