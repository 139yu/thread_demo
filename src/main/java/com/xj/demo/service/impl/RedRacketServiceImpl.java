package com.xj.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xj.demo.vo.CreateRedPacket;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.demo.dao.RedRacketDao;
import com.xj.demo.entity.RedRacketEntity;
import com.xj.demo.service.RedRacketService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class RedRacketServiceImpl extends ServiceImpl<RedRacketDao, RedRacketEntity> implements RedRacketService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<RedRacketEntity> getList() {
        return this.baseMapper.selectList(new QueryWrapper<RedRacketEntity>());
    }

    /**
     * 创建红包，将红包添加进redis
     * @param createRedPacket
     */
    @Override
    public void create(CreateRedPacket createRedPacket) {
        RedRacketEntity redRacketEntity = new RedRacketEntity();
        int totalAmount = createRedPacket.getTotalAmount().multiply(new BigDecimal(100)).intValue();
        String redPacketId = UUID.randomUUID().toString();
        redRacketEntity
                .setVersion(1)
                .setRedPacketId(redPacketId)
                .setTotalAmount(totalAmount)
                .setTotalPacket(createRedPacket.getTotalPacket())
                .setCreateTime(new Date())
                .setType(createRedPacket.getType());
        redisTemplate.opsForValue().set("redPacketId:" + redPacketId, JSON.toJSONString(redRacketEntity),24, TimeUnit.HOURS);
        this.save(redRacketEntity);
    }

    @Override
    public void startTwoSeckil(String redPacketId) {

    }
}