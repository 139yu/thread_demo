package com.xj.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.demo.entity.RedRacketEntity;
import com.xj.demo.vo.CreateRedPacket;

import java.util.List;
import java.util.Map;

/**
 * 红包信息表
 *
 * @author yu
 * @email 
 * @date 2021-04-27 17:18:30
 */
public interface RedRacketService extends IService<RedRacketEntity> {
    List<RedRacketEntity> getList();

    String create(CreateRedPacket createRedPacket);

    void startTwoSeckil(String redPacketId);
}

