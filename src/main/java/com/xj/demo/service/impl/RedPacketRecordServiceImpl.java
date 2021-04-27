package com.xj.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.demo.dao.RedPacketRecordDao;
import com.xj.demo.entity.RedPacketRecordEntity;
import com.xj.demo.service.RedPacketRecordService;

import java.util.List;


@Service
public class RedPacketRecordServiceImpl extends ServiceImpl<RedPacketRecordDao, RedPacketRecordEntity> implements RedPacketRecordService {

    @Override
    public List<RedPacketRecordEntity> getList() {
        return this.baseMapper.selectList(new QueryWrapper<RedPacketRecordEntity>());
    }
}