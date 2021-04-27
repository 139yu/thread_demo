package com.xj.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.demo.entity.RedPacketRecordEntity;

import java.util.List;


/**
 * 抢红包记录表
 *
 * @author yu
 * @email 
 * @date 2021-04-27 17:18:30
 */
public interface RedPacketRecordService extends IService<RedPacketRecordEntity> {
    List<RedPacketRecordEntity> getList();
}

